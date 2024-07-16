/*

[/] Change roomNum from int to String
note: add checkers for room input validity 
[/] Room Types 
- [/] Room Availability
- [/] Removing Room
- [/] Adding Rooms
- [/] Updating Room Price

    simulate booking > select hotel > select room

    standard - 1299.0
    deluxe - base + 20%
    executive - base + 35%

fix: 
    [_] 1. create hotel - should prompt user how many rooms for each type they wish to add
    [/] 2. room class - add room type as a new variable
    [/] 3. remove rooms - should ask room type
    [/] 4. add rooms - should ask room type
    [/] 5. simulate booking - should only display a certain room type’s availability
    [/] 6. add room type to displays ex. view hotel > room > room #
    [/] 7. change room base price 

    note: set separate variables for room price based on the type (under hotel class)

[/] Discount code
    simulate booking > select hotel > select room > (continue with the same procedure for reserving) > discount code
[_] Date Price Modifier
[X] MVC
[X] GUI


*/

import java.util.Scanner;
import java.util.ArrayList;

public class HRS {

    public static int hotelCount = 0;
    public static ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prints the main menu for the hotel reservation system.
     * Displays options for creating, viewing, and managing a hotel.
     * Allows the user to simulate booking a reservation.
     */
    private static void printMenu() {
        System.out.println("\n.------------------------------------------------------.");
        System.out.println("|                      MAIN MENU                       |");
        System.out.println("|               Current Hotel Count: " + hotelCount + "                 |");
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                                                      |");
        System.out.println("|   [Create Hotel]                      [View Hotel]   |");
        System.out.println("|                                                      |");
        System.out.println("|   [Manage Hotel]                [Simulate Booking]   |");
        System.out.println("|                                                      |");
        System.out.println("|                        [Exit]                        |");
        System.out.println("'------------------------------------------------------'");
    }

    /**
     * Prints the list of hotels currently available.
     */
    private static void printHotels() {
        // Print the list of hotels with centered names
        System.out.println("\n.------------------------------------------.");
        System.out.println("|   HOTELS                                 |");
        System.out.println(".------------------------------------------.");

        for (Hotel hotel : hotelList) {
            System.out.format("|     > %-35s|\n", hotel.getHotelName());
        }
        System.out.println(".------------------------------------------.");
    }

    /**
     * Determines if a user's input is a valid integer.
     * Returns the integer input if the input is valid.
     * Prints an error message and returns -1 otherwise.
     * 
     * @return user's input if valid, -1 if not
     */
    private static int getIntInput() {
        if (scanner.hasNextInt()) {
            int input = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            return input;
        } else {
            scanner.next(); // clear the invalid input
            return -1; // signals that the input was invalid
        }
    }

    /**
     * Checks if an entered hotel name does not yet exist among the list of hotels.
     * 
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
     * 
     * @param hotelList list of available hotels
     */
    private static void createHotel(ArrayList<Hotel> hotelList) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nHotel Name: ");
        String hotelName = scanner.nextLine();

