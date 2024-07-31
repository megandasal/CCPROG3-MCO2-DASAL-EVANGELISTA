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

    /**
     * Constructor for the HRSController class
     * @param gui The GUI object
     * @param hotelList The list of hotels
     * @param hotelCount The number of hotels
     */
    public HRSController(HotelReservationGUI gui, ArrayList<Hotel> hotelList, int hotelCount) {
        this.gui = gui;
        this.hotelList = hotelList;
        this.hotelCount = hotelCount;
        this.currentOperation = "";
        
        gui.setActionListener(this); // connect listeners via gui class
        gui.setDocumentListener(this);
    }

    /**
     * Method to handle action events from the GUI
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    System.out.println("Action Command: " + command); // Debugging
    
    SwingUtilities.invokeLater(() -> {
        hideAllDialogs();
        
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
                gui.toggleConfirmModificationDialog(false);
                break;

            case "Cancel Modification":
                handleCancelModification();
                gui.toggleConfirmModificationDialog(false);
                JOptionPane.showMessageDialog(null, "Modification cancelled.");
                break;

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
                for (Hotel hotel : hotelList) {
                    if (hotel.getHotelName().equals(gui.getSelectedHotelFromComboBox())) {
                        if (hotel.getAllReservations().isEmpty()) {
                            gui.showErrorMessage("No reservations available for viewing.");
                            return;
                        }
                    }
                }
                populateReservationCBox();
                gui.toggleViewHotelMenu(false);
                gui.toggleViewReservationMenu(true);
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

    /**
     * Handles the selection of a hotel from the combo box and opens the appropriate frame or dialog by toggling its visibility
     */
    public void handleHotelSelection() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        System.out.println("Selected hotel: " + selectedHotel); // Debugging
    
        boolean dialogShown = false;
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                switch (currentOperation) {
                    case "View Hotel":
                        setHotelInfoTA();
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleViewHotelMenu(true);
                        dialogShown = true;
                        break;
    
                    case "Change Hotel Name":
                        gui.toggleChangeHotelNameDialog(true);
                        dialogShown = true;
                        break;
    
                    case "Add Rooms":
                        gui.toggleAddRoomsDialog(true);
                        dialogShown = true;
                        break;
    
                    case "Remove Rooms":
                        gui.setRoomRemovalTextArea(hotel.getRoomInfoForRemoval());
                        populateRemoveRoomsComboBox();
                        gui.toggleRemoveRoomsFrame(true);
                        dialogShown = true;
                        break;
    
                    case "Update Room Price":
                        gui.setRoomBasePriceLabel(hotel.getBaseRate());
                        gui.toggleUpdateRoomPriceDialog(true);
                        dialogShown = true;
                        break;
    
                    case "Date Price Modifier":
                        setMDPTextArea();
                        populateDPMComboBox();
                        gui.toggleModifyDatePriceFrame(true);
                        dialogShown = true;
                        break;
    
                    case "Remove Reservation":
                        gui.toggleRemoveReservationDialog(true);
                        dialogShown = true;
                        break;
    
                    case "Remove Hotel":
                        gui.toggleRemoveHotelDialog(true);
                        dialogShown = true;
                        break;
    
                    case "Simulate Booking":
                        gui.toggleSimulateBookingDialog(true);
                        dialogShown = true;
                        break;
    
                    default:
                        System.out.println("Unknown operation command: " + currentOperation); // Debugging
                        break;
                }
            }
        }
    
        if (!dialogShown) {
            gui.showErrorMessage("No valid hotel selected or action could not be processed.");
        }
    }
    
    /**
     * Hides all dialogs and frames if necessary
     */
    private void hideAllDialogs() {
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

    /**
     * Clears all text fields in the GUI
     */
    private void clearAllTextFields() {
        gui.clearCreateHotelTF();
        gui.clearChangeHotelNameTF();
        gui.clearAddRoomsTf();
        gui.clearUpdateRoomPriceTf();
        gui.clearPercentageMDP();
        gui.clearRemoveReservationTf();
    }
    
    /**
     * Handles the confirmation of a modification via Manage Hotel. The method processes the modification based on the current operation.
     */
    public void handleConfirmModification() {
        boolean validInput = true; // Flag to determine if input is valid
    
        switch (currentModification) {
            case "Submit New Hotel Name":
                String newHotelName = gui.getNewHotelName().trim();
                if (newHotelName.isEmpty()) {
                    gui.showErrorMessage("Please enter a new hotel name.");
                    validInput = false;
                } else {
                    renameHotel(newHotelName);
                }
                break;
    
            case "Submit New Rooms":
                currentModification = "Submit New Rooms";
                String newStdRooms = gui.getNewStdRooms().trim();
                String newDlxRooms = gui.getNewDlxRooms().trim();
                String newExecRooms = gui.getNewExecRooms().trim();
                if (newStdRooms.isEmpty() || newDlxRooms.isEmpty() || newExecRooms.isEmpty()) {
                    gui.showErrorMessage("Please fill in all fields for new rooms.");
                    validInput = false;
                } else {
                    processNewRooms();
                    gui.clearAddRoomsTf();
                }
                break;
    
            case "Remove Selected Room":
                currentModification = "Remove Selected Room";
                processRoomRemoval();
                break;
    
            case "Submit New Room Price":
                currentModification = "Submit New Room Price";
                String newRoomBasePrice = gui.getNewRoomBasePrice().trim();
                if (newRoomBasePrice.isEmpty() || newRoomBasePrice.equals("")) {
                    gui.showErrorMessage("Please enter a new room price.");
                    validInput = false;
                } else {
                    processRoomPriceUpdate();
                    gui.clearUpdateRoomPriceTf();
                }
                break;
    
            case "Modify Date Price":
                String datePriceModifier = gui.getPercentageMDP().trim();
                if (datePriceModifier.isEmpty()) {
                    gui.showErrorMessage("Please enter a date price modifier.");
                    validInput = false;
                } else {
                    processDPM();
                    gui.clearPercentageMDP();
                }
                break;
    
            case "Submit Remove Reservation":
                String bookingID = gui.getBookingID().trim();
                if (bookingID.isEmpty()) {
                    gui.showErrorMessage("Please enter reservation details to remove.");
                    validInput = false;
                } else {
                    processRemoveReservation();
                    gui.clearRemoveReservationTf();
                }
                break;
    
            case "Submit Remove Hotel":
                processRemoveHotel();
                break;
    
            default:
                System.out.println("Unknown modification command: " + currentModification);
                validInput = false;
                break;
        }
    
        //show confirmation dialog if input is valid
        if (validInput) {
            gui.toggleConfirmModificationDialog(true);
        }
    }
    

    /**
     * Handles the cancellation of a modification via Manage Hotel. The method toggles the visibility of the appropriate dialogs and frames and clears the appropriate input fields.
     */
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
    /**
     * Checks if the hotel name is unique
     * @param hotelName The hotel name to check
     * @return True if the hotel name is unique, false otherwise
     */
    public boolean isUniqueName(String hotelName) {
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(hotelName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses the room count from a string
     * @param roomCount The number of rooms as a string
     * @return The number of rooms as an integer, or -1 if the input is invalid
     */
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

    /**
     * Processes the completion of creating a hotel by instantiating a new hotel object and adding it to the hotel list and adding the appropriate number of rooms to the hotel
     */
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

    /**
     * Renames a hotel
     * @param hotelName The new hotel name
     */
    private void renameHotel(String hotelName) {
        if (isUniqueName(hotelName) == false){
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

    /**
     * Processes the addition of new rooms to a hotel
     */
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

    /**
     * Displays the room removal information in the text area displaying rooms available for removal
     */
    public void displayRoomRemovalInfo() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String roomInfo = hotel.getRoomInfoForRemoval();
                gui.setRoomRemovalTextArea(roomInfo);
            }
        }
    }

    /**
     * Processes the removal of a room from a hotel
     */
    public void processRoomRemoval() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        System.out.println("Selected Hotel: " + selectedHotel); // debugging
    
        String selectedRoom = gui.getRoomToRemove();
        System.out.println("Selected Room: " + selectedRoom); // debugging
    
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                int canBeRemoved = hotel.removeRoomFromHotel(selectedRoom);
                if (canBeRemoved == -1 || canBeRemoved == 0) {
                    gui.showErrorMessage("Room " + selectedRoom + " cannot be removed from this hotel.");
                    return;
                }
                gui.showConfirmationMessage("Room " + selectedRoom + " has been removed from Hotel " + selectedHotel + ".");
                return; // exit after removing room
            }
        }
    }
    

    /**
     * Populates the combo box with available rooms for removal
     */
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

    /**
     * Parses a string input to a double
     * @param newPrice The string input to parse
     * @return The parsed double value, or -1 if the input is invalid
     */
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

    /**
     * Processes the update of a room's base price.
     */
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

    /**
     * Sets the text area for the date price modifier, displaying the dates and their respective price multipliers
     */
    public void setMDPTextArea() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String mdpInfo = hotel.getMultiplierDatabaseToString();
                gui.setModifyDatePriceTextArea(mdpInfo);
            }
        }
    }

    /**
     * Parses a string input to an integer
     * @param intInput The string input to parse
     * @return The parsed integer value, or -1 if the input is invalid
     */
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

    /**
     * Populates the date price modifier combo box with dates from 1 to 30
     */
    public void populateDPMComboBox() {
        JComboBox<Integer> dateComboBox = gui.getDateCBox();
        dateComboBox.removeAllItems();
        for (int i = 1; i <= 30; i++) {
            dateComboBox.addItem(i);
        }
    }

    /**
     * Processes the modification of multipliers for a date range
     */
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

    /**
     * Processes the removal of a reservation
     */
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

    /**
     * Processes the removal of a hotel
     */
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

    /**
     * Populates the combo box with dates from 1 to 31
     */
    public void populateDateComboBox() { // will add 1-31 as elements of the combo box for date selection
        JComboBox<String> dateComboBox = gui.getDateComboBox();
        dateComboBox.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            dateComboBox.addItem(Integer.toString(i));
        }
    }

    /**
     * Sets the text area containing information of a hotel
     */
    public void setHotelInfoTA() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                String hotelInfo = hotel.getHotelInformation();
                gui.setViewHotelTA(hotelInfo);
            }
        }
    }

    /**
     * Populates the combo box with available rooms for viewing given a hotel name and date
     * @return The number of available rooms on the selected date
     */
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

    /**
     * Populates the combo box with available rooms for viewing
     */
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

    /**
     * Populates the combo box with reservations for viewing
     */
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

    /**
     * Sets the text area for viewing room information
     */
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
    
    /**
     * Sets the text area for viewing room availability
     */
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

    /**
     * Sets the text area for viewing a reservation
     */
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

    /**
     * Populates the combo box with check in and check out dates
     */
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

    /**
     * Processes a booking made by the user
     */
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
                            gui.showErrorMessage("Invalid date range.");
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

    /**
     * Populates the combo box with available rooms for booking
     */
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
