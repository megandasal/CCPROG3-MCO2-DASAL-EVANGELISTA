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
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;

public class HotelReservationGUI extends JFrame {
    // main menu buttons
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

        // update room base price
        private JDialog updRoomPriceDialog;
        private JTextField newRoomPriceTf;
        private JButton submitNewRoomPriceBtn;

        // date price modifier

        // remove reservation
        private JDialog removeReservationDialog;
        private JTextField removeReservationTf;
        private JButton submitRemoveReservationBtn;

        // remove hotel
        private JDialog removeHotelDialog;
        private JButton submitRemoveHotelBtn;
        private JButton cancelRemoveHotelBtn;

    // view hotel
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

        // view reservation (unimplemented atm)
        private JDialog viewReservationDialog;
        private JTextArea viewReservationTextArea;


    private ArrayList<Hotel> hotelList;

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
        // updateRoomPriceDialog();
        removeReservationDialog();
        removeHotelDialog();

        // view hotel
        viewHotelMenu();
        viewDateDialog();
        selectRoomDialog();
        viewRoomFrame();
        viewReservationMenu();
    
        // HRSController controller = new HRSController(this, hotelList, hotelCount);
        // setActionListener(controller); 
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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
        JLabel titleLabel = new JLabel("Create a Hotel!");
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
    
        hotelNameTf = new JTextField(10);
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
        gbc.gridy = 3;

        manageHotelMenuPanel.add(addRoomsBtn, gbc);
        gbc.gridy = 5;

        manageHotelMenuPanel.add(removeRoomsBtn, gbc);
        gbc.gridy = 7;

        manageHotelMenuPanel.add(dpmBtn, gbc);
        gbc.gridy = 9;

        manageHotelMenuPanel.add(removeReservationBtn, gbc);
        gbc.gridy = 11;

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
        JTextArea removeRoomsTA = new JTextArea(10, 40);
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
        removeRoomsFrame.setVisible(true);
    }
    
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
        JLabel subTitleLbl2 = new JLabel("Current room base price:");
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
        updRoomPriceDialog.setVisible(true);
    }

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
        removeReservationDialog.setVisible(true);
        removeReservationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
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
        removeHotelDialog.setVisible(true);
        removeHotelDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void viewHotelMenu() {
        //String hotelInfo = hotel.getHotelInformation();
        JPanel highLevelPanel = new JPanel(new BorderLayout());
        JLabel highLevelLabel = new JLabel("HIGH LEVEL INFORMATION");
        Font font = new Font("Arial",Font.BOLD,16);
        highLevelLabel.setFont(font);
        highLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highLevelTextArea = new JTextArea();
        highLevelTextArea.setEditable(false);
        JScrollPane highLevelScrollPane = new JScrollPane(highLevelTextArea);
        highLevelPanel.add(highLevelLabel, BorderLayout.NORTH);
        highLevelPanel.add(highLevelScrollPane, BorderLayout.CENTER);
        
        // Create buttons panel with 3 buttons horizontally
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));

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
        
        // Create a JPanel for low-level information (including buttons)
        JPanel lowLevelPanel = new JPanel(new BorderLayout());
        JLabel lowLevelLabel = new JLabel("LOW LEVEL INFORMATION");
        lowLevelLabel.setFont(font);
        lowLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add buttons panel to the bottom of low-level information panel
        lowLevelPanel.add(lowLevelLabel, BorderLayout.NORTH);
        lowLevelPanel.add(buttonsPanel, BorderLayout.SOUTH); // Adding buttons here
        
        // Combine high-level and low-level information panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(highLevelPanel, BorderLayout.CENTER);
        mainPanel.add(lowLevelPanel, BorderLayout.SOUTH);

        JFrame viewHotelFrame = new JFrame("View Hotel");
        viewHotelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewHotelFrame.add(mainPanel);
        viewHotelFrame.setSize(550, 300);
        viewHotelFrame.setLocationRelativeTo(null);
        viewHotelFrame.setVisible(true);
    }

    public void viewDateDialog() {
        JDialog viewDateDialog = new JDialog();
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

        viewDateDialog.setVisible(true);
    }
    
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

        selectRoomDialog.setVisible(true);
    }

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

        viewRoomFrame.setVisible(true);
        viewRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // for choosing a reservation to view
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

        reservationMenuDialog.setVisible(true);
    }


    /* listeners */
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
        submitRemoveReservationBtn.addActionListener(listener);
        submitRemoveHotelBtn.addActionListener(listener);
        cancelRemoveHotelBtn.addActionListener(listener);

        dateBtn.addActionListener(listener);
        roomBtn.addActionListener(listener);
        reservationBtn.addActionListener(listener);
        selectDateBtn.addActionListener(listener);
        selectRoomBtn.addActionListener(listener);
        selectReservationBtn.addActionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener) {
        hotelNameTf.getDocument().addDocumentListener(listener);
        stdRoomsTf.getDocument().addDocumentListener(listener);
        dlxRoomsTf.getDocument().addDocumentListener(listener);
        execRoomsTf.getDocument().addDocumentListener(listener);
        newHotelNameTf.getDocument().addDocumentListener(listener);
        newStdRoomsTf.getDocument().addDocumentListener(listener);
        newDlxRoomsTf.getDocument().addDocumentListener(listener);
        newExecRoomsTf.getDocument().addDocumentListener(listener);
        newRoomPriceTf.getDocument().addDocumentListener(listener);
        removeReservationTf.getDocument().addDocumentListener(listener);
    }


    /* other display methods */

    public void toggleCreateHotelDialog(boolean show) {
        roomTypeDialog.setVisible(show);
    }

    public void toggleHotelSelectionDialog(boolean show) {
        hotelSelectionDialog.setVisible(show);
    }

    public void toggleChangeHotelNameDialog(boolean show) {
        changeHotelNameDialog.setVisible(show);
    }

    public void toggleAddRoomsDialog(boolean show) {
        addRoomsDialog.setVisible(show);
    }

    public void toggleManageHotelMenu(boolean show) {
        manageHotelFrame.setVisible(show);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showConfirmationMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getHotelName() {
        return hotelNameTf.getText();
    }

    public String getNewHotelName() { // for changing hotel names
        return newHotelNameTf.getText();
    }

    public String getStdRooms() {
        return stdRoomsTf.getText(); // text fields get strings. convert to int in controller
    }

    public String getDlxRooms() {
        return dlxRoomsTf.getText();
    }

    public String getExecRooms() {
        return execRoomsTf.getText();
    }

    public void clearCreateHotelTF() {
        hotelNameTf.setText("");
        stdRoomsTf.setText("");
        dlxRoomsTf.setText("");
        execRoomsTf.setText("");
    }

    public void clearAddRoomsTf() {
        newStdRoomsTf.setText("");
        newDlxRoomsTf.setText("");
        newExecRoomsTf.setText("");
    }

    public void clearChangeHotelNameTF() {
        newHotelNameTf.setText("");
    }

    public String getSelectedHotelFromComboBox() {
        return (String) hotelComboBox.getSelectedItem();
    }

    public void updateHotelComboBox(ArrayList<Hotel> hotelList) {
        hotelComboBox.removeAllItems();
        for (Hotel hotel : hotelList) {
            hotelComboBox.addItem(hotel.getHotelName());
        }
    }
}