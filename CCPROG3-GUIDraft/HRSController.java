import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HRSController implements ActionListener, DocumentListener {
    private HotelReservationGUI gui;
    private ArrayList<Hotel> hotelList;
    private int hotelCount;
    private String currentOperation = "";

    public HRSController(HotelReservationGUI gui, ArrayList<Hotel> hotelList, int hotelCount) {
        this.gui = gui;
        this.hotelList = hotelList;
        this.hotelCount = hotelCount;
        this.currentOperation = "";
        
        gui.setActionListener(this);
        gui.setDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {}
    @Override
    public void removeUpdate(DocumentEvent e) {}
    @Override
    public void changedUpdate(DocumentEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action Command: " + command); // Debugging
    
        switch (command) {
            case "Create Hotel":
                gui.toggleCreateHotelDialog(true);
                break;
    
            case "Finish Create Hotel":
                processFinishCreateHotel();
                break;
    
            case "Manage Hotel":
                if (hotelList.isEmpty()) {
                    gui.showErrorMessage("No hotels to manage.");
                    return;
                }
                gui.toggleManageHotelMenu(true);
                break;
    
            case "Change Hotel Name":
                currentOperation = "Change Hotel Name";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Submit New Hotel Name":
                System.out.println("Submit new hotel name clicked"); // Debugging
                String newHotelName = gui.getNewHotelName();
                renameHotel(newHotelName);
                gui.toggleChangeHotelNameDialog(false);
                gui.clearChangeHotelNameTF();
                break;
    
            case "Add Rooms":
                currentOperation = "Add Rooms";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Submit New Rooms":
                System.out.println("Submit new rooms button clicked"); // Debugging
                processNewRooms();
                break;
    
            case "Remove Rooms":
                currentOperation = "Remove Rooms";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Update Room Price":
                currentOperation = "Update Room Price";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Remove Reservation":
                currentOperation = "Remove Reservation";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Remove Hotel":
                currentOperation = "Remove Hotel";
                gui.toggleManageHotelMenu(false);
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "View Hotel":
                if (hotelList.isEmpty()) {
                    gui.showErrorMessage("No hotels to view.");
                    return;
                }
                currentOperation = "View Hotel";
                gui.toggleHotelSelectionDialog(true);
                break;
    
            case "Simulate Booking":
                if (hotelList.isEmpty()) {
                    gui.showErrorMessage("No hotels available for booking.");
                    return;
                }
                gui.toggleManageHotelMenu(false);
                populateCheckInOutCb();
                populateAvailableRoomsToBookCBox();
                setSimBookingTA();
                gui.toggleSimulateBookingDialog(true);
                break;
    
            case "Select Hotel":
                switch (currentOperation) {
                    case "Change Hotel Name":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleChangeHotelNameDialog(true);
                        break;
    
                    case "Add Rooms":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleAddRoomsDialog(true);
                        break;
    
                    case "Remove Rooms":
                        String selectedHotel = gui.getSelectedHotelFromComboBox();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(selectedHotel)) {
                                if (hotel.getNRooms() == 0) {
                                    gui.showErrorMessage("There are currently no rooms in this hotel.");
                                    gui.toggleHotelSelectionDialog(false);
                                    return;
                                }
                            }
                        }
                        displayRoomRemovalInfo();
                        populateRemoveRoomsComboBox();
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveRoomsFrame(true);
                        break;
    
                    case "Update Room Price":
                        String selectedHotel1 = gui.getSelectedHotelFromComboBox();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(selectedHotel1)) {
                                if (hotel.getNRooms() == 0) {
                                    gui.showErrorMessage("There are currently no rooms in this hotel.");
                                    gui.toggleHotelSelectionDialog(false);
                                    return;
                                }
                            }
                        }
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleUpdateRoomPriceDialog(true);
                        break;
    
                    case "Remove Reservation":
                        for (Hotel hotel : hotelList) {
                            if (hotel.getAllReservations().isEmpty()) {
                                gui.showErrorMessage("There are currently no reservations in this hotel.");
                                gui.toggleHotelSelectionDialog(false);
                                return;
                            }
                        }
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveReservationDialog(true);
                        break;
    
                    case "Remove Hotel":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveHotelDialog(true);
                        break;
    
                    case "View Hotel":
                        gui.toggleHotelSelectionDialog(false);
                        setHotelInfoTA();
                        gui.toggleViewHotelMenu(true);
    
                    default:
                        System.out.println("Unknown currentOperation: " + currentOperation); // Debugging
                        break;
                }
                break;

                case "Submit New Room Price":
                processRoomPriceUpdate();
                break;

                case "View Date":
                for (Hotel hotel : hotelList) {
                    if (hotel.getNRooms() == 0) {
                        gui.showErrorMessage("There are currently no rooms in this hotel.");
                        return;
                    }
                }
                populateDateComboBox();
                gui.toggleViewHotelMenu(false);
                gui.toggleViewDateDialog(true);
                break;
    
                case "View Room":
                for (Hotel hotel : hotelList) {
                    if (hotel.getNRooms() == 0) {
                        gui.showErrorMessage("No rooms available for viewing.");
                        return;
                    }
                }
                gui.toggleViewHotelMenu(false);
                populateRoomsToViewCBox();
                gui.toggleSelectRoomDialog(true);
                break;

                case "Select Room":
                gui.toggleSelectRoomDialog(false);
                setRoomInfoTA();
                setRoomToViewTA();
                gui.toggleViewRoomsFrame(true);
                break;
        
                case "View Reservation":
                for (Hotel hotel : hotelList) {
                    if (hotel.getAllReservations().isEmpty()) {
                        gui.showErrorMessage("No reservations available for viewing.");
                        return;
                    }
                }
                gui.toggleViewHotelMenu(false);
                gui.toggleViewReservationDialog(true);
                break;

                case "Remove Selected Room":
                processRoomRemoval();
                gui.toggleRemoveRoomsFrame(false);
                break;

                case "Submit Remove Hotel":
                processRemoveHotel();
                gui.toggleRemoveHotelDialog(false);
                break;

                case "Select Date":
                int availableRooms = getAvailableRoomsOnDate();
                if (availableRooms == 0) {
                    gui.showErrorMessage("No rooms available on this date.");
                    return;
                }
                else {
                    gui.showConfirmationMessage("There are " + availableRooms + " rooms available on this date.");
                    gui.toggleViewDateDialog(false);
                }
                break;

                case "Submit View Reservation":
                break;

                case "Book Room":
                processSimulateBooking();
                gui.toggleSimulateBookingDialog(false);
                gui.clearSimulateBookingTF();
                break;

            default:
                System.out.println("Unknown action command: " + command); // Debugging
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
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (Room room : hotel.getRooms()) {
                    gui.addRoomToViewComboBox(room.getRoomName());
                }
            }
        }
    }

    public void setRoomInfoTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getSelectedRoomToView();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomName().equals(selectedRoom)) {
                        String roomInfo = room.getRoomInfoForViewing();
                        gui.setViewRoomInfoTA(roomInfo);
                    }
                }
            }
        }
    }

    public void setRoomToViewTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getSelectedRoomToView();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomName().equals(selectedRoom)) {
                        String roomInfo = room.getRoomAvailabilityForViewing();
                        gui.setViewRoomAvailabilityTA(roomInfo);
                    }
                }
            }
        }
    }

    public void populateAvailableRoomsToBookCBox() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        JComboBox<String> roomToBookCBox = gui.getRoomToBookCBox();
        roomToBookCBox.removeAllItems();
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                for (int i = 1; i <= hotel.getNRooms(); i++) { 
                    Room room = hotel.getRooms().get(i - 1); 
                    if (room.isAvailable(i)) {
                        roomToBookCBox.addItem(room.getRoomName());
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

    public void setSimBookingTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String roomInfo = hotel.printAvailableRoomsToBook();
                gui.setSimBookingTextArea(roomInfo);
            }
        }
    }
    

    public void processSimulateBooking() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        String selectedRoom = gui.getRoomToBook();

        String guestName = gui.getGuestName();
        int checkInDate = gui.getCheckInDate();
        int checkOutDate = gui.getCheckOutDate();
        String discountCode = gui.getDiscountCode();

        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                int result = hotel.bookRoomGUI(guestName, checkInDate, checkOutDate, selectedRoom, discountCode);

                switch (result) {
                    case -1:
                        gui.showErrorMessage("Invalid date range. Check-in date must be before check-out date.");
                        break;
                    case 0:
                        gui.showErrorMessage("Booking failed. Please check the room number and availability.");
                        break;
                    case 1:
                        gui.showConfirmationMessage("Successfully booked Room " + selectedRoom + " for " + guestName + "!");
                        break;
                }

                return; // Exit after processing the selected hotel
            }
        }
    }
}
