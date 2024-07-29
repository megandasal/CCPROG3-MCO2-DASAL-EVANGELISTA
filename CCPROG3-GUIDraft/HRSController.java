import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveRoomsFrame(true);
                        break;
    
                    case "Update Room Price":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleUpdateRoomPriceDialog(true);
                        break;
    
                    case "Remove Reservation":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveReservationDialog(true);
                        break;
    
                    case "Remove Hotel":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleRemoveHotelDialog(true);
                        break;
    
                    case "View Hotel":
                        gui.toggleHotelSelectionDialog(false);
                        gui.toggleViewHotelMenu(true);
    
                    default:
                        System.out.println("Unknown currentOperation: " + currentOperation); // Debugging
                        break;
                }
                break;

                case "View Date":
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
                gui.toggleSelectRoomDialog(true);
                break;

                case "Select Room":
                gui.toggleSelectRoomDialog(false);
                gui.toggleViewRoomsFrame(true);
                break;
        
                case "View Reservation":
                gui.toggleViewHotelMenu(false);
                gui.toggleViewReservationDialog(true);
                break;
                
            default:
                System.out.println("Unknown action command: " + command); // Debugging
                break;
        }
    }
    

    // checks if the hotel name is unique
    public boolean isUniqueName(String hotelName) {
        return hotelList.stream().noneMatch(hotel -> hotel.getHotelName().equals(hotelName));
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

        if (roomCount.trim().isEmpty()) {
            return 0; // zero rooms for that type
        }

        try {
            int count = Integer.parseInt(roomCount.trim());
            return count;
        } catch (NumberFormatException ex) {
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
        System.out.println("Total room count after adding: " + totalRooms); // debugging
        
        newHotel.addRoomToHotel(totalRooms, stdRooms, dlxRooms, execRooms);
        gui.showConfirmationMessage("Successfully created Hotel " + hotelName + "!");
        gui.toggleCreateHotelDialog(false);
        gui.clearCreateHotelTF();
    }

    private void renameHotel(String hotelName) {
        for (Hotel hotel : hotelList) {
            if (isUniqueHotelName(hotelName) == false)
                gui.showErrorMessage("This hotel name already exists.");
            else {
                hotel.setHotelName(hotelName);
                gui.updateHotelComboBox(hotelList);
                gui.showConfirmationMessage("Hotel name changed to " + hotelName + ".");
            }
        }
        gui.clearChangeHotelNameTF();
    }

    private void processNewRooms() {
        String selectedHotel = gui.getSelectedHotelFromComboBox();
            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(selectedHotel)) {
                    String stdRoomsStr = gui.getStdRooms();
                    String dlxRoomsStr = gui.getDlxRooms();
                    String execRoomsStr = gui.getExecRooms();

                    // if a field is not filled out, it means no rooms are to be added to that room type
                    int stdRooms = parseRoomCount(stdRoomsStr);
                    int dlxRooms = parseRoomCount(dlxRoomsStr);
                    int execRooms = parseRoomCount(execRoomsStr);

                    if (stdRooms == -1 || dlxRooms == -1 || execRooms == -1) {
                        gui.showErrorMessage("Please enter valid integers for the room amounts.");
                        return;
                    }

                    int totalRoomsToAdd = stdRooms + dlxRooms + execRooms;
                    int newTotalRooms = hotel.getNRooms() + totalRoomsToAdd;

                    if (newTotalRooms > 50) {
                        gui.showErrorMessage("Total number of rooms cannot exceed 50.");
                        return;
                    } else {
                        hotel.addRoomToHotel(totalRoomsToAdd, stdRooms, dlxRooms, execRooms);
                        gui.showConfirmationMessage(selectedHotel + " now has " + newTotalRooms + " rooms.");
                        System.out.println("Total rooms after adding: " + newTotalRooms); // debugging
                        gui.toggleAddRoomsDialog(false);
                        gui.clearAddRoomsTf();
                    }
                    break;
                }
            }
    }
}
