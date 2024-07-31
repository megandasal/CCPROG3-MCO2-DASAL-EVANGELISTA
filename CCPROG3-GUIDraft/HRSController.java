import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HRSController implements ActionListener, DocumentListener {
    private HotelReservationGUI gui;
    private ArrayList<Hotel> hotelList;
    private int hotelCount;
    private String currentOperation = "";
    private String currentModification = "";

    public HRSController(HotelReservationGUI gui, ArrayList<Hotel> hotelList, int hotelCount) {
        this.gui = gui;
        this.hotelList = hotelList;
        this.hotelCount = hotelCount;
        this.currentOperation = "";
        
        gui.setActionListener(this);
        gui.setDocumentListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action Command: " + command); // Debugging
        
        SwingUtilities.invokeLater(() -> { // Ensure GUI updates are on EDT
            // Hide all frames/dialogs initially if needed
            hideAllDialogs();
            
            switch (command) {
                // main menu buttons
                case "Create Hotel":
                    gui.toggleCreateHotelDialog(true);
                    break;
    
                case "Finish Create Hotel":
                    processFinishCreateHotel();
                    break;
    
                case "Manage Hotel":
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("No hotels to manage.");
                    } else {
                        gui.toggleManageHotelMenu(true);
                    }
                    break;
    
                case "View Hotel":
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("No hotels to view.");
                    } else {
                        currentOperation = "View Hotel";
                        gui.toggleHotelSelectionDialog(true);
                    }
                    break;
    
                case "Simulate Booking":
                    currentOperation = "Simulate Booking";
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("No hotels available for booking.");
                    } else {
                        populateCheckInOutCb();
                        populateAvailableRoomsToBookCBox();
                        gui.toggleHotelSelectionDialog(true);
                    }
                    break;
    
                case "Submit Booking Info 1":
                    gui.toggleSimulateBookingDialog2(true);
                    break;
    
                case "Submit Booking Info 2":
                    gui.toggleSimulateBookingDialog(true);
                    processSimulateBooking();
                    gui.toggleSimulateBookingDialog2(false);
                    gui.clearSimBookingTextFields();
                    break;
    
                // manage hotel buttons
                case "Change Hotel Name":
                case "Add Rooms":
                case "Remove Rooms":
                case "Update Room Price":
                case "Date Price Modifier":
                case "Remove Reservation":
                case "Remove Hotel":
                    currentOperation = command;
                    gui.toggleManageHotelMenu(false);
                    gui.toggleHotelSelectionDialog(true);
                    break;
    
                // hotel selection dialog buttons
                case "Select Hotel":
                    handleHotelSelection();
                    break;
    
                case "Submit New Hotel Name":
                case "Submit New Rooms":
                case "Remove Selected Room":
                case "Submit New Room Price":
                case "Modify Date Price":
                case "Submit Remove Reservation":
                case "Submit Remove Hotel":
                    currentModification = command;
                    gui.toggleConfirmModificationDialog(true);
                    break;
    
                case "Confirm Modification":
                    handleConfirmModification();
                    break;
    
                case "Cancel Modification":
                    handleCancelModification();
                    break;
    
                // other buttons
                case "View Date":
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("There are currently no rooms in this hotel.");
                    } else {
                        populateDateComboBox();
                        gui.toggleViewHotelMenu(false);
                        gui.toggleViewDateDialog(true);
                    }
                    break;
    
                case "View Room":
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("No rooms available for viewing.");
                    } else {
                        populateRoomsToViewCBox();
                        gui.toggleViewHotelMenu(false);
                        gui.toggleSelectRoomDialog(true);
                    }
                    break;
    
                case "Select Room":
                    setRoomInfoTA(); // display room information
                    setRoomToViewTA();
                    gui.toggleSelectRoomDialog(false);
                    gui.toggleViewRoomsFrame(true);
                    break;
    
                case "View Reservation":
                    if (hotelList.isEmpty()) {
                        gui.showErrorMessage("No reservations available for viewing.");
                    } else {
                        populateReservationCBox();
                        gui.toggleViewHotelMenu(false);
                        gui.toggleViewReservationMenu(true);
                    }
                    break;
    
                case "Select Reservation":
                    gui.toggleViewReservationMenu(false);
                    gui.toggleViewReservationDialog(true);
                    setReservationToViewTA();
                    break;
    
                case "Select Date":
                    int availableRooms = getAvailableRoomsOnDate();
                    if (availableRooms == 0) {
                        gui.showErrorMessage("No rooms are available on this date.");
                    } else {
                        gui.showConfirmationMessage("There are " + availableRooms + " rooms available on this date.");
                        gui.toggleViewDateDialog(false);
                    }
                    break;
    
                default:
                    System.out.println("Unknown action command: " + command); // Debugging
                    break;
            }
        });
    }

    public void handleHotelSelection() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        System.out.println("Selected hotel: " + selectedHotel); // debugging

        for (Hotel hotel : hotelList) {
            switch (currentOperation) {
                case "View Hotel":
                    setHotelInfoTA();
                    gui.toggleHotelSelectionDialog(false);
                    gui.toggleViewHotelMenu(true);
                    break;
        
                case "Change Hotel Name":
                    gui.toggleChangeHotelNameDialog(true);
                    break;
        
                case "Add Rooms":
                    gui.toggleAddRoomsDialog(true);
                    break;
        
                case "Remove Rooms":
                    gui.setRoomRemovalTextArea(hotel.getRoomInfoForRemoval());
                    populateRemoveRoomsComboBox();
                    gui.toggleRemoveRoomsFrame(true);
                    break;
        
                case "Update Room Price":
                    gui.setRoomBasePriceLabel(hotel.getBaseRate());
                    gui.toggleUpdateRoomPriceDialog(true);
                    break;
        
                case "Date Price Modifier":
                    setMDPTextArea();
                    populateDPMComboBox();
                    gui.toggleModifyDatePriceFrame(true);
                    break;
        
                case "Remove Reservation":
                    gui.toggleRemoveReservationDialog(true);
                    break;
        
                case "Remove Hotel":
                    gui.toggleRemoveHotelDialog(true);
                    break;
        
                case "Simulate Booking":
                    gui.toggleSimulateBookingDialog(true);
                    break;
        
                default:
                    System.out.println("Unknown operation command: " + currentOperation); // Debugging
                    break;
            }
        }
    }
    
    private void hideAllDialogs() {
        // Hide all dialogs or frames if necessary
        gui.toggleCreateHotelDialog(false);
        gui.toggleManageHotelMenu(false);
        gui.toggleHotelSelectionDialog(false);
        gui.toggleSimulateBookingDialog(false);
        gui.toggleSimulateBookingDialog2(false);
        gui.toggleChangeHotelNameDialog(false);
        gui.toggleAddRoomsDialog(false);
        gui.toggleRemoveRoomsFrame(false);
        gui.toggleUpdateRoomPriceDialog(false);
        gui.toggleModifyDatePriceFrame(false);
        gui.toggleRemoveReservationDialog(false);
        gui.toggleRemoveHotelDialog(false);
        gui.toggleViewHotelMenu(false);
        gui.toggleSelectRoomDialog(false);
        gui.toggleViewRoomsFrame(false);
        gui.toggleViewReservationMenu(false);
        gui.toggleViewReservationDialog(false);
        gui.toggleViewDateDialog(false);
    }

    private void clearAllTextFields() {
        gui.clearCreateHotelTF();
        gui.clearChangeHotelNameTF();
        gui.clearAddRoomsTf();
        gui.clearUpdateRoomPriceTf();
        gui.clearPercentageMDP();
        gui.clearRemoveReservationTf();
    }
    
    public void handleConfirmModification() {
        switch (currentModification) {
            case "Submit New Hotel Name":
                String newHotelName = gui.getNewHotelName();
                if (newHotelName.trim().isEmpty()) {
                    gui.showErrorMessage("Please enter a new hotel name.");
                } else {
                    renameHotel(newHotelName);
                    gui.toggleConfirmModificationDialog(false);
                }
                break;
    
            case "Submit New Rooms":
                processNewRooms();
                gui.toggleConfirmModificationDialog(false);
                gui.clearAddRoomsTf();
                break;
    
            case "Remove Selected Room":
                processRoomRemoval();
                gui.toggleConfirmModificationDialog(false);
                break;
    
            case "Submit New Room Price":
                processRoomPriceUpdate();
                gui.toggleConfirmModificationDialog(false);
                gui.clearUpdateRoomPriceTf();
                break;
    
            case "Modify Date Price":
                processDPM();
                gui.toggleConfirmModificationDialog(false);
                gui.clearPercentageMDP();
                break;
    
            case "Submit Remove Reservation":
                processRemoveReservation();
                gui.toggleConfirmModificationDialog(false);
                gui.clearRemoveReservationTf();
                break;
    
            case "Submit Remove Hotel":
                processRemoveHotel();
                gui.toggleConfirmModificationDialog(false);
                break;
    
            default:
                System.out.println("Unknown modification command: " + currentModification); // Debugging
                break;
        }
    }

    public void handleCancelModification() {
        switch (currentOperation) {
            case "Change Hotel Name":
            case "Add Rooms":
            case "Remove Rooms":
            case "Update Room Price":
            case "Date Price Modifier":
            case "Remove Reservation":
            case "Remove Hotel":
                gui.toggleHotelSelectionDialog(false);
                gui.toggleManageHotelMenu(true);
                clearAllTextFields();
                break;
    
            default:
                System.out.println("Unknown operation command: " + currentOperation); // Debugging
                break;
        }
    }
    
    // checks if the hotel name is unique
    public boolean isUniqueName(String hotelName) {
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                return false;
            }
        }
        return true;
    }

    public boolean isUniqueHotelName(String hotelName) {
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                return false;
            }
        }
        return true;
    }

    public int parseRoomCount(String roomCount) {
        try {
            roomCount = roomCount.trim();
            int num = Integer.parseInt(roomCount);
            if (num < 0 || num > 50) {
                return -1;
            }
            return num;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processFinishCreateHotel() {
        System.out.println("button clicked!"); // debugging
        System.out.println("Hotel count: " + hotelCount); // debugging
        
        String hotelName = gui.getHotelName();
        String stdRoomsStr = gui.getStdRooms();
        String dlxRoomsStr = gui.getDlxRooms();
        String execRoomsStr = gui.getExecRooms();
        
        if (hotelName.trim().isEmpty() || stdRoomsStr.trim().isEmpty() ||
            dlxRoomsStr.trim().isEmpty() || execRoomsStr.trim().isEmpty()) {
            gui.showErrorMessage("Please fill out all fields.");
            return;
        }
        
        int stdRooms = parseRoomCount(stdRoomsStr);
        int dlxRooms = parseRoomCount(dlxRoomsStr);
        int execRooms = parseRoomCount(execRoomsStr);
        
        if (stdRooms == -1 || dlxRooms == -1 || execRooms == -1) {
            gui.showErrorMessage("Please enter valid integers for the room amounts.");
            return;
        }
        
        int totalRooms = stdRooms + dlxRooms + execRooms;
        if (totalRooms > 50) {
            gui.showErrorMessage("Total number of rooms cannot exceed 50.");
            return;
        }
        
        if (!isUniqueName(hotelName)) {
            gui.showErrorMessage("Hotel name already exists.");
            return;
        }

        Hotel newHotel = new Hotel(hotelName);
        hotelList.add(newHotel);
        gui.updateHotelComboBox(hotelList);
        hotelCount++;
        System.out.println("Hotel count: " + hotelCount); // debugging
        
        
        int newRooms = stdRooms + dlxRooms + execRooms;
        newHotel.addHotelRooms(newRooms, stdRooms, dlxRooms, execRooms);
        System.out.println("Total rooms after adding: " + newHotel.getNRooms()); // debugging
        gui.showConfirmationMessage("Successfully created Hotel " + hotelName + "!");
        gui.toggleCreateHotelDialog(false);
        gui.clearCreateHotelTF();
    }

    private void renameHotel(String hotelName) {
        if (isUniqueHotelName(hotelName) == false){
            gui.showErrorMessage("This hotel name already exists.");
        }
        else {
            String selectedHotel = gui.getSelectedHotelFromComboBox();
            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(selectedHotel)) {
                    hotel.setHotelName(hotelName);
                    gui.updateHotelComboBox(hotelList);
                }
            }
        }
        gui.clearChangeHotelNameTF();
        gui.showConfirmationMessage("Hotel name changed to " + hotelName + ".");
    }

    private void processNewRooms() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                // get the room counts from the text fields
                String stdRoomsStr = gui.getNewStdRooms();
                String dlxRoomsStr = gui.getNewDlxRooms();
                String execRoomsStr = gui.getNewExecRooms();
    
                // debugging; print input strings
                System.out.println("Input strings:");
                System.out.println("Standard rooms string: '" + stdRoomsStr + "'");
                System.out.println("Deluxe rooms string: '" + dlxRoomsStr + "'");
                System.out.println("Executive rooms string: '" + execRoomsStr + "'");
    
                // convert the string inputs to integers
                int stdRooms = parseRoomCount(stdRoomsStr);
                int dlxRooms = parseRoomCount(dlxRoomsStr);
                int execRooms = parseRoomCount(execRoomsStr);
    
                // Debugging: print parsed values
                System.out.println("Parsed values:");
                System.out.println("Standard rooms: " + stdRooms);
                System.out.println("Deluxe rooms: " + dlxRooms);
                System.out.println("Executive rooms: " + execRooms);
    
                // check if the inputs are valid
                if (stdRooms == -1 || dlxRooms == -1 || execRooms == -1) {
                    gui.showErrorMessage("Please enter valid integers for the room amounts.");
                    return;
                } else if (hotel.getNRooms() + stdRooms + dlxRooms + execRooms > 50) {
                    gui.showErrorMessage("Total number of rooms cannot exceed 50.");
                    return;
                } else {
                    int newRooms = stdRooms + dlxRooms + execRooms;
                    hotel.addHotelRooms(newRooms, stdRooms, dlxRooms, execRooms);
                    // debugging
                    System.out.println("Rooms to add: " + newRooms);
                    System.out.println("Total rooms after adding: " + hotel.getNRooms());
                    gui.showConfirmationMessage(selectedHotel + " now has " + hotel.getNRooms() + " rooms.");
                    gui.toggleAddRoomsDialog(false);
                    gui.clearAddRoomsTf();
                }
            }
        }
    }

    public void displayRoomRemovalInfo() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String roomInfo = hotel.getRoomInfoForRemoval();
                gui.setRoomRemovalTextArea(roomInfo);
            }
        }
    }

    public void processRoomRemoval() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        System.out.println("Selected Hotel: " + selectedHotel); // debugging
    
        String selectedRoom = gui.getRoomToRemove();
        System.out.println("Selected Room: " + selectedRoom); // debugging
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                int canBeRemoved = hotel.removeRoomFromHotel(selectedRoom);
                if (canBeRemoved == -1) {
                    gui.showErrorMessage("Room " + selectedRoom + " cannot be removed from this hotel.");
                    return;
                }
                gui.showConfirmationMessage("Room " + selectedRoom + " has been removed from Hotel " + selectedHotel + ".");
                return; // exit after removing room
            }
        }
    }
    

    private void populateRemoveRoomsComboBox() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        JComboBox<String> removeRoomsComboBox = gui.getRemoveRoomsCBox();
        removeRoomsComboBox.removeAllItems(); // Clear existing items
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                ArrayList<Room> rooms = hotel.getRooms();
                for (Room room : rooms) {
                    if (room.getNReservations() == 0) {
                        removeRoomsComboBox.addItem(room.getRoomName()); // Add available rooms for removal to combo box
                    }
                }
            }
        }
    }

    public double parseDouble(String newPrice) {
        try {
            newPrice = newPrice.trim();
            double price = Double.parseDouble(newPrice);
            if (price < 100.00) {
                return -1;
            }
            return price;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void processRoomPriceUpdate() {
        String newRoomBasePriceStr = gui.getNewRoomBasePrice();
        double newRoomBasePrice = parseDouble(newRoomBasePriceStr);

        if (newRoomBasePrice == -1) {
            gui.showErrorMessage("Invalid room price. Please enter a value greater than 100.00.");
            return;
        }
        else {
            String selectedHotel = gui.getSelectedHotelFromComboBox();
            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(selectedHotel)) {
                    hotel.setBaseRate(newRoomBasePrice);
                    gui.showConfirmationMessage("Room price updated to " + newRoomBasePrice + " for Hotel " + selectedHotel + ".");
                    System.out.println("New room price: " + hotel.getBaseRate()); // debugging
                    gui.toggleUpdateRoomPriceDialog(false);
                    gui.clearUpdateRoomPriceTf();
                }
            }
        }
    }

    public void setMDPTextArea() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String mdpInfo = hotel.getMultiplierDatabaseToString();
                gui.setModifyDatePriceTextArea(mdpInfo);
            }
        }
    }

    public int parseInteger(String intInput) {
        try {
            intInput = intInput.trim();
            int num = Integer.parseInt(intInput);
            if (num < 50 || num > 150) {
                return -1;
            }
            return num;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void populateDPMComboBox() {
        JComboBox<Integer> dateComboBox = gui.getDateCBox();
        dateComboBox.removeAllItems();
        for (int i = 1; i <= 30; i++) {
            dateComboBox.addItem(i);
        }
    }

    public void processDPM() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (selectedHotel.equals(hotel.getHotelName())) {
                // convert sting input from % TA to double
                String newMultiplierStr = gui.getPercentageMDP();
                int newMultiplier = parseInteger(newMultiplierStr);

                // get input from starting date combo box
                int startDate = gui.getSelectedDateMDP();

                // check if the input is valid
                if (newMultiplier == -1) {
                    gui.showErrorMessage("Invalid percentage. Please enter a value from 50 - 150.");
                    return;
                }
                else {
                    hotel.datePriceModifierGUI(startDate, newMultiplier);
                    setMDPTextArea();
                    gui.showConfirmationMessage("Price modifier on Days " + startDate + " - " + (startDate + 1) + " updated to " + newMultiplier + "% for Hotel " + selectedHotel + ".");
                }
            }
        }
    }

    public void processRemoveReservation() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        System.out.println("Selected hotel: " + selectedHotel); // debugging
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                System.out.println("Found hotel: " + hotel.getHotelName()); // debugging
                ArrayList<Reservation> reservationList = hotel.getAllReservations();
                String reservationToRemove = gui.getBookingID();
                System.out.println("Reservation to remove: " + reservationToRemove); // debugging
    
                for (Reservation reservation : reservationList) {
                    System.out.println("Checking reservation: " + reservation.getBookingID()); // debugging
                    if (reservationToRemove.equals(reservation.getBookingID())) {
                        System.out.println("Found reservation: " + reservation.getBookingID()); // debugging
                        int successfulBookingRemoval = hotel.removeReservationGUI(reservationToRemove, reservation.getCheckInDate(), reservation.getCheckOutDate());
                        switch (successfulBookingRemoval) {
                            case 1:
                                gui.showConfirmationMessage("Reservation " + reservationToRemove + " has been removed.");
                                break;
                            case -1:
                                gui.showErrorMessage("Reservation " + reservationToRemove + " not found.");
                                break;
                            default:
                                gui.showErrorMessage("An unknown error occurred.");
                                break;
                        }
                        return; // exit after processing reservation removal
                    }
                }
                System.out.println("Reservation not found: " + reservationToRemove); // debugging
                gui.showErrorMessage("Reservation " + reservationToRemove + " not found.");
                return;
            }
        }
        System.out.println("Hotel not found: " + selectedHotel); // debugging
        gui.showErrorMessage("Hotel " + selectedHotel + " not found.");
    }

    public void processRemoveHotel() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        Iterator<Hotel> hotelIterator = hotelList.iterator(); // ensure no concurrent modification error occurs
        while (hotelIterator.hasNext()) {
            Hotel hotel = hotelIterator.next();
            if (hotel.getHotelName().equals(selectedHotel)) {
                hotelIterator.remove();
                gui.showConfirmationMessage("Hotel " + selectedHotel + " has been removed.");
                return; // exit the method after removing the hotel
            }
        }
        gui.showErrorMessage("Hotel " + selectedHotel + " not found.");
    }

    public void populateDateComboBox() { // will add 1-31 as elements of the combo box for date selection
        JComboBox<String> dateComboBox = gui.getDateComboBox();
        dateComboBox.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            dateComboBox.addItem(Integer.toString(i));
        }
    }

    public void setHotelInfoTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String hotelInfo = hotel.getHotelInformation();
                gui.setViewHotelTA(hotelInfo);
            }
        }
    }

    public int getAvailableRoomsOnDate() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedDateStr = gui.getSelectedDate();
        int selectedDate = Integer.parseInt(selectedDateStr);

        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                return hotel.roomsAvailableOnDate(selectedDate);
            }
        }
        return -1;
    }

    public void populateRoomsToViewCBox() {
        // clear existing items in the combo box
        gui.clearRoomComboBox();

        // populate the combo box with available rooms
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(gui.getSelectedHotelFromComboBox())) {
                List<Room> availableRooms = hotel.getRooms();
                for (Room room : availableRooms) {
                    gui.addRoomToViewComboBox(room.getRoomName());
                }
                break; // exit after updating the combo box for the selected hotel
            }
        }
    }

    public void populateReservationCBox() {
        gui.clearReservationComboBox();

        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(gui.getSelectedHotelFromComboBox())) {
                List<Reservation> reservations = hotel.getAllReservations();
                for (Reservation reservation : reservations) {
                    String reservationInfo = "[" + reservation.getRoomName() + "] " + reservation.getGuestName();
                    gui.addReservationToComboBox(reservationInfo);
                }
                break;
            }
        }
    }

    public void setRoomInfoTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getSelectedRoom();
        System.out.println("Selected hotel: " + selectedHotel); // debugging
        System.out.println("Selected room: " + selectedRoom); // debugging
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                System.out.println("Found hotel: " + hotel.getHotelName()); // debugging
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomName().equals(selectedRoom)) {
                        System.out.println("Found room: " + room.getRoomName()); // debugging
                        String roomInfo = room.getRoomInfoForViewing();
                        gui.setViewRoomInfoTA(roomInfo);
                        System.out.println("Room info: " + roomInfo); // debugging
                        return;
                    }
                }
                System.out.println("Room not found: " + selectedRoom); // debugging
                return;
            }
        }
        System.out.println("Hotel not found: " + selectedHotel); // debugging
        System.out.println("Unsuccessful room info retrieval"); // debugging
    }
    
    public void setRoomToViewTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getSelectedRoom();
        System.out.println("Selected hotel: " + selectedHotel); // debugging
        System.out.println("Selected room: " + selectedRoom); // debugging
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                System.out.println("Found hotel: " + hotel.getHotelName()); // debugging
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomName().equals(selectedRoom)) {
                        System.out.println("Found room: " + room.getRoomName()); // debugging
                        String roomInfo = room.getRoomAvailabilityForViewing();
                        gui.setViewRoomAvailabilityTA(roomInfo);
                        System.out.println("Room info: " + roomInfo); // debugging
                        return;
                    }
                }
                System.out.println("Room not found: " + selectedRoom); // debugging
                return;
            }
        }
        System.out.println("Hotel not found: " + selectedHotel); // debugging
        System.out.println("Unsuccessful room info retrieval"); // debugging
    }

    public void setReservationToViewTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (Room room : hotel.getRooms()) {
                    ArrayList<Reservation> reservations = room.getReservations();
                    String reservationInfo;
                    for (Reservation reservation : reservations) {
                        String chosenReservation = "[" + reservation.getRoomName() + "] " + reservation.getGuestName();
                        if (chosenReservation.equals(gui.getSelectedReservation())) {
                            reservationInfo = reservation.reservationInformationGUI();
                            gui.setViewReservationTA(reservationInfo);
                        }
                    }
                }
            }
        }
    }

    public void populateCheckInOutCb() {
        JComboBox<Integer> checkInCb = gui.getCheckInComboBox();
        JComboBox<Integer> checkOutCb = gui.getCheckOutComboBox();
        checkInCb.removeAllItems();
        checkOutCb.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            checkInCb.addItem(i);
            checkOutCb.addItem(i);
        }
    }

    public void processSimulateBooking() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getRoomToBook();
        String guestName = gui.getGuestName();
        int checkInDate = gui.getCheckInDate();
        int checkOutDate = gui.getCheckOutDate();
        String discountCode = gui.getDiscountCode();
        int isValidDiscount = 0;
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                ArrayList<Room> availableRooms = hotel.checkRoomAvailability(checkInDate, checkOutDate);
                boolean roomAvailable = false;
                for (Room room : availableRooms) {
                    if (selectedRoom.equals(room.getRoomName())) {
                        roomAvailable = true;
                        break;
                    } // how do i know if selectedroom is in availablerooms??
                }
                
                if (!roomAvailable) {
                    gui.showErrorMessage("Room " + selectedRoom + " is not available on the selected dates.");
                }
                else {
                    int result = hotel.isValidReservationGUI(guestName, checkInDate, checkOutDate, selectedRoom, discountCode, availableRooms);
                    System.out.println("Result: " + result); // debugging

                    switch(result) {
                        case -1:
                            gui.showErrorMessage("Invalid date range. Check-in date must be before check-out date.");
                            break;
                            case -2:
                            gui.showErrorMessage("Please enter a guest name for the booking.");
                            break;
                        case -3:
                            String bookingReceipt1 = hotel.bookRoomGUI(guestName, checkInDate, checkOutDate, selectedRoom, discountCode, isValidDiscount);
                            JOptionPane.showMessageDialog(null, "No discount code used. Proceeding with booking...");
                            gui.showConfirmationMessage("Successfully booked Room " + selectedRoom + " for " + guestName + "!");
                            gui.setBookingReceiptTextArea(bookingReceipt1);
                            gui.setHotelNameBooked(selectedHotel);
                            gui.toggleBookingReceiptDialog(true);
                            gui.toggleSimulateBookingDialog(false);
                            System.out.println("Booking receipt: " + bookingReceipt1); // debugging
                            break;
                        case -4:
                            gui.showErrorMessage("Invalid discount code. Please enter a valid discount code.");
                            break;
                        case -5:
                            gui.showErrorMessage("Error: Room " + selectedRoom + " not found.");
                            break;
                        case 1:
                            String bookingReceipt = hotel.bookRoomGUI(guestName, checkInDate, checkOutDate, selectedRoom, discountCode, isValidDiscount);
                            if (isValidDiscount != -1) {
                                gui.showConfirmationMessage("Successfully booked Room " + selectedRoom + " for " + guestName + "!");
                                gui.setBookingReceiptTextArea(bookingReceipt);
                                String receiptTitle = "Hotel " + selectedHotel;
                                gui.setHotelNameBooked(receiptTitle);
                                gui.toggleBookingReceiptDialog(true);
                                gui.toggleSimulateBookingDialog(false);
                                System.out.println("Booking receipt: " + bookingReceipt); // debugging
                                break;
                            }
                        break;          
                    }
                }
            }
        }
    }

    public void populateAvailableRoomsToBookCBox() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        JComboBox<String> roomCBox = gui.getRoomToBookCBox();
        roomCBox.removeAllItems();

        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (Room room : hotel.getRooms()) {
                    if (room.getNReservations() < 31) {
                        roomCBox.addItem(room.getRoomName());
                    }
                }
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'insertUpdate'");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'removeUpdate'");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }
}