        if (isUniqueName(hotelList, hotelName)) { // checks if hotel name is unique before creating a new hotel object
            Hotel hotel = new Hotel(hotelName);
            hotelList.add(hotel); // adds hotel to hotelList
            hotelCount++; // increase number of hotels
            System.out.println(hotelName + " was created!");

            hotel.addRooms();

            System.out.println("Number of rooms: " + hotel.getNRooms());
        }
    }

    /**
     * Allows user to view the date, room, and reservation information of a given
     * hotel.
     * 
     * @param hotelList list of available hotels
     */
    private static void viewHotel(ArrayList<Hotel> hotelList) {

        Scanner scanner = new Scanner(System.in);

        if (hotelCount > 0) {
            printHotels();

            System.out.println("\nWhich hotel do you wish to view? ");
            System.out.print("> Enter choice: ");
            String name = scanner.nextLine();

            boolean found = false;

            for (Hotel hotel : hotelList) {
                if (hotel.getHotelName().equals(name)) { // check if entered name exists within hotelList

                    System.out.print("\n");
                    hotel.printHotelInformation();

                    System.out.println(".------------------------------------------------------.");
                    System.out.println("|              LOW LEVEL HOTEL INFORMATION             |");
                    System.out.println(".------------------------------------------------------.");
                    System.out.println("|    [    DATE    ]  [    ROOM    ]  [ RESERVATION ]   |");
                    System.out.println(".------------------------------------------------------.");

                    System.out.print("> Enter choice: ");
                    String choice = scanner.nextLine();
                    choice = choice.toUpperCase();

                    switch (choice) {
                        case "DATE":
                            while (true) { // checks if date input is valid
                                System.out.print("Date: ");
                                int date = getIntInput();

                                if ((date >= 1 && date <= 31) && date > 0) {
                                    System.out.print("Number of rooms available on " + date + ": "
                                            + hotel.roomsAvailableOnDate(date));
                                    break;
                                } else
                                    System.out.println("Invalid date. Please enter a valid date.");
                            }
                            break;
                        case "ROOM":
                            hotel.printRooms();
                            hotel.printRoomInfo();
                            break;

                        case "RESERVATION":
                            if (hotel.totalReservations() == 0) { // checks if there are any reservations in the hotel
                                System.out.println("There are no reservations in this hotel.");
                                return;
                            } else {
                                hotel.printAllReservations();
                                hotel.printReservationInfo();
                            }
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
        } else {
            System.out.println("There are no hotels to view at the moment.");
        }

    }

    /**
     * Allow the user to manage the hotel by changing the hotel name, adding and
     * removing rooms,
     * updating the base price of a room, removing an existing reservation, and
     * removing the hotel itself.
     * 
     * @param hotelList list of available hotels
     */
    private static void manageHotel(ArrayList<Hotel> hotelList) {

        if (hotelCount > 0) {

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
            System.out.println("|                 [Date Price Modifier]                |");
            System.out.println("|                                                      |");
            System.out.println("|                  [Remove Reservation]                |");
            System.out.println("|                                                      |");
            System.out.println("|                     [Remove Hotel]                   |");
            System.out.println(".------------------------------------------------------.");

            System.out.print("> Enter choice: ");
            Scanner scanner = new Scanner(System.in);
            String manageChoice = scanner.nextLine();
            manageChoice = manageChoice.toLowerCase();
            boolean hotelFound = false;
            String hotelName;
            String newName;
            boolean newNameIsValid = false;

            switch (manageChoice) {
                case "change hotel name":
                    System.out.println("Please select a hotel from the following: ");
                    printHotels();
                    System.out.print("Hotel Name: ");
                    hotelName = scanner.nextLine();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getHotelName().equals(hotelName)) { // checks if entered hotel name exists
                            hotelFound = true;

                            while (!newNameIsValid) { // repeatedly asks for new hotel name until it is valid
                                System.out.print("New Name: ");
                                newName = scanner.nextLine();
                                newNameIsValid = isUniqueName(hotelList, newName); // checks if new name is unique
                                if (newNameIsValid) {
                                    System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                                    char c = scanner.nextLine().charAt(0);
                                    if (c == 'Y' || c == 'y') {
                                        hotel.setHotelName(newName);
                                        System.out.println("Hotel name successfully changed to " + newName);
                                    } else if (c == 'N' || c == 'n') {
                                        System.out.println("Modification cancelled.");
                                    } else {
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

                case "add rooms":
                    System.out.println("Please select a hotel from the following: ");
                    printHotels();
                    System.out.print("\n> Enter the hotel name you wish to add rooms to: ");
                    String hotelNameToAddRooms = scanner.nextLine();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getHotelName().equals(hotelNameToAddRooms)) { // if hotel selected exists
                            hotel.addRooms();
                            hotelFound = true;
                            break;
                        }
                    }
                    if (!hotelFound) {
                        System.out.println("Hotel not found.");
                    }
                    break;

                case "remove rooms":
                    System.out.println("Please select a hotel from the following: ");
                    printHotels();
                    System.out.print("\n> Enter hotel name to remove rooms from: ");
                    String hotelNameToRemoveRooms = scanner.nextLine();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getHotelName().equals(hotelNameToRemoveRooms)) {
                            hotelFound = true;

                            if (hotel.getNRooms() == 1) { // if there is only one room in the hotel
                                System.out.println("There are no rooms to remove.");
                                return;
                            } else {
                                hotel.removeRooms();
                            }
                            break;
                        }
                    }
                    if (!hotelFound) {
                        System.out.println("Hotel not found.");
                    }
                    break;

                case "update room base price":
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
                case "date price modifier":
                    System.out.println("\nPlease select a hotel from the following: ");
                    printHotels();
                    System.out.print("\n> Enter hotel name to modify date price: ");
                    String hotelChoice = scanner.nextLine();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getHotelName().equals(hotelChoice)) {
                            hotel.printMultiplierDatabase();
                            hotel.datePriceModifier();
                            hotelFound = true;
                            break;
                        }
                    }
                    if (!hotelFound) {
                        System.out.println("Hotel not found.");
                    }

                    break;
                case "remove reservation":
                    System.out.println("Please select a hotel from the following: ");
                    printHotels();
                    System.out.print("\n> Enter choice: ");
                    String hotelNameToRemoveReservation = scanner.nextLine();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getHotelName().equals(hotelNameToRemoveReservation)) {
                            hotelFound = true;

                            if (hotel.totalReservations() == 0) { // if there are no reservations in the hotel
                                System.out.println("There are no reservations in this hotel.");
                            } else {
                                hotel.removeReservation();
                            }

                            break;
                        }
                    }
                    if (!hotelFound) {
                        System.out.println("Hotel not found.");
                    }

                    break;

                case "remove hotel":
                    System.out.println("Please select a hotel from the following: ");
                    printHotels();
                    System.out.print("\n> Enter hotel name to remove: ");
                    String removeHotelName = scanner.nextLine();

                    for (int i = 0; i < hotelList.size(); i++) {
                        Hotel hotel = hotelList.get(i);
                        if (hotel.getHotelName().equals(removeHotelName)) {
                            System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                            char confirm = scanner.nextLine().charAt(0);

                            if (confirm == 'Y' || confirm == 'y') {
                                hotelList.remove(i);
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
        } else {
            System.out.println("No hotels available.");
        }
    }

    /**
     * Allows the user to book a reservation in the hotel by entering information
     * such as
     * their name, and the dates they wish to check in and check out.
     * 
     * @param hotelList list of available hotels
     */
    private static void simulateBooking(ArrayList<Hotel> hotelList) {
        if (hotelCount > 0) {
            Scanner scanner = new Scanner(System.in);
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
        } else {
            System.out.println("No hotels available.");
        }

    }

    public static void main(String[] args) {

        while (true) {
            printMenu();
            System.out.print("> Enter choice: ");

            String choice = scanner.nextLine();
            choice = choice.toLowerCase();

            switch (choice) {
                case "create hotel":
                    createHotel(hotelList);
                    break;
                case "view hotel":
                    viewHotel(hotelList);
                    break;
                case "manage hotel":
                    manageHotel(hotelList);
                    break;
                case "simulate booking":
                    simulateBooking(hotelList);
                    break;
                case "exit":
                    System.out.println("\n       --- Exiting the program. Goodbye! ---");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            choice = ""; // repeats menu
        }

    }
}
