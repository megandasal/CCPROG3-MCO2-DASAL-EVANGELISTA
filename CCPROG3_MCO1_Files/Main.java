/*
SPECIFICATIONS CHECKLIST:

  [X] - FOR NOT YET IMPLEMENTED
  [_] - FOR ONGOING
  [/] - FOR IMPLEMENTED
  
  1. Create Hotel
  
    [/] Initialize New Hotel - Hotel Count must start at 0 and name must be UNIQUE.
    [/] Create Rooms - 0...50 rooms, each room has a unique name. 
    [/] Set Room Base Price to default price of 1,299.0 (this will be set in Room class constructor) (note: this is effective for all rooms in the hotel)
    [/] Reserve a Room 
        a. BASIC INFORMATION: guest name, check-in date, check-out date, and link too room information.   
            - reminder: check logic for reserving room from days n to n
        b. PRICE: total price and breakdown of cost per night
    
  2. View Hotel ***NOTE: FIX AESTHETICS + ERROR PROOFING***
  
    [/] HIGH LEVEL INFORMATION
        [/] hotel name
        [/] total number of rooms
        [FOR CHECKING] estimate earnings per month (i.e. sum of total price across all reservations)
        
    [/] LOW LEVEL INFORMATION
        [/] number of available and booked rooms for a selected date
        [FOR CHECKING] information about selected room (name, cost per night, and availabilty across the whole month)
        note: REVISE COMPUTATION 
        [/] information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night) (note: this just                  displays the same information as the one in the 'reservation ticket")
        
  3. Manage Hotel - User must be prompted to confirm modification before it is made or else it will be discarded
        [/] Change name of hotel
        [UNDER REVISION] Add room(s)
        [UNDER REVISION] Remove room(s)\
            note: can only remove rooms that do not have active reservations 
        [/] Update base price for rooms 
            - can only update room price if there are no reservations
            - new price must be >= 100.0
        [/] Remove reservation
        [/] Remove hotel
            
  
  4. Simulate Booking
      [/] Select hotel and specify information 
      [/] Select room 
        note: just display a list of the rooms available and user can select from those
      [/] Validation of Check in and Check out dates
      [/] Update status of room

*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static int hotelCount = 0;
    public static ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
    
    /**
     * Prints the main menu for the hotel reservation system.
     * Displays options for creating, viewing, and managing a hotel.
     * Allows the user to simulate booking a reservation.
     */
    private static void printMenu() {
        System.out.println(".------------------------------------------------------."); 
        System.out.println("|                      MAIN MENU                       |");
        System.out.println("|               Current Hotel Count: " + hotelCount + "                 |"); 
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                                                      |");
        System.out.println("|   [Create Hotel]                      [View Hotel]   |");
        System.out.println("|                                                      |");
        System.out.println("|   [Manage Hotel]                [Simulate Booking]   |");
        System.out.println("|                                                      |");
        System.out.println("'------------------------------------------------------'");
    }

    /**
     * Prints the list of hotels currently available.
     */
    private static void printHotels() {
        // Print the list of hotels with centered names
        System.out.print("\n--- Available Hotels ---");
        System.out.println("\n");
        for (Hotel hotel : hotelList) {
            String hotelName = hotel.getHotelName();
            int totalWidth = 24;  // total width of the output
            int spaces = (totalWidth - hotelName.length()) / 2;
            String formatString = "%" + spaces + "s%s%" + spaces + "s";
            System.out.printf(formatString, "", hotelName, "");
            System.out.println();  // move to the next line after each hotel
        }
        System.out.println("\n------------------------");
    }


    /**
     * Checks if an entered hotel name does not yet exist among the list of hotels.
     * @param hotelList List of hotels to check against
     * @param hotelName Name of hotel to check
     * @return true if hotel name is unique, return false otherwise
     */
    private static boolean isUniqueName(ArrayList<Hotel> hotelList, String hotelName) {

        for (Hotel hotel : hotelList) { // iterates through hotelList to check if hotelName already exists
            if (hotel.getHotelName().equals(hotelName)) { // in the case that hotel name already exists
                System.out.println("This hotel name is already taken.");
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a hotel and adds it to the list of currently existing hotels.
     * @param hotelList list of available hotels
     */
    private static void createHotel(ArrayList<Hotel> hotelList) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nHotel Name: ");
        String hotelName = scanner.nextLine();

        if (isUniqueName(hotelList, hotelName)) {
            Hotel hotel = new Hotel(hotelName);
            hotelList.add(hotel);
            hotelCount++;
            System.out.println(hotelName + " was created!");

            // add code for room creation here (note: MIN OF 1 ROOM AND MAX OF 50 ROOMS)
            hotel.addRooms();

            System.out.println("Number of rooms: " + hotel.getNRooms());

            // print rooms made

        }
    }

  /**
   * Determines if a user's input is a valid integer.
   * Returns the integer input if the input is valid.
   * Prints an error message and returns -1 otherwise.
   * @return user's input if valid, -1 if not
   */
    private static int getIntInput() {
        if (scanner.hasNextInt()) {
          int input = scanner.nextInt();
          scanner.nextLine(); // consume the newline character
          return input;
        } else {
          System.out.println("Invalid input. Please enter an integer.");
          scanner.next(); // clear the invalid input
          return -1; //signals that the input was invalid
        }
      }

    /**
     * Allows user to view the date, room, and reservation information of a given hotel.
     * @param hotelList list of available hotels
     */
    private static void viewHotel(ArrayList<Hotel> hotelList) {

        Scanner scanner = new Scanner(System.in);

        if (hotelCount > 0){
            printHotels();

            System.out.println("\nWhich hotel do you wish to view? ");
            System.out.print("> Enter choice: ");
            String name = scanner.nextLine();

            boolean found = false;

            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(name)) {
                    /* DETAILS CAN BE PRINTED IN HOTEL CLASS */
                    System.out.print("\n");
                    hotel.printHotelInformation();

                    /* FIX THIS PART, CLEAN UP */
                    System.out.println(".------------------------------------------------------.");
                    System.out.println("|              LOW LEVEL HOTEL INFORMATION             |");
                    System.out.println(".------------------------------------------------------.");
                    System.out.println("|    [    DATE    ]  [    ROOM    ]  [ RESERVATION ]   |");
                    System.out.println(".------------------------------------------------------.");

                    System.out.print("> Enter choice: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "DATE":
                            while(true){
                                System.out.print("Date: ");
                                int date = getIntInput();

                                if((date >= 1 && date <= 31) && date > 0){
                                    System.out.print("Number of rooms available on " + date + ": " + hotel.roomsAvailableOnDate(date));
                                    break;
                            }
                                else
                                    System.out.println("Invalid date. Please enter a valid date.");
                                
                            }
                            break;
                        case "ROOM":
                            hotel.viewRooms();
                            hotel.viewRoomInfo();
                            // information about selected room (name, cost per night, and availabilty across
                            // the whole month) just iterate through rooms and print out the information of
                            // the selected room
                            break;

                        case "RESERVATION":
                            if(hotel.totalReservations() == 0){
                                System.out.println("There are no reservations in this hotel.");
                                return;
                            }else{
                            hotel.printAllReservations();
                            hotel.viewReservationInfo();
                            }
                            // information about selected reservation (guest information, room information,
                            // check-in and check-out dates, total price of booking and breakdown of cost
                            // per night)
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Hotel not found");
            }
        }
        else {
            System.out.println("There are no hotels to view at the moment.");
        }
        
    }

    /**
     * Allow the user to manage the hotel by changing the hotel name, adding and removing rooms,
     * updating the base price of a room, removing an existing reservation, and
     * removing the hotel itself.
     * @param hotelList list of available hotels
     */
    private static void manageHotel(ArrayList<Hotel> hotelList) {

        if (hotelCount > 0){ 

            System.out.println(".------------------------------------------------------.");
            System.out.println("|                      MANAGE HOTEL                    |");
            System.out.println(".------------------------------------------------------.");
            System.out.println("|                  [Change Hotel Name]                 |");
            System.out.println("|                                                      |");
            System.out.println("|                      [Add Rooms]                     |");
            System.out.println("|                                                      |");
            System.out.println("|                     [Remove Rooms]                   |");
            System.out.println("|                                                      |");
            System.out.println("|               [Update Room Base Price]               |");
            System.out.println("|                                                      |");
            System.out.println("|                  [Remove Reservation]                |");
            System.out.println("|                                                      |");
            System.out.println("|                     [Remove Hotel]                   |");
            System.out.println(".------------------------------------------------------.");

            
            System.out.print("> Enter choice: ");
            Scanner scanner = new Scanner(System.in);
            String manageChoice = scanner.nextLine();
            boolean hotelFound = false;
            String hotelName;
            String newName;
            boolean newNameIsValid = false;

            switch (manageChoice) {
                case "Change Hotel Name":

                    /*
                     * [/] 1. print all hotels
                     * [/] 2. input hotel you wish to change name
                     * [/] 3. if found, ask for new name
                     * [/] 4. if new name is valid, call hotel class setter
                     */

                        System.out.println("Please select a hotel from the following: ");
                        printHotels();
                        System.out.print("Hotel Name: ");
                        hotelName = scanner.nextLine();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(hotelName)) {
                                hotelFound = true;

                                while(!newNameIsValid){
                                    System.out.print("New Name: ");
                                    newName = scanner.nextLine();
                                    newNameIsValid = isUniqueName(hotelList, newName);
                                    if(newNameIsValid){
                                        System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                                        char c = scanner.nextLine().charAt(0);
                                        if (c == 'Y' || c == 'y'){
                                            hotel.setHotelName(newName);
                                            System.out.println("Hotel name successfully changed to " + newName);
                                        }
                                        else if (c == 'N' || c == 'n'){
                                            System.out.println("Modification cancelled.");
                                        }
                                        else {
                                            System.out.println("Invalid input.");
                                        }
                                    }
                                }
                            }
                        }
                        if (!hotelFound) {
                            System.out.println("Hotel not found.");
                        }

                    break;

                case "Add Rooms":
                        System.out.println("Please select a hotel from the following: ");
                        printHotels();
                        System.out.print("\n> Enter the hotel name you wish to add rooms to: ");
                        String hotelNameToAddRooms = scanner.nextLine();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(hotelNameToAddRooms)) {
                                hotel.addRooms();
                                hotelFound = true;
                                break;
                            }
                        }
                        if (!hotelFound) {
                            System.out.println("Hotel not found.");
                        }
                    break;

                case "Remove Rooms": 
                        System.out.println("Please select a hotel from the following: ");
                        printHotels();
                        System.out.print("\n> Enter hotel name to remove rooms from: ");
                        String hotelNameToRemoveRooms = scanner.nextLine();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(hotelNameToRemoveRooms)) {
                                hotelFound = true;
                                
                                if(hotel.getNRooms() == 1){
                                    System.out.println("There are no rooms to remove.");
                                    return;
                                }else{
                                hotel.removeRooms();
                                }
                                break;
                            }
                        }
                        if (!hotelFound) {
                            System.out.println("Hotel not found.");
                        }
                    break;

                case "Update Room Base Price":
                        System.out.println("\nPlease select a hotel from the following: ");
                        printHotels();
                        System.out.print("\n> Enter hotel name to update base price: ");
                        String hotelUpdatePrice = scanner.nextLine();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(hotelUpdatePrice)) {
                                hotel.updateRoomPrice();
                                hotelFound = true;
                                break;
                            }
                        }
                        if (!hotelFound) {
                            System.out.println("Hotel not found.");
                        }
                    break;

                case "Remove Reservation":
                        System.out.println("Please select a hotel from the following: ");
                        printHotels();
                        System.out.print("\n> Enter choice: ");
                        String hotelNameToRemoveReservation = scanner.nextLine();
                        for (Hotel hotel : hotelList) {
                            if (hotel.getHotelName().equals(hotelNameToRemoveReservation)) {
                                hotelFound = true;
                                
                                if(hotel.totalReservations() == 0){
                                    System.out.println("There are no reservations in this hotel.");
                                }
                                else{
                                hotel.removeReservation();
                                }
                                
                                break;
                            }
                        }
                        if (!hotelFound) {
                            System.out.println("Hotel not found.");
                        }
                    
                    break;

                case "Remove Hotel":
                    System.out.println("Please select a hotel from the following: "); 
                    printHotels();
                    System.out.print("\n> Enter hotel name to remove: ");
                    String removeHotelName = scanner.nextLine();

                    Iterator<Hotel> iterator = hotelList.iterator();
                    while (iterator.hasNext()) {
                        Hotel hotel = iterator.next();
                        if (hotel.getHotelName().equals(removeHotelName)) {
                            System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                            char confirm = scanner.nextLine().charAt(0);

                            if (confirm == 'Y' || confirm == 'y') {
                                iterator.remove();
                                hotelCount--;
                                System.out.println("\n" + removeHotelName + " was removed successfully.");
                            } else {
                                System.out.println("\nModification cancelled.");
                            }
                            hotelFound = true;
                            break;
                        }
                    }
                    if (!hotelFound) {
                        System.out.println("\nHotel not found.");
                    }
                    break;

                default:
                    System.out.println("\nInvalid choice.");
                    break;
            }
        }
        else {
            System.out.println("No hotels available.");
        }
    }

    /**
     * Allows the user to book a reservation in the hotel by entering information such as
     * their name, and the dates they wish to check in and check out.
     * @param hotelList list of available hotels
     */
    private static void simulateBooking(ArrayList<Hotel> hotelList) {
        /*
         * [/] 1. ask for check in and check out dates
         * [/] 2. print which rooms are available on those given days
         * [/] 3. user chooses a room from the list
         * [/] 4. reservation is made and room availability is updated
         * [/] 5. reservation details are printed
         */

        if (hotelCount > 0){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Please select a hotel to make a reservation. ");
            printHotels();

            System.out.println("\nIn which hotel do you wish to book a room? ");
            System.out.print("> Enter choice: ");
            String hotelChoice = scanner.nextLine();

            boolean hotelFound = false;
            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(hotelChoice)) {
                    System.out.println(
                            "You have selected the " + hotel.getHotelName() + " Hotel!\n");

                    hotel.bookRoom();

                    hotelFound = true;
                    break;
                }
            }

            if (!hotelFound) {
                System.out.println("Hotel not found.");
            }
        }
        else {
            System.out.println("No hotels available.");
        }
        
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            printMenu();

            System.out.print("> Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "Create Hotel":

                    createHotel(hotelList);

                    break;
                case "View Hotel":
                    viewHotel(hotelList);
                    break;
                case "Manage Hotel":
                    manageHotel(hotelList);

                    break;
                case "Simulate Booking":
                    simulateBooking(hotelList);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }

            choice = ""; // repeats menu
        }

    }
}
