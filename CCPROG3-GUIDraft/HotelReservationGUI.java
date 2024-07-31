import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class HotelReservationGUI extends JFrame {
    // main menu 
    private JButton createHotelBtn;
    private JButton manageHotelBtn;
    private JButton viewHotelBtn;
    private JButton simulateBookingBtn;
    
    // create hotel components
    private JTextField hotelNameTf;
    private JButton createHotelFinBtn; // to proceed after entering the number of rooms and finish the hotel creation process
    private JTextField stdRoomsTf;
    private JTextField dlxRoomsTf;
    private JTextField execRoomsTf;
    private JDialog roomTypeDialog;

    // manage hotel buttons
    private JFrame manageHotelFrame;
    private JButton changeHotelNameBtn;
    private JButton addRoomsBtn;
    private JButton removeRoomsBtn;
    private JButton updRoomPriceBtn;
    private JButton dpmBtn; // date price modifier button
    private JButton removeReservationBtn;
    private JButton removeHotelBtn;
    private JDialog hotelSelectionDialog;
    private JButton selectHotelFinBtn;

    // confirmation dialogs for hotel modifications
    private JDialog confirmModificationDialog;
    private JButton confirmModificationBtn;
    private JButton cancelModificationBtn;

        // change hotel name
        private JDialog changeHotelNameDialog;
        JComboBox<String> hotelComboBox;
        private JButton submitNewHotelNameBtn;
        private JTextField newHotelNameTf;

        // add rooms
        private JDialog addRoomsDialog;
        private JButton submitNewRoomsBtn;
        private JTextField newStdRoomsTf;
        private JTextField newDlxRoomsTf;
        private JTextField newExecRoomsTf;

        // remove rooms
        private JFrame removeRoomsFrame;
        private JButton removeRoomsFinBtn;
        private JComboBox<String> removeRoomsCBox; // combo box for selecting rooms to remove
        private JTextArea removeRoomsTA; // text area for displaying rooms

        // update room base price
        private JDialog updRoomPriceDialog;
        private JTextField newRoomPriceTf;
        private JButton submitNewRoomPriceBtn;
        private JLabel subTitleLbl2; // displays the current room base price

        // date price modifier
        private JFrame datePriceModifierFrame;
        private JComboBox<Integer> dateCBox;
        private JTextField percentageTf;
        private JButton commitChangesButton;
        private JTextArea datePriceModifierTA;

        // remove reservation
        private JDialog removeReservationDialog;
        private JTextField removeReservationTf;
        private JButton submitRemoveReservationBtn;

        // remove hotel
        private JDialog removeHotelDialog;
        private JButton submitRemoveHotelBtn;
        private JButton cancelRemoveHotelBtn;

    // view hotel
    private JFrame viewHotelFrame;
    private JTextArea highLevelTextArea;
    private JButton dateBtn;
    private JButton roomBtn;
    private JButton reservationBtn;

        // view date
        private JDialog viewDateDialog;
        private JComboBox<String> dateComboBox;
        private JButton selectDateBtn;

        // room selection
        private JDialog selectRoomDialog;
        private JComboBox<String> roomComboBox;
        private JButton selectRoomBtn;

        // view room
        private JFrame viewRoomFrame;
        private JTextArea viewRoomTextArea;
        private JTextArea viewRoomTextArea2; // shows the availability of a room on a given date

        // reservation menu
        private JDialog reservationMenuDialog;
        private JButton selectReservationBtn;
        private JComboBox<String> reservationComboBox;

        // view reservation
        private JDialog viewReservationDialog;
        private JTextArea viewReservationTextArea;

    // simulate booking
    private JFrame simulateBookingFrame;
    private JTextField guestNameTf;
    private JComboBox<String> roomToBookCBox;
    private JTextField discountCodeTf;

    // simulate booking input guest name and check in/check out dates
    private JDialog simulateBookingDialog;
    private JTextField guestsNameTf;
    private JComboBox<Integer> checkInDateCBox;
    private JComboBox<Integer> checkOutDateCBox;
    private JButton submitBookingInfoBtn;

    // simulate booking input room and discount code
    private JDialog simulateBookingDialog2;
    private JComboBox<String> roomCBox;
    private JButton submitBookingInfoBtn2;


    // booking receipt components
    private JDialog bookingReceiptDialog;
    private JTextArea bookingReceiptTA;
    private JLabel hotelNameBooked;

    private ArrayList<Hotel> hotelList;

    /**
     * Constructor for the HotelReservationGUI class
     * @param hotelList
     */
    public HotelReservationGUI(ArrayList<Hotel> hotelList) {
        super("Hotel Reservation System");
        setSize(600, 400);
    
        this.hotelList = hotelList;
        
        // initialize main menu
        init();
        initComponents();
    
        // create hotel
        initCreateHotel();
    
        // manage hotel
        initManageHotelMenu();
        hotelSelectionDialog();
        changeHotelNameDialog();
        addRoomsDialog();
        removeRoomsFrame();
        updateRoomPriceDialog();
        datePriceModifierFrame();
        removeReservationDialog();
        removeHotelDialog();

        // view hotel
        viewHotelMenu();
        viewDateDialog();
        selectRoomDialog();
        viewRoomFrame();
        viewReservationMenu();
        viewReservationDialog();

        // simulate booking
        //simulateBookingFrame();
        bookingReceiptDialog();
        simulateBookingDialog();
        simulateBookingDialog2();
    
        confirmModificationDialog();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialize the main menu frame
     */
    private void init() {
        JPanel mainMenuPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        mainMenuPanel.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);

        // String hex = "#ADD8E6";
        // Color bgColor = Color.decode(hex);
        // mainMenuPanel.setBackground(bgColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // align the component to the top of its space

        JLabel menuLabel = new JLabel("Hotel Reservation System");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 28));

        mainMenuPanel.add(menuLabel, gbc);
        this.add(mainMenuPanel);

        createHotelBtn = new JButton("Create Hotel");
        createHotelBtn.setActionCommand("Create Hotel");

        manageHotelBtn = new JButton("Manage Hotel");
        manageHotelBtn.setActionCommand("Manage Hotel");

        viewHotelBtn = new JButton("View Hotel");
        viewHotelBtn.setActionCommand("View Hotel");

        simulateBookingBtn = new JButton("Simulate Booking");
        simulateBookingBtn.setActionCommand("Simulate Booking");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0); // add 5 spaces between the buttons

        mainMenuPanel.add(createHotelBtn, gbc);
        gbc.gridy = 3;

        mainMenuPanel.add(manageHotelBtn, gbc);
        gbc.gridy = 5;

        mainMenuPanel.add(viewHotelBtn, gbc);
        gbc.gridy = 7;

        mainMenuPanel.add(simulateBookingBtn, gbc);

        // reset gbc.insets to avoid affecting other components added later
        gbc.insets = new Insets(0, 0, 0, 0);

        // adjust button sizes
        Dimension maxSize = new Dimension(0, 0);
        JButton[] buttons = {createHotelBtn, manageHotelBtn, viewHotelBtn, simulateBookingBtn};

        for (JButton button : buttons) {
            Dimension buttonSize = button.getPreferredSize();
            if (buttonSize.width > maxSize.width) {
                maxSize.width = buttonSize.width;
            }
            if (buttonSize.height > maxSize.height) {
                maxSize.height = buttonSize.height;
            }
        }

        // set preferred size for all buttons
        for (JButton button : buttons) {
            button.setPreferredSize(maxSize);
        }
        this.setVisible(true);
    }

    /**
     * Initialize the manage hotel components for the GUI
     */
    private void initComponents() {
        // Initialize the buttons
        selectHotelFinBtn = new JButton("Select Hotel");
        changeHotelNameBtn = new JButton("Change Hotel Name");
        addRoomsBtn = new JButton("Add Rooms");
        removeRoomsBtn = new JButton("Remove Rooms");
        updRoomPriceBtn = new JButton("Update Room Price");
        dpmBtn = new JButton("DPM");
        removeReservationBtn = new JButton("Remove Reservation");
        removeHotelBtn = new JButton("Remove Hotel");

        // Initialize other components
        hotelComboBox = new JComboBox<>();
        hotelNameTf = new JTextField();
        stdRoomsTf = new JTextField();
        dlxRoomsTf = new JTextField();
        execRoomsTf = new JTextField();
        hotelList = new ArrayList<>();
        roomTypeDialog = new JDialog(this, "Select Room Types", true);
        changeHotelNameDialog = new JDialog(this, "Change Hotel Name", true);
        submitNewHotelNameBtn = new JButton("Submit");
        newHotelNameTf = new JTextField();

        // Set up the dialog
        hotelSelectionDialog();
    }

    /**
     * Initialize the create hotel dialog
     */
    void initCreateHotel() {
        roomTypeDialog = new JDialog(this);
        roomTypeDialog.setTitle("Create Hotel");
        roomTypeDialog.setSize(500, 350);
        roomTypeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        roomTypeDialog.setLocationRelativeTo(null);
    
        JPanel createHotelMainPanel = new JPanel();
        createHotelMainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // title label
        JLabel titleLabel = new JLabel("Create a Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        createHotelMainPanel.add(titleLabel, gbc);
    
        gbc.gridy++;
    
        JLabel enterRoomsLabel = new JLabel("Enter a hotel name and the amount of rooms for each type.");
        enterRoomsLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Example: Adjust font size and style
        gbc.anchor = GridBagConstraints.CENTER; // Center align within the cell
        createHotelMainPanel.add(enterRoomsLabel, gbc);
    
        gbc.gridy++;
    
        JLabel hotelNameLbl = new JLabel("Hotel Name:");
        gbc.gridx = 0;
        gbc.gridwidth = 1; // reset grid width
        gbc.anchor = GridBagConstraints.EAST;
        createHotelMainPanel.add(hotelNameLbl, gbc);
    
        hotelNameTf = new JTextField(); //10?
        hotelNameTf.setMinimumSize(new Dimension(300, 30));
        hotelNameTf.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        createHotelMainPanel.add(hotelNameTf, gbc);

        gbc.gridy++;

        // labels and text fields for room types and amount
        JLabel stdRoomsLbl = new JLabel("Standard Rooms:");
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        createHotelMainPanel.add(stdRoomsLbl, gbc);
    
        stdRoomsTf = new JFormattedTextField();
        stdRoomsTf.setMinimumSize(new Dimension(50, 30));
        stdRoomsTf.setPreferredSize(new Dimension(50, 30));
        stdRoomsTf.setColumns(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        createHotelMainPanel.add(stdRoomsTf, gbc);
    
        JLabel dlxRoomsLbl = new JLabel("Deluxe Rooms:");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        createHotelMainPanel.add(dlxRoomsLbl, gbc);
    
        dlxRoomsTf = new JFormattedTextField();
        dlxRoomsTf.setMinimumSize(new Dimension(50, 30));
        dlxRoomsTf.setPreferredSize(new Dimension(50, 30));
        dlxRoomsTf.setColumns(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        createHotelMainPanel.add(dlxRoomsTf, gbc);
    
        JLabel execRoomsLbl = new JLabel("Executive Rooms:");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        createHotelMainPanel.add(execRoomsLbl, gbc);
    
        execRoomsTf = new JFormattedTextField();
        execRoomsTf.setMinimumSize(new Dimension(50, 30));
        execRoomsTf.setPreferredSize(new Dimension(50, 30));
        execRoomsTf.setColumns(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        createHotelMainPanel.add(execRoomsTf, gbc);
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createHotelFinBtn = new JButton("Finish");
        createHotelFinBtn.setActionCommand("Finish Create Hotel");
        buttonPanel.add(createHotelFinBtn);
    
        roomTypeDialog.add(createHotelMainPanel, BorderLayout.CENTER);
        roomTypeDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        ((JComponent) roomTypeDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        roomTypeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Initialize the manage hotel menu
     */
    void initManageHotelMenu() {
        manageHotelFrame = new JFrame("Manage Hotel");
        manageHotelFrame.setSize(600, 400);
        manageHotelFrame.setLocationRelativeTo(null);
        JPanel manageHotelMenuPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        manageHotelMenuPanel.setLayout(new GridBagLayout());

        // // change hex code later
        // String hex = "#ADD8E6";
        // Color bgColor = Color.decode(hex);
        // manageHotelMenuPanel.setBackground(bgColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel manageHotelLbl = new JLabel("Manage Hotel");
        manageHotelLbl.setFont(new Font("Arial", Font.BOLD, 28));

        manageHotelMenuPanel.add(manageHotelLbl, gbc);
        this.add(manageHotelMenuPanel);

        /* SET ACTION COMMANDS */
        changeHotelNameBtn = new JButton("Change Hotel Name");
        changeHotelNameBtn.setActionCommand("Change Hotel Name");

        addRoomsBtn = new JButton("Add Rooms");
        addRoomsBtn.setActionCommand("Add Rooms");

        removeRoomsBtn = new JButton("Remove Rooms");
        removeRoomsBtn.setActionCommand("Remove Rooms");

        updRoomPriceBtn = new JButton("Update Room Price");
        updRoomPriceBtn.setActionCommand("Update Room Price");

        dpmBtn = new JButton("Date Price Modifier");
        dpmBtn.setActionCommand("Date Price Modifier");

        removeReservationBtn = new JButton("Remove Reservation");
        removeReservationBtn.setActionCommand("Remove Reservation");

        removeHotelBtn = new JButton("Remove Hotel");
        removeHotelBtn.setActionCommand("Remove Hotel");


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0); // add 5 spaces between the buttons

        manageHotelMenuPanel.add(changeHotelNameBtn, gbc);
        gbc.gridy = 2;

        manageHotelMenuPanel.add(addRoomsBtn, gbc);
        gbc.gridy = 3;

        manageHotelMenuPanel.add(removeRoomsBtn, gbc);
        gbc.gridy = 4;

        manageHotelMenuPanel.add(updRoomPriceBtn, gbc);
        gbc.gridy = 5;

        manageHotelMenuPanel.add(dpmBtn, gbc);
        gbc.gridy = 6;

        manageHotelMenuPanel.add(removeReservationBtn, gbc);
        gbc.gridy = 7;

        manageHotelMenuPanel.add(removeHotelBtn, gbc);

        // reset gbc.insets
        gbc.insets = new Insets(0, 0, 0, 0);

        // adjust button sizes
        Dimension maxSize = new Dimension(0, 0);
        JButton[] buttons = {changeHotelNameBtn, addRoomsBtn, removeRoomsBtn, updRoomPriceBtn, dpmBtn, removeReservationBtn, removeHotelBtn};

        for (JButton button : buttons) {
            Dimension buttonSize = button.getPreferredSize();
            if (buttonSize.width > maxSize.width) {
                maxSize.width = buttonSize.width;
            }
            if (buttonSize.height > maxSize.height) {
                maxSize.height = buttonSize.height;
            }
        }

        // set preferred size for all buttons
        for (JButton button : buttons) {
            button.setPreferredSize(maxSize);
        }

        manageHotelFrame.add(manageHotelMenuPanel);
        manageHotelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the hotel selection dialog for manage and view hotel and simulate booking funcitonalities
     */
    public void hotelSelectionDialog() {
        hotelSelectionDialog = new JDialog();
        hotelSelectionDialog.setTitle("Select a Hotel");
        hotelSelectionDialog.setSize(400, 250);
        hotelSelectionDialog.setLocationRelativeTo(null);
    
        JPanel hotelSelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
    
        JLabel selectHotelLbl = new JLabel("Select a hotel:");
        selectHotelLbl.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span to end
        gbc.anchor = GridBagConstraints.CENTER;
        hotelSelectionPanel.add(selectHotelLbl, gbc);
    
        hotelComboBox = new JComboBox<String>();
        for (Hotel hotel : hotelList) {
            hotelComboBox.addItem(hotel.getHotelName());
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        hotelSelectionPanel.add(hotelComboBox, gbc);
    
        selectHotelFinBtn = new JButton("Select Hotel");
        selectHotelFinBtn.setActionCommand("Select Hotel");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        hotelSelectionPanel.add(selectHotelFinBtn, gbc);
    
        hotelSelectionDialog.add(hotelSelectionPanel);
        hotelSelectionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /*
     * Initialize the dialog for changing the hotel name
     */
    public void changeHotelNameDialog() {
        changeHotelNameDialog = new JDialog();
        changeHotelNameDialog.setTitle("Change Hotel Name");
        changeHotelNameDialog.setSize(400, 150);
        changeHotelNameDialog.setModal(true); 
        changeHotelNameDialog.setLocationRelativeTo(null);

        JLabel changeHotelNameLbl = new JLabel("Enter a new hotel name:");
        changeHotelNameLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        newHotelNameTf = new JTextField(20);

        submitNewHotelNameBtn = new JButton("Submit");
        submitNewHotelNameBtn.setActionCommand("Submit New Hotel Name");

        JPanel changeHotelNamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        changeHotelNamePanel.add(changeHotelNameLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        changeHotelNamePanel.add(newHotelNameTf, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;

        changeHotelNamePanel.add(submitNewHotelNameBtn, gbc);

        changeHotelNameDialog.getContentPane().add(changeHotelNamePanel);
    }

    /**
     * Initialize the dialog for adding rooms to the hotel
     */
    public void addRoomsDialog() {
        addRoomsDialog = new JDialog();
        addRoomsDialog.setTitle("Add Rooms");
        addRoomsDialog.setSize(500, 350);
        addRoomsDialog.setLocationRelativeTo(null);

        // main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // title
        JLabel titleLabel = new JLabel("Add Rooms");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // sub title
        JLabel titleSubLb = new JLabel("Please enter the number of rooms to add for each type.");
        titleSubLb.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        mainPanel.add(titleSubLb, gbc);

        // standard rooms
        JLabel stdRoomsLbl = new JLabel("Standard Rooms:");
        newStdRoomsTf = new JTextField(10);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(stdRoomsLbl, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(newStdRoomsTf, gbc);

        //deluxe Rooms
        JLabel dlxRoomsLbl = new JLabel("Deluxe Rooms:");
        newDlxRoomsTf = new JTextField(10);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(dlxRoomsLbl, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(newDlxRoomsTf, gbc);

        // executive Rooms
        JLabel execRoomsLbl = new JLabel("Executive Rooms:");
        newExecRoomsTf = new JTextField(10);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(execRoomsLbl, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(newExecRoomsTf, gbc);

        // submit btn
        submitNewRoomsBtn = new JButton("Submit");
        submitNewRoomsBtn.setActionCommand("Submit New Rooms");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitNewRoomsBtn, gbc);

        addRoomsDialog.getContentPane().add(mainPanel);
        addRoomsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the frame for removing rooms from the hotel
     */
    public void removeRoomsFrame() {
        removeRoomsFrame = new JFrame("Remove Rooms");
        removeRoomsFrame.setSize(400, 500);
        removeRoomsFrame.setLocationRelativeTo(null);
    
        JPanel removeRoomsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // title
        JLabel removeRoomsLabel = new JLabel("Remove Rooms");
        removeRoomsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        removeRoomsLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        removeRoomsPanel.add(removeRoomsLabel, gbc);
        
        // text area for displaying rooms
        removeRoomsTA = new JTextArea(10, 20);
        removeRoomsTA.setEditable(false); // text area for display only
        removeRoomsTA.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(removeRoomsTA);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        removeRoomsPanel.add(scrollPane, gbc);
    
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel noReservationLabel = new JLabel("[ - ]  no reservation   |   ");
        noReservationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel hasReservationLabel = new JLabel("[ * ]  has reservation");
        hasReservationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusPanel.add(noReservationLabel);
        statusPanel.add(hasReservationLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        removeRoomsPanel.add(statusPanel, gbc);
    
        JLabel selectRoomLabel = new JLabel("Select Room to Remove:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        removeRoomsPanel.add(selectRoomLabel, gbc);
    
        removeRoomsCBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        removeRoomsPanel.add(removeRoomsCBox, gbc);
    
        removeRoomsFinBtn = new JButton("Remove Selected Room");
        removeRoomsFinBtn.setActionCommand("Remove Selected Room");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        removeRoomsPanel.add(removeRoomsFinBtn, gbc);
    
        removeRoomsFrame.add(removeRoomsPanel);
        removeRoomsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Initialize the dialog for updating the room base price
     */
    public void updateRoomPriceDialog() {
        updRoomPriceDialog = new JDialog();
        updRoomPriceDialog.setTitle("Update Room Base Price");
        updRoomPriceDialog.setSize(500, 300);
    
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding

        // title
        JLabel titleLabel = new JLabel("Update Room Base Price");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);
    
        // sub title
        JLabel subTitleLbl = new JLabel("Please enter a new base price for the hotel rooms.");
        subTitleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(subTitleLbl, gbc);
    
        // current room price label
        subTitleLbl2 = new JLabel("Current room base price: ");
        subTitleLbl2.setFont(new Font("Arial", Font.ITALIC, 12));
        subTitleLbl2.setForeground(Color.BLUE);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(subTitleLbl2, gbc);
    
        /* enter code for displaying current room base price? */
    
        // new room price label
        JLabel newRoomPriceLbl = new JLabel("New Room Price:");
        newRoomPriceLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(newRoomPriceLbl, gbc);
    
        // new room price text field
        newRoomPriceTf = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        mainPanel.add(newRoomPriceTf, gbc);
    
        // submit new room price button
        submitNewRoomPriceBtn = new JButton("Submit");
        submitNewRoomPriceBtn.setActionCommand("Submit New Room Price");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(submitNewRoomPriceBtn, gbc);
    
        updRoomPriceDialog.add(mainPanel);
        updRoomPriceDialog.setLocationRelativeTo(null);
        updRoomPriceDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the frame for the date price modifier
     */
    public void datePriceModifierFrame() {
        datePriceModifierFrame = new JFrame("Date Price Modifier");
        datePriceModifierFrame.setSize(600, 750);
        datePriceModifierFrame.setLocationRelativeTo(null);
        
        JPanel datePriceModifierPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        datePriceModifierPanel.add(new JLabel(), gbc);
    
        // title
        JLabel titleLabel = new JLabel("Date Price Modifier");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        datePriceModifierPanel.add(titleLabel, gbc);
    
        // sub title
        JLabel subTitleLbl = new JLabel("Please select a date and enter a percentage to modify the room prices.");
        subTitleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        subTitleLbl.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        datePriceModifierPanel.add(subTitleLbl, gbc);
    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(5, 5, 5, 5); // padding
    
        // date label
        JLabel dateLbl = new JLabel("Select Date:");
        inputGbc.gridx = 0;
        inputGbc.gridy = 0;
        inputGbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(dateLbl, inputGbc);
    
        // date combo box
        dateCBox = new JComboBox<>();
        dateCBox.setPreferredSize(new Dimension(150, 20));
        inputGbc.gridx = 1;
        inputGbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(dateCBox, inputGbc);
    
        // percentage label
        JLabel percentageLbl = new JLabel("Percentage:");
        inputGbc.gridx = 0;
        inputGbc.gridy = 1;
        inputGbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(percentageLbl, inputGbc);
    
        // percentage text field
        percentageTf = new JTextField(13);
        inputGbc.gridx = 1;
        inputGbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(percentageTf, inputGbc);
    
        JLabel percentSignLbl = new JLabel("%");
        inputGbc.gridx = 2;
        inputGbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(percentSignLbl, inputGbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        datePriceModifierPanel.add(inputPanel, gbc);

        datePriceModifierTA = new JTextArea(10, 20);
        datePriceModifierTA.setEditable(false);
        datePriceModifierTA.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(datePriceModifierTA);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        datePriceModifierPanel.add(scrollPane, gbc);

        // commit changes button
        commitChangesButton = new JButton("Finish");
        commitChangesButton.setActionCommand("Modify Date Price");
        commitChangesButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        datePriceModifierPanel.add(commitChangesButton, gbc);
    
        datePriceModifierFrame.add(datePriceModifierPanel);
        datePriceModifierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Initialize the dialog for removing a reservation
     */
    private void removeReservationDialog() {
        removeReservationDialog = new JDialog();
        removeReservationDialog.setTitle("Remove Reservation");
        removeReservationDialog.setSize(400, 150);
        removeReservationDialog.setLocationRelativeTo(null);
    
        JPanel removeReservationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
    
        // label for reservation ID
        JLabel removeReservationLbl = new JLabel("Enter your unique reservation ID:");
        removeReservationLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        removeReservationPanel.add(removeReservationLbl, gbc);
    
        // text field for reservation ID
        removeReservationTf = new JTextField(10);
        gbc.gridy = 1; // Move to the next row
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.WEST;
        removeReservationPanel.add(removeReservationTf, gbc);
    
        // remove reservation button
        submitRemoveReservationBtn = new JButton("Remove Reservation");
        submitRemoveReservationBtn.setActionCommand("Submit Remove Reservation");
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        removeReservationPanel.add(submitRemoveReservationBtn, gbc);
    
        removeReservationDialog.add(removeReservationPanel);
        removeReservationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Initialize the dialog for removing a hotel
     */
    public void removeHotelDialog() {
        removeHotelDialog = new JDialog();
        removeHotelDialog.setTitle("Remove Hotel");
        removeHotelDialog.setSize(400, 150);
        removeHotelDialog.setLocationRelativeTo(null); 
    
        JPanel removeHotelPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        
        // main label
        JLabel removeHotelLbl = new JLabel("Are you sure you want to remove this hotel?");
        removeHotelLbl.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        removeHotelPanel.add(removeHotelLbl, gbc);
    
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        removeHotelPanel.add(new JLabel(), gbc);
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        submitRemoveHotelBtn = new JButton("Remove");
        submitRemoveHotelBtn.setActionCommand("Submit Remove Hotel"); // set action cmds
        cancelRemoveHotelBtn = new JButton("Cancel");
        cancelRemoveHotelBtn.setActionCommand("Cancel Remove Hotel");
        buttonPanel.add(submitRemoveHotelBtn);
        buttonPanel.add(cancelRemoveHotelBtn);
    
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        removeHotelPanel.add(buttonPanel, gbc);
    
        removeHotelDialog.add(removeHotelPanel);
        removeHotelDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the view hotel frame, which displays high-level and low-level information about the hotel
     */
    public void viewHotelMenu() {
    // High-level information panel
    JPanel highLevelPanel = new JPanel();
    highLevelPanel.setLayout(new BoxLayout(highLevelPanel, BoxLayout.Y_AXIS));
    
    JLabel highLevelLabel = new JLabel("HIGH LEVEL HOTEL INFORMATION");
    Font font = new Font("Arial", Font.BOLD, 16);
    highLevelLabel.setFont(font);
    highLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    highLevelTextArea = new JTextArea();
    highLevelTextArea.setEditable(false);
    JScrollPane highLevelScrollPane = new JScrollPane(highLevelTextArea);
    
    // Add components to high-level panel with spacing
    highLevelPanel.add(highLevelLabel);
    highLevelPanel.add(Box.createVerticalStrut(10)); // Add vertical space
    highLevelPanel.add(highLevelScrollPane);
    highLevelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

    // Create buttons panel with 3 buttons horizontally
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Add horizontal space between buttons

    /* SET ACTION COMMANDS */
    dateBtn = new JButton("DATE");
    dateBtn.setActionCommand("View Date");

    roomBtn = new JButton("ROOM");
    roomBtn.setActionCommand("View Room");

    reservationBtn = new JButton("RESERVATION");
    reservationBtn.setActionCommand("View Reservation");

    buttonsPanel.add(dateBtn);
    buttonsPanel.add(roomBtn);
    buttonsPanel.add(reservationBtn);

    // Low-level information panel
    JPanel lowLevelPanel = new JPanel();
    lowLevelPanel.setLayout(new BoxLayout(lowLevelPanel, BoxLayout.Y_AXIS));
    
    JLabel lowLevelLabel = new JLabel("LOW LEVEL HOTEL INFORMATION");
    lowLevelLabel.setFont(font);
    lowLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    lowLevelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
    
    // Add components to low-level panel with spacing
    lowLevelPanel.add(lowLevelLabel);
    lowLevelPanel.add(Box.createVerticalStrut(20)); // Add vertical space
    lowLevelPanel.add(buttonsPanel);

    // Combine high-level and low-level information panels
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(highLevelPanel, BorderLayout.CENTER);
    mainPanel.add(lowLevelPanel, BorderLayout.SOUTH);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

    viewHotelFrame = new JFrame("View Hotel");
    viewHotelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    viewHotelFrame.add(mainPanel);
    viewHotelFrame.setSize(550, 300);
    viewHotelFrame.setLocationRelativeTo(null);
}

    /**
     * Initialize the dialog for viewing the number of rooms available on a specific date
     */
    public void viewDateDialog() {
        viewDateDialog = new JDialog();
        viewDateDialog.setTitle("View Date");
        viewDateDialog.setSize(400, 150);
        viewDateDialog.setLocationRelativeTo(null);

        JPanel viewDatePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel selectDateLbl = new JLabel("Select a date to view:");
        selectDateLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        viewDatePanel.add(selectDateLbl, gbc);

        dateComboBox = new JComboBox<>();
        dateComboBox.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        viewDatePanel.add(dateComboBox, gbc);

        selectDateBtn = new JButton("Select Date");
        selectDateBtn.setActionCommand("Select Date");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        viewDatePanel.add(selectDateBtn, gbc);

        viewDateDialog.add(viewDatePanel);
        viewDateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Initialize the dialog for selecting a room
     */
    public void selectRoomDialog() {
        selectRoomDialog = new JDialog();
        selectRoomDialog.setTitle("Select a Room");
        selectRoomDialog.setSize(400, 200);
        selectRoomDialog.setLocationRelativeTo(null);

        JPanel selectRoomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel selectRoomLbl = new JLabel("Select a room to view:");
        selectRoomLbl.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        selectRoomPanel.add(selectRoomLbl, gbc);

        roomComboBox = new JComboBox<>();
        roomComboBox.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        selectRoomPanel.add(roomComboBox, gbc);

        selectRoomBtn = new JButton("Select Room");
        selectRoomBtn.setActionCommand("Select Room");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        selectRoomPanel.add(selectRoomBtn, gbc);

        selectRoomDialog.add(selectRoomPanel);
        selectRoomDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    /**
     * Initialize the frame for viewing a given room's information
     */
    public void viewRoomFrame() {
        viewRoomFrame = new JFrame("Room Information");
        viewRoomFrame.setSize(400, 600);
        viewRoomFrame.setLayout(new BorderLayout());
        viewRoomFrame.setLocationRelativeTo(null);

        Font borderFont = new Font("Arial", Font.BOLD, 16);

        // panel containing room info 
        JPanel roomInfoPanel = new JPanel();
        roomInfoPanel.setLayout(new BorderLayout());
        
        TitledBorder roomInfoBorder = BorderFactory.createTitledBorder("ROOM INFORMATION");
        roomInfoBorder.setTitleFont(borderFont);
        roomInfoPanel.setBorder(roomInfoBorder);

        viewRoomTextArea = new JTextArea();
        viewRoomTextArea.setEditable(false);
        viewRoomTextArea.setMargin(new Insets(5, 5, 5, 5));

        viewRoomTextArea.setPreferredSize(new Dimension(300, 100)); // set size of text area para walang scroll bar

        JScrollPane roomInfoScrollPane = new JScrollPane(viewRoomTextArea);
        roomInfoPanel.add(roomInfoScrollPane, BorderLayout.CENTER);
        
        // panel containing room availability
        JPanel availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new BorderLayout());
        
        TitledBorder roomAvailabilityBorder = BorderFactory.createTitledBorder("ROOM AVAILABILITY");
        roomAvailabilityBorder.setTitleFont(borderFont);
        availabilityPanel.setBorder(roomAvailabilityBorder);

        viewRoomTextArea2 = new JTextArea();
        viewRoomTextArea2.setEditable(false);
        viewRoomTextArea2.setMargin(new Insets(5, 5, 5, 5));

        viewRoomTextArea2.setPreferredSize(new Dimension(300, 500));

        JScrollPane availabilityScrollPane = new JScrollPane(viewRoomTextArea2);
        availabilityPanel.add(availabilityScrollPane, BorderLayout.CENTER);

        viewRoomFrame.add(roomInfoPanel, BorderLayout.NORTH);
        viewRoomFrame.add(availabilityPanel, BorderLayout.CENTER);

        viewRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // for choosing a reservation to view
    /**
     * Initialize the dialog for selecting a reservation
     */
    public void viewReservationMenu() {
        reservationMenuDialog = new JDialog();
        reservationMenuDialog.setTitle("Select a Reservation");
        reservationMenuDialog.setSize(400, 200);
        reservationMenuDialog.setLocationRelativeTo(null);

        JPanel reservationMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel selectReservationLbl = new JLabel("Select a reservation to view:");
        selectReservationLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        reservationMenuPanel.add(selectReservationLbl, gbc);

        reservationComboBox = new JComboBox<>();
        reservationComboBox.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        reservationMenuPanel.add(reservationComboBox, gbc);

        selectReservationBtn = new JButton("Select Reservation");
        selectReservationBtn.setActionCommand("Select Reservation");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        reservationMenuPanel.add(selectReservationBtn, gbc);

        reservationMenuDialog.add(reservationMenuPanel);
        reservationMenuDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the dialog for viewing a reservation's details
     */
    public void viewReservationDialog() {
        viewReservationDialog = new JDialog();
        viewReservationDialog.setTitle("Reservation Details");
        viewReservationDialog.setSize(300, 400);
        viewReservationDialog.setLocationRelativeTo(null);
    
        JPanel viewReservationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JLabel reservationDetailsLbl = new JLabel("Reservation Information");
        reservationDetailsLbl.setFont(new Font("Arial", Font.BOLD, 18));
        reservationDetailsLbl.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        viewReservationPanel.add(reservationDetailsLbl, gbc);
    
        viewReservationTextArea = new JTextArea();
        viewReservationTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        viewReservationTextArea.setEditable(false);
        viewReservationTextArea.setMargin(new Insets(5, 5, 5, 5));
        viewReservationTextArea.setPreferredSize(new Dimension(200, 200));
    
        JScrollPane reservationScrollPane = new JScrollPane(viewReservationTextArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        viewReservationPanel.add(reservationScrollPane, gbc);
    
        viewReservationDialog.add(viewReservationPanel);
        viewReservationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    // to display for when user makes modifications through manage hotel menu
    /**
     * Initialize the dialog for confirming a modification to the hotel
     */
    public void confirmModificationDialog() {
        confirmModificationDialog = new JDialog();
        confirmModificationDialog.setTitle("Confirm Modification");
        confirmModificationDialog.setSize(400, 250);
        confirmModificationDialog.setLocationRelativeTo(null);

        JPanel confirmModificationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel confirmModificationLbl = new JLabel("Are you sure you want to make this modification?");
        confirmModificationLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        confirmModificationPanel.add(confirmModificationLbl, gbc);

        confirmModificationBtn = new JButton("Confirm");
        confirmModificationBtn.setActionCommand("Confirm Modification");

        cancelModificationBtn = new JButton("Cancel");
        cancelModificationBtn.setActionCommand("Cancel Modification");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(confirmModificationBtn);
        buttonPanel.add(cancelModificationBtn);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        confirmModificationPanel.add(buttonPanel, gbc);

        confirmModificationDialog.add(confirmModificationPanel);
        confirmModificationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    // will collect the guest's name, check-in date, and check-out date
    /**
     * Initialize the dialog for simulating a booking in the hotel. Collects guest name, check-in date, and check-out date. 
     */
    public void simulateBookingDialog() {
        simulateBookingDialog = new JDialog();
        simulateBookingDialog.setTitle("Simulate Booking");
        simulateBookingDialog.setSize(400, 250);
        simulateBookingDialog.setLocationRelativeTo(null);
    
        JPanel simulateBookingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JLabel guestNameLbl = new JLabel("Guest Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(guestNameLbl, gbc);
    

        guestsNameTf = new JTextField();
        guestsNameTf.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(guestsNameTf, gbc);
    
        JLabel checkInLbl = new JLabel("Check-in Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(checkInLbl, gbc);
        
        checkInDateCBox = new JComboBox<Integer>();
        checkInDateCBox.setPreferredSize(new Dimension(200, 25)); 
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(checkInDateCBox, gbc);
    
        JLabel checkOutLbl = new JLabel("Check-out Date:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(checkOutLbl, gbc);
    
        checkOutDateCBox = new JComboBox<Integer>();
        checkOutDateCBox.setPreferredSize(new Dimension(200, 25)); 
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel.add(checkOutDateCBox, gbc);
    
        submitBookingInfoBtn = new JButton("Next");
        submitBookingInfoBtn.setActionCommand("Submit Booking Info 1");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        simulateBookingPanel.add(submitBookingInfoBtn, gbc);
    
        simulateBookingDialog.add(simulateBookingPanel);
        simulateBookingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the dialog for simulating a booking in the hotel. Collects room to book and an optional discount code. Called after the simulateBookingDialog method.
     */
    public void simulateBookingDialog2() {
        simulateBookingDialog2 = new JDialog();
        simulateBookingDialog2.setTitle("Simulate Booking");
        simulateBookingDialog2.setSize(400, 250);
        simulateBookingDialog2.setLocationRelativeTo(null);

        JPanel simulateBookingPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel roomLbl = new JLabel("Room to book:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel2.add(roomLbl, gbc);

        roomCBox = new JComboBox<>();
        roomCBox.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel2.add(roomCBox, gbc);

        JLabel discountLbl = new JLabel("Discount Code (optional):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel2.add(discountLbl, gbc);

        discountCodeTf = new JTextField();
        discountCodeTf.setMinimumSize(new Dimension(200, 25)); 
        discountCodeTf.setPreferredSize(new Dimension(200, 25));
        discountCodeTf.setText("");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulateBookingPanel2.add(discountCodeTf, gbc);

        submitBookingInfoBtn2 = new JButton("Book Room");
        submitBookingInfoBtn2.setActionCommand("Submit Booking Info 2");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        simulateBookingPanel2.add(submitBookingInfoBtn2, gbc);

        simulateBookingDialog2.add(simulateBookingPanel2);
        simulateBookingDialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the dialog for displaying the booking receipt containing reservation details
     */
    public void bookingReceiptDialog() {
        bookingReceiptDialog = new JDialog();
        bookingReceiptDialog.setTitle("Booking Receipt");
        bookingReceiptDialog.setSize(300, 250); 
        bookingReceiptDialog.setLocationRelativeTo(null); 
    
        JPanel bookingReceiptPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
    
        hotelNameBooked = new JLabel("");
        hotelNameBooked.setFont(new Font("Arial", Font.BOLD, 12));
        
        bookingReceiptTA = new JTextArea();
        bookingReceiptTA.setEditable(false);
        bookingReceiptTA.setMargin(new Insets(5, 5, 5, 5));
        bookingReceiptTA.setPreferredSize(new Dimension(200, 100));
    
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Booking Receipt", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 16), 
            Color.BLACK
        ));
        textAreaPanel.setOpaque(false); 
        textAreaPanel.add(new JScrollPane(bookingReceiptTA), BorderLayout.CENTER);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // No extra space allocated for the label
        gbc.anchor = GridBagConstraints.CENTER;
        bookingReceiptPanel.add(hotelNameBooked, gbc);
    
        gbc.gridy = 1;
        gbc.weighty = 1.0; 
        bookingReceiptPanel.add(textAreaPanel, gbc);
    
        bookingReceiptDialog.add(bookingReceiptPanel);
    
        bookingReceiptDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /* listeners */
    /**
     * Set the action listener for the buttons in the GUI
     * @param listener the action listener
     */
    public void setActionListener (ActionListener listener) {
        createHotelBtn.addActionListener(listener);
        manageHotelBtn.addActionListener(listener);
        viewHotelBtn.addActionListener(listener);
        simulateBookingBtn.addActionListener(listener);

        createHotelFinBtn.addActionListener(listener);
        selectHotelFinBtn.addActionListener(listener);
        submitNewRoomsBtn.addActionListener(listener);
        submitNewHotelNameBtn.addActionListener(listener);
        removeRoomsFinBtn.addActionListener(listener);
        
        changeHotelNameBtn.addActionListener(listener);
        addRoomsBtn.addActionListener(listener);
        removeRoomsBtn.addActionListener(listener);
        updRoomPriceBtn.addActionListener(listener);
        dpmBtn.addActionListener(listener);
        removeReservationBtn.addActionListener(listener);
        removeHotelBtn.addActionListener(listener);
        submitNewRoomPriceBtn.addActionListener(listener);
        commitChangesButton.addActionListener(listener);
        submitRemoveReservationBtn.addActionListener(listener);
        submitRemoveHotelBtn.addActionListener(listener);
        cancelRemoveHotelBtn.addActionListener(listener);

        dateBtn.addActionListener(listener);
        roomBtn.addActionListener(listener);
        reservationBtn.addActionListener(listener);
        selectDateBtn.addActionListener(listener);
        selectRoomBtn.addActionListener(listener);
        selectReservationBtn.addActionListener(listener);
        submitBookingInfoBtn.addActionListener(listener);
        submitBookingInfoBtn2.addActionListener(listener);

        confirmModificationBtn.addActionListener(listener);
        cancelModificationBtn.addActionListener(listener);
    }

    /**
     * Set a document listener for the text fields in the GUI
     * @param listener the document listener
     */
    public void setDocumentListener(DocumentListener listener) {
        // for creating a hotel
        hotelNameTf.getDocument().addDocumentListener(listener);
        stdRoomsTf.getDocument().addDocumentListener(listener);
        dlxRoomsTf.getDocument().addDocumentListener(listener);
        execRoomsTf.getDocument().addDocumentListener(listener);

        // for changing the hotel's name
        newHotelNameTf.getDocument().addDocumentListener(listener);

        // for adding rooms to the hotel
        newStdRoomsTf.getDocument().addDocumentListener(listener);
        newDlxRoomsTf.getDocument().addDocumentListener(listener);
        newExecRoomsTf.getDocument().addDocumentListener(listener);

        // for setting a new room base price
        newRoomPriceTf.getDocument().addDocumentListener(listener);

        // modifying date price
        percentageTf.getDocument().addDocumentListener(listener);
        
        // for removing a reservation
        removeReservationTf.getDocument().addDocumentListener(listener);

        // for booking a room
        guestsNameTf.getDocument().addDocumentListener(listener);
        discountCodeTf.getDocument().addDocumentListener(listener);
    }


    /* other display methods */

    /**
     * Display or hide the create hotel dialog
     * @param show true to display, false to hide
     */
    public void toggleCreateHotelDialog(boolean show) {
        roomTypeDialog.setVisible(show);
    }

    /**
     * Display or hide the hotel selection dialog
     * @param show true to display, false to hide
     */
    public void toggleHotelSelectionDialog(boolean show) {
        hotelSelectionDialog.setVisible(show);
    }

    /**
     * Display or hide the change hotel name dialog
     * @param show true to display, false to hide
     */
    public void toggleChangeHotelNameDialog(boolean show) {
        changeHotelNameDialog.setVisible(show);
    }

    /**
     * Display or hide the add rooms dialog
     * @param show true to display, false to hide
     */
    public void toggleAddRoomsDialog(boolean show) {
        addRoomsDialog.setVisible(show);
    }

    /**
     * Display or hide the remove rooms dialog
     * @param show true to display, false to hide
     */
    public void toggleRemoveRoomsFrame(boolean show) {
        removeRoomsFrame.setVisible(show);
    }

    /**
     * Display or hide the update room price dialog
     * @param show true to display, false to hide
     */
    public void toggleUpdateRoomPriceDialog(boolean show) {
        updRoomPriceDialog.setVisible(show);
    }

    /**
     * Display or hide the remove reservation dialog
     * @param show true to display, false to hide
     */
    public void toggleRemoveReservationDialog(boolean show) {
        removeReservationDialog.setVisible(show);
    }

    /**
     * Display or hide the remove hotel dialog
     * @param show true to display, false to hide
     */
    public void toggleRemoveHotelDialog(boolean show) {
        removeHotelDialog.setVisible(show);
    }

    /**
     * Display or hide the view hotel frame
     * @param show true to display, false to hide
     */
    public void toggleViewHotelMenu(boolean show) {
        viewHotelFrame.setVisible(show);
    }

    /**
     * Display or hide the manage hotel frame
     * @param show true to display, false to hide
     */
    public void toggleManageHotelMenu(boolean show) {
        manageHotelFrame.setVisible(show);
    }

    /**
     * Display or hide the select room dialog
     * @param show true to display, false to hide
     */
    public void toggleSelectRoomDialog(boolean show){
        selectRoomDialog.setVisible(show);
    }

    /**
     * Display or hide the view date dialog
     * @param show true to display, false to hide
     */
    public void toggleViewDateDialog(boolean show) {
        viewDateDialog.setVisible(show);
    }

    /**
     * Display or hide the view rooms frame
     * @param show true to display, false to hide
     */
    public void toggleViewRoomsFrame(boolean show) {
        viewRoomFrame.setVisible(show);
    }

    /**
     * Display or hide the view reservation dialog
     * @param show true to display, false to hide
     */
    public void toggleViewReservationMenu(boolean show) {
        reservationMenuDialog.setVisible(show);
    }

    /**
     * Display or hide the view reservation dialog
     * @param show true to display, false to hide
     */
    public void toggleViewReservationDialog(boolean show) {
        viewReservationDialog.setVisible(show);
    }

    /**
     * Display or hide the first simulate booking dialog
     * @param show true to display, false to hide
     */
    public void toggleSimulateBookingDialog(boolean show) {
        simulateBookingDialog.setVisible(show);
    }

    /**
     * Display or hide the second simulate booking dialog
     * @param show true to display, false to hide
     */
    public void toggleSimulateBookingDialog2(boolean show) {
        simulateBookingDialog2.setVisible(show);
    }

    /**
     * Display or hide the confirm modification dialog
     * @param show true to display, false to hide
     */
    public void toggleConfirmModificationDialog(boolean show) {
        confirmModificationDialog.setVisible(show);
    }

    /**
     * Displays an error message given a string.
     * @param message the error message to display
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a confirmation message given a string.
     * @param message the confirmation message to display
     */
    public void showConfirmationMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Retrieves the hotel name from the text field.
     * @return the hotel name entered by the user
     */
    public String getHotelName() {
        return hotelNameTf.getText();
    }

    /**
     * Retrieves the new hotel name entered when creating a hotel.
     * @return the new hotel's name
     */
    public String getNewHotelName() { // for changing hotel names
        return newHotelNameTf.getText();
    }

    // for hotel creation!!
    /**
     * Retrieves the number of standard rooms entered when creating a hotel.
     * @return the number of standard roomsas a string
     */
    public String getStdRooms() {
        return stdRoomsTf.getText(); // text fields get strings. convert to int in controller
    }

    /**
     * Retrieves the number of deluxe rooms entered when creating a hotel.
     * @return the number of deluxe rooms as a string
     */
    public String getDlxRooms() {
        return dlxRoomsTf.getText();
    }

    /**
     * Retrieves the number of executive rooms entered when creating a hotel.
     * @return the number of executive rooms as a string
     */
    public String getExecRooms() {
        return execRoomsTf.getText();
    }

    // for adding rooms!!!
    /**
     * Retrieves the number of standard rooms entered when adding rooms to a hotel.
     * @return the number of standard rooms as a string
     */
    public String getNewStdRooms() {
        return newStdRoomsTf.getText();
    }

    /**
     * Retrieves the number of deluxe rooms entered when adding rooms to a hotel.
     * @return the number of deluxe rooms as a string
     */
    public String getNewDlxRooms() {
        return newDlxRoomsTf.getText();
    }

    /**
     * Retrieves the number of executive rooms entered when adding rooms to a hotel.
     * @return the number of executive rooms as a string
     */
    public String getNewExecRooms() {
        return newExecRoomsTf.getText();
    }

    /**
     * Clears the text fields for creating a hotel.
     */
    public void clearCreateHotelTF() {
        hotelNameTf.setText("");
        stdRoomsTf.setText("");
        dlxRoomsTf.setText("");
        execRoomsTf.setText("");
    }

    /**
     * Clears the text fields for adding rooms to a hotel.
     */
    public void clearAddRoomsTf() {
        newStdRoomsTf.setText("");
        newDlxRoomsTf.setText("");
        newExecRoomsTf.setText("");
    }

    /**
     * Clears the text field for changing a hotel's name.
     */
    public void clearChangeHotelNameTF() {
        newHotelNameTf.setText("");
    }

    // for removing rooms
    /**
     * Sets the text area for removing rooms with the given string.
     * @param roomInfo the string to set the text area with
     */
    public void setRoomRemovalTextArea(String roomInfo) {
        System.out.println("Setting room removal text area with: " + roomInfo); // debug
        removeRoomsTA.setText(roomInfo);
    }

    /**
     * Retrieves the room name to remove from the combo box.
     * @return the room to remove as a string
     */
    public String getRoomToRemove() {
        return (String) removeRoomsCBox.getSelectedItem();
    }

    /**
     * Retrieves the combo box for removing rooms.
     * @return the combo box for removing rooms
     */
    public JComboBox<String> getRemoveRoomsCBox() {
        return removeRoomsCBox;
    }
    
    /**
     * Retrieves the booking ID from the text field.
     * @return the booking ID as a string
     */
    public String getBookingID() {
        return removeReservationTf.getText();
    }

    /**
     * Retrieves the new room base price from the text field.
     * @return the new room base price as a string
     */
    public String getNewRoomBasePrice() {
        return newRoomPriceTf.getText();
    }

    /**
     * Sets the label indicating the room's current base price before the user enters a new one.
     * @param roomPrice the room's current base price
     */
    public void setRoomBasePriceLabel(double roomPrice) {
        subTitleLbl2.setText("Current room base price: " + roomPrice);
    }

    /**
     * Clears the text field for updating a room's base price.
     */
    public void clearUpdateRoomPriceTf() {
        newRoomPriceTf.setText("");
    }

    /**
     * Retrieves the hotel name from the combo box.
     * @return the hotel name
     */
    public String getSelectedHotelFromComboBox() {
        return (String) hotelComboBox.getSelectedItem();
    }

    /**
     * Sets the text area for viewing the hotel's high-level information.
     * @param hotelInfo the hotel's high-level information
     */
    public void setViewHotelTA(String hotelInfo) {
        highLevelTextArea.setText(hotelInfo);
    }

    /**
     * Retrieves the date combo box itself.
     * @return the date combo box
     */
    public JComboBox<String> getDateComboBox() {
        return dateComboBox;
    }

    /**
     * Retrieves a date from the combo box.
     * @return the selected date as a string
     */
    public String getSelectedDate() { // for viewing date in "view hotel"
        return (String) dateComboBox.getSelectedItem();
    }

    /**
     * Retrieves a room from the combo box to make a reservation.
     * @return the selected room as a string
     */
    public String getSelectedRoomToView() {
        return (String) roomCBox.getSelectedItem();
    }

    /**
     * Retrieves a room from the combo box for viewing room information.
     * @return the selected room as a string
     */
    public String getSelectedRoom() {
        return (String) roomComboBox.getSelectedItem();
    }

    /**
     * Sets the text area for viewing a room's information.
     * @param roomInfo the room's information
     */
    public void setViewRoomInfoTA(String roomInfo) {
        viewRoomTextArea.setText(roomInfo);
    }

    /**
     * Sets the text area for viewing a room's availability.
     * @param roomInfo the room's availability information
     */
    public void setViewRoomAvailabilityTA(String roomInfo) {
        viewRoomTextArea2.setText(roomInfo);
    }

    /**
     * Sets the text area for viewing a reservation's details.
     * @param reservationInfo the reservation information
     */
    public void setViewReservationTA(String reservationInfo) {
        viewReservationTextArea.setText(reservationInfo);
    }

    /**
     * Retrieves the reservation combo box itself.
     * @return the reservation combo box
     */
    public JComboBox<String> getReservationComboBox() {
        return reservationComboBox;
    }

    /**
     * Retrieves the selected reservation from the combo box.
     * @return the selected reservation as a string
     */
    public String getSelectedReservation() {
        return (String) reservationComboBox.getSelectedItem();
    }

    /**
     * Clears the combo box for selecting a reservation.
     */
    public void clearReservationComboBox() {
        reservationComboBox.removeAllItems();
    }

    /**
     * Updates the combo box with newly added hotels.
     * @param hotelList the list of hotels to add to the combo box
     */
    public void updateHotelComboBox(ArrayList<Hotel> hotelList) {
        hotelComboBox.removeAllItems();
        for (Hotel hotel : hotelList) {
            hotelComboBox.addItem(hotel.getHotelName());
        }
    }

    /**
     * Adds a reservation element to the combo box.
     * @param reservation the reservation to add
     */
    public void addReservationToComboBox(String reservation) {
        reservationComboBox.addItem(reservation);
    }

    // combo box in view rooms
    /**
     * Adds a room to the combo box for viewing rooms.
     * @param roomName the room to add
     */
    public void addRoomToViewComboBox(String roomName) {
        roomComboBox.addItem(roomName);
    }

    /**
     * Clears the text fields for booking a room.
     */
    public void clearSimulateBookingTF() {
        guestNameTf.setText("");
        discountCodeTf.setText("");
    }

    /**
     * Adds a room to the combo box for booking a room.
     * @param roomName the room to add
     */
    public void addRoomToBookingComboBox(String roomName) {
        roomToBookCBox.addItem(roomName);
    }

    /**
     * Retrieves the guest's name from the text field.
     * @return
     */
    public String getGuestName() {
        return guestsNameTf.getText();
    }

    /**
     * Retrieves the check-in date from the combo box.
     * @return the check-in date
     */
    public int getCheckInDate() {
        return (int) checkInDateCBox.getSelectedItem();
    }

    /**
     * Retrieves the check-out date from the combo box.
     * @return the check-out date
     */
    public int getCheckOutDate() {
        return (int) checkOutDateCBox.getSelectedItem();
    }

    /**
     * Retrieves the discount code from the text field.
     * @return the discount code
     */
    public String getDiscountCode() {
        return discountCodeTf.getText();
    }

    /**
     * Retrieves the combo box of rooms to book.
     * @return the combo box of rooms to book
     */
    public JComboBox<String> getRoomToBookCBox() {
        return roomCBox;
    }

    /**
     * Retrieves the room to book from the combo box.
     * @return the room to book
     */
    public String getRoomToBook() {
        return (String) roomCBox.getSelectedItem();
    }

    /**
     * Retrieves the check-in date from the combo box.
     * @return the check-in date
     */
    public JComboBox<Integer> getCheckInComboBox() {
        return checkInDateCBox;
    }

    /**
     * Retrieves the check-out date from the combo box.
     * @return the check-out date
     */
    public JComboBox<Integer> getCheckOutComboBox() {
        return checkOutDateCBox;
    }

    /**
     * Sets the text area for the booking receipt with the given string.
     * @param receiptInfo the booking receipt information
     */
    public void setBookingReceiptTextArea(String receiptInfo) {
        bookingReceiptTA.setText(receiptInfo);
    }

    /**
     * Shows or hides the booking receipt dialog.
     * @param show true to show, false to hide
     */
    public void toggleBookingReceiptDialog(boolean show) {
        bookingReceiptDialog.setVisible(show);
    }

    /**
     * Clears the combo box for booking a room.
     */
    public void clearRoomComboBox() {
        roomComboBox.removeAllItems();
    }

    /**
     * Clears the text fields for booking a room.
     */
    public void clearSimBookingTextFields() {
        guestsNameTf.setText("");
        discountCodeTf.setText("");
    }

    /**
     * Shows or hides the frame for modifying the date price.
     * @param show true to show, false to hide
     */
    public void toggleModifyDatePriceFrame(boolean show) {
        datePriceModifierFrame.setVisible(show);
    }

    /**
     * Sets the text area for modifying the date price with the given string.
     * @param datePriceInfo the date price information
     */
    public void setModifyDatePriceTextArea(String datePriceInfo) {
        datePriceModifierTA.setText(datePriceInfo);
    }

    /**
     * Retrieves the percentage from the text field for modifying the date price.
     * @return the percentage as a string
     */
    public String getPercentageMDP() {
        return percentageTf.getText();
    }

    /**
     * Clears the text field for modifying the date price percentage.
     */
    public void clearPercentageMDP() {
        percentageTf.setText("");
    }

    /**
     * Clears the text field for removing a reservation given through a booking ID.
     */
    public void clearRemoveReservationTf() {
        removeReservationTf.setText("");
    }

    /**
     * Retrieves the date selected from the combo box for modifying the date price.
     * @return the selected starting date for the date price modification
     */ 
    public int getSelectedDateMDP() {
        return (int) dateCBox.getSelectedItem();
    }

    /**
     * Retrieves the combo box for modifying the date price.
     * @return the combo box for modifying the date price
     */
    public JComboBox<Integer> getDateCBox() {
        return dateCBox;
    }
    
    /**
     * Sets the label for indicating the hotel name in the booking receipt.
     * @param hotelName the hotel name
     */
    public void setHotelNameBooked(String hotelName) {
        hotelNameBooked.setText(hotelName);
    }

    /**
     * Clears the text area for date price modification.
     */
    public void clearDPMTextArea() {
        datePriceModifierTA.setText("");
    }
}
