import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HRSController implements ActionListener, DocumentListener {
    private HotelReservationGUI gui;
    private ArrayList<Hotel> hotelList;
    private IntWrapper hotelCount;
    private Hotel selectedHotel; // Track selected hotel

    public HRSController(HotelReservationGUI gui, ArrayList<Hotel> hotelList, IntWrapper hotelCount) {
        this.gui = gui;
        this.hotelList = hotelList;
        this.hotelCount = hotelCount;
        
        gui.setActionListener(this);
        gui.setDocumentListener(this);
    }

    // Checks if the hotel name is unique
    public boolean isUniqueName(String hotelName) {
        return hotelList.stream().noneMatch(hotel -> hotel.getHotelName().equals(hotelName));
    }

    // Method for parsing string inputs to integers for room counts
    public int parseRoomCount(String roomCount) {
        try {
            return Integer.parseInt(roomCount);
        } catch (NumberFormatException ex) {
            return -999; // if invalid integer
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {}
    @Override
    public void removeUpdate(DocumentEvent e) {}
    @Override
    public void changedUpdate(DocumentEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Create Hotel":
                gui.toggleCreateHotelDialog(true);
                break;
                
            case "Finish Create Hotel":
                processFinishCreateHotel();
                break;
                
            case "Manage Hotel":
                processManageHotel();
                break;
                
            case "Change Hotel Name":
                gui.toggleChangeHotelNameDialog(true);
                break;
                
            case "Select Hotel":
                processSelectHotel();
                break;
                
            case "Submit New Hotel Name":
                processSubmitNewHotelName();
                break;
                
            case "Add Rooms":
                gui.toggleAddRoomsDialog(true);
                break;
                
            default:
                break;
        }
    }

    private void processFinishCreateHotel() {
        System.out.println("button clicked!"); // debugging
        System.out.println("Hotel count: " + hotelCount.value); // debugging
        
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
        
        if (stdRooms == -999 || dlxRooms == -999 || execRooms == -999) {
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
        hotelCount.value++;
        System.out.println("Hotel count: " + hotelCount.value); // debugging
        System.out.println("Total room count after adding: " + totalRooms); // debugging
        
        newHotel.addRoomToHotel(totalRooms, stdRooms, dlxRooms, execRooms);
        gui.showConfirmationMessage("Successfully created Hotel " + hotelName + "!");
        gui.toggleCreateHotelDialog(false);
        gui.clearCreateHotelTF();
    }

    private void processSelectHotel() {
        String selectedHotelName = gui.getSelectedHotel();
        if (selectedHotelName == null) {
            gui.showErrorMessage("Please select a hotel.");
            return;
        }
        
        selectedHotel = hotelList.stream()
                .filter(hotel -> hotel.getHotelName().equals(selectedHotelName))
                .findFirst()
                .orElse(null);

        if (selectedHotel != null) {
            gui.toggleHotelSelectionDialog(false);
            gui.toggleChangeHotelNameDialog(true);
        }
    }

    private void processSubmitNewHotelName() {
        String newHotelName = gui.getNewHotelName();
        System.out.println("Submit button clicked"); // debugging
        
        if (newHotelName.trim().isEmpty()) {
            gui.showErrorMessage("Please enter a new hotel name.");
            return;
        }
        
        if (!isUniqueName(newHotelName)) {
            gui.showErrorMessage("Hotel name already exists.");
            return;
        }
        
        if (selectedHotel != null) {
            selectedHotel.setHotelName(newHotelName);
            gui.showConfirmationMessage("Hotel name updated successfully.");
            gui.updateHotelComboBox(hotelList);
            gui.toggleChangeHotelNameDialog(false);
        }
    }

    private void processManageHotel() {
        gui.toggleHotelSelectionDialog(true);
        gui.getSelectedHotel();
    }
}
