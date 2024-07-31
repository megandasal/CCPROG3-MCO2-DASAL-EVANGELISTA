import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Hotel {

    private double baseRate;
    private String hotelName;
    private int nRooms;
    private double estimateEarnings;
    private int roomCtr; // counts how many rooms have been added so far, even those removed. this will
    // be used for unique naming of rooms
    private ArrayList<Room> rooms = new ArrayList<Room>(); // list of rooms in hotel
    private ArrayList<Reservation> allReservations = new ArrayList<Reservation>(); // keeps track of all the
                                                                                   // reservations

    private int[] multiplierDatabase = new int[30]; // date price modifier for 30 nights

    // in a hotel
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new Hotel object with a given hotel name.
     * Initializes the hotel object with one room, a room base price of 1299.0,
     * and estimated earnings of 0.
     *
     * @param hotelName name of the hotel
     */
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.nRooms = 1;
        this.baseRate = 1299.0;
        rooms.add(new StandardRoom(0, this.baseRate)); // add 1 room to the hotel upon instantiation
        this.estimateEarnings = 0;
        this.roomCtr = 1; // count rooms created, this is only used for naming purposes
        Arrays.fill(multiplierDatabase, 100); //makes price for all dates 100%

        
    }

    public void addRoomsToHotel(Room room) {
        rooms.add(room);
        nRooms++;
    }


    /**
     * Displays the information of a hotel: name, number of rooms, price of rooms,
     * and estimated earnings.
     */
    public void printHotelInformation() {
        System.out.println(".------------------------------------------------------.");
        System.out.println("|             HIGH LEVEL HOTEL INFORMATION             |");
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                                                      |");
        System.out.format("|   Hotel Name: %-38s |\n", this.hotelName);
        System.out.format("|   Number of Rooms: %-33s |\n", this.nRooms);
        System.out.format("|   Base Rate: %-38s |\n", this.baseRate); // REVISE: print different room prices..?
        System.out.format("|   Estimate Earnings: %-31s |\n", this.estimateEarnings);
        System.out.println("|                                                      |");
        System.out.println(".------------------------------------------------------.");
    }

    /**
     * Displays all rooms in a hotel.
     */
    public void printRooms() {
        System.out.println("\n.-------------------------.");
        System.out.println("|          ROOMS          |");
        System.out.println(".-------------------------.");
        for (Room room : rooms) {
            System.out.format("|       [ROOM %s]       |\n", room.getRoomName());
        }
        System.out.println(".-------------------------.");
    }

    /**
     * Prompts user to enter a room number and displays the corresponding room's
     * information if it exists. Prints an error message otherwise.
     */
    public void printRoomInfo() {
        /*
         * CASES
         * 1. input is not a number
         * 2. room number exists
         */

        scanner = new Scanner(System.in);

        boolean roomExists = false;

        System.out.print("Room Name: ");
        String roomName = scanner.nextLine();

        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                roomExists = true;
                room.printRoomInformation();
                break;
            }
        }

        if (!roomExists)
            System.out.println("Room does not exist.");
        return;
    }

    /**
     * Displays all reservations made across a hotel.
     */
    public void printAllReservations() {

        this.allHotelReservations();
        System.out.println("All Reservations:");
        for (int i = 0; i < allReservations.size(); i++) {
            System.out.println(
                    (i + 1) + ". [ROOM " + allReservations.get(i).getRoomName() + "] "
                            + allReservations.get(i).getGuestName());
        }
    }

    /**
     * Prompts the user to enter their unique reservation number. Displays the
     * corresponding
     * reservation information if it exists. Prints an error message otherwise.
     */
    public void printReservationInfo() {

        while (true) { // keeps asking input until valid input is given

            System.out.print("\nView Details for Reservation Number: ");
            int reservationNum = getIntInput();

            if (reservationNum < 1 || reservationNum > allReservations.size()) { // input is not a valid room number
                System.out.println("Invalid input. Please enter a valid reservation number.");
            } else {
                allReservations.get(reservationNum - 1).printReservationInformation(); // input is a valid room number
                break;
            }
        }
    }

    /**
     * Adds 1 to 49 rooms to a hotel.
     */
    public void addRooms() { // turn into roomlist

        if (nRooms == 50) {
            System.out.println("The hotel has reached its maximum capacity of 50 rooms.");
            return;
        } else { // only adds rooms when there are less than 50 rooms
            if (nRooms == 1)
                System.out.println("There is currently " + nRooms + " room available.");
            else
                System.out.println("There are currently " + nRooms + " rooms available.");

            int newRooms;
            Room room;

            System.out.println("\nHow many rooms would you like to add?");
            System.out.print("> Enter amount: ");
            newRooms = getIntInput();

            if (newRooms < 0 && nRooms + newRooms > 50) {
                System.out.println(
                        "\nInvalid number of rooms. There are currently " + nRooms + " rooms.");
            } else {

                if(newRooms != 0){
                    System.out.println(".------------------------------------------------------.");
                    System.out.println("|     STANDARD     |     DELUXE     |     EXECUTIVE    |");
                    System.out.println(".------------------------------------------------------.");

                    String roomTypeChoice;
                    System.out.print("\nWhat type of room would you like to add?: ");
                    roomTypeChoice = scanner.nextLine();

                    for (int i = 0; i < newRooms; i++) {
                        switch (roomTypeChoice.toLowerCase()) {
                            case "standard":
                                room = new StandardRoom(roomCtr, this.baseRate);
                                rooms.add(room);
                                break;
                            case "deluxe":
                                room = new DeluxeRoom(roomCtr, this.baseRate);
                                rooms.add(room);
                                break;
                            case "executive":
                                room = new ExecutiveRoom(roomCtr, this.baseRate);
                                rooms.add(room);
                                break;
                            default:
                                System.out.println("Invalid room type. Please try again.");
                                return;
                        }
                        nRooms++;
                        roomCtr++;
                    }
                }
            }

            // REVISE: VERIFY MODIFICATION

        }

    }

    // controller implementation
    public void addRoomToHotel(int totalRooms, int stdRooms, int dlxRooms, int execRooms) {
        // Ensure the sum of room types matches the totalRooms
        if (stdRooms + dlxRooms + execRooms != totalRooms) {
            System.out.println("The total number of rooms does not match the sum of room types provided.");
            return;
        }
        
        // Check if adding these rooms would exceed the hotel's capacity
        if (this.nRooms + totalRooms > 50) {
            System.out.println("Adding these rooms would exceed the hotel's capacity of 50 rooms.");
            return;
        }
        
        for (int i = 0; i < totalRooms; i++) {
            if (stdRooms > 0) {
                rooms.add(new StandardRoom(roomCtr, this.baseRate));
                stdRooms--;
            } else if (dlxRooms > 0) {
                rooms.add(new DeluxeRoom(roomCtr, this.baseRate));
                dlxRooms--;
            } else if (execRooms > 0) {
                rooms.add(new ExecutiveRoom(roomCtr, this.baseRate));
                execRooms--;
            }
            roomCtr++;
        }
        this.nRooms += totalRooms;
    }

    public void addHotelRooms(int totalRooms, int stdRooms, int dlxRooms, int execRooms) {
        // Ensure the sum of room types matches the totalRooms
        if (stdRooms + dlxRooms + execRooms != totalRooms) {
            System.out.println("The total number of rooms does not match the sum of room types provided.");
            return;
        }
    
        // Check if adding these rooms would exceed the hotel's capacity
        if (this.nRooms + totalRooms > 50) {
            System.out.println("Adding these rooms would exceed the hotel's capacity of 50 rooms.");
            return;
        }
    
        for (int i = 0; i < totalRooms; i++) {
            if (stdRooms > 0) {
                rooms.add(new StandardRoom(roomCtr, this.baseRate));
                stdRooms--;
            } else if (dlxRooms > 0) {
                rooms.add(new DeluxeRoom(roomCtr, this.baseRate));
                dlxRooms--;
            } else if (execRooms > 0) {
                rooms.add(new ExecutiveRoom(roomCtr, this.baseRate));
                execRooms--;
            }
            roomCtr++;
        }
        this.nRooms += totalRooms;
        System.out.println("Updated nRooms to: " + this.nRooms); // Debugging
    }
    



    /**
     * Checks the availability of a room for reservation given a check-in and
     * check-out date.
     *
     * @param checkInDate  check-in date
     * @param checkOutDate check-out date
     * @return array list of Room objects available for specified date range
     */
    public ArrayList<Room> checkRoomAvailability(int checkInDate, int checkOutDate) {

        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean available = true;

            // Check if room has reservations starting or ending on checkInDate or
            // checkOutDate
            if (room.isReservationStartingEndingOn(checkInDate, true)
                    || room.isReservationStartingEndingOn(checkOutDate, false)) {
                available = false;
            }

            // Check availability for each day in the date range
            for (int day = checkInDate; day <= checkOutDate; day++) {
                if (!room.isAvailable(day)) {
                    available = false;
                    break;
                }
            }

            // Check for overlapping reservations within the date range
            for (int day = checkInDate + 1; day < checkOutDate; day++) {
                if (room.isReservationStartingEndingOn(day, false) || room.isReservationStartingEndingOn(day, true)) {
                    available = false;
                    break;
                }
            }

            // If room is available, add it to availableRooms
            if (available) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Handles booking process of a room in a given hotel.
     * User is prompted to enter their name, check-in and check-out dates,
     * and room number they wish to reserve. Creates a reservation and updates the
     * hotel's estimated earnings.
     *
     * @return true if a booking is successful, false otherwise
     */
    public void bookRoom() {

        Scanner scanner = new Scanner(System.in);

        // signals whether booking is successful/not, default is false and will only be
        // true once all conditions are met

        System.out.print("Enter name to book reservation: ");
        String guestName = scanner.nextLine();

        int checkInDate;
        int checkOutDate;

        while (true) {
            System.out.print("\nEnter Check In Date: ");
            checkInDate = getIntInput();

            if (checkInDate < 1 || checkInDate > 30) { // validates check-in date
                System.out.println("Invalid input. Please enter a valid check in date.");
            } else {
                break;
            }

        }

        while (true) {
            System.out.print("Enter Check Out Date: ");
            checkOutDate = getIntInput();

            if (checkOutDate <= checkInDate || checkOutDate > 31) // validates check-out date
                System.out.println("Invalid input. Please enter a valid check out date.");
            else
                break;

        }

        ArrayList<Room> availableRooms = this.checkRoomAvailability(checkInDate, checkOutDate); // room availability is
        // checked given the
        // check-in and check-out
        // dates

        int roomTypeCtr = 0;

        if (availableRooms.size() > 0) { // if there are rooms available, it is printed

            System.out.println(".-------------------------.");
            System.out.println("|        STANDARD         |");
            System.out.println("|-------------------------|");
            
            for (Room room : availableRooms) { // prints available standard rooms
                if (room.getRoomType().equals("Standard")) {
                    System.out.format("|       [ROOM %s]       |\n", room.getRoomName());
                    roomTypeCtr++;
                }
            }

            if (roomTypeCtr == 0)
                System.out.println("|            -            |");

            roomTypeCtr = 0;

            System.out.println("|-------------------------|");
            System.out.println("|          DELUXE         |");
            System.out.println("|-------------------------|");

            for (Room room : availableRooms) { // prints available deluxe rooms
                if (room.getRoomType().equals("Deluxe")) {
                    System.out.format("|       [ROOM %s]       |\n", room.getRoomName());
                    roomTypeCtr++;
                }
            }

            if (roomTypeCtr == 0)
                System.out.println("|            -            |");

            roomTypeCtr = 0;

            System.out.println("|-------------------------|");
            System.out.println("|        EXECUTIVE        |");
            System.out.println("|-------------------------|");

            for (Room room : availableRooms) { // prints available executive rooms
                if (room.getRoomType().equals("Executive")) {
                    System.out.format("|       [ROOM %s]       |\n", room.getRoomName());
                    roomTypeCtr++;
                }
            }

            if (roomTypeCtr == 0)
                System.out.println("|            -            |");

            roomTypeCtr = 0;

            System.out.println(".-------------------------.");
        } else if (availableRooms.size() == 0) {
            System.out.println("\nThere are no rooms available on the given dates.");
            return; // returns to menu
        }

        String selectedRoom;

        System.out.print("\nEnter room number to book: ");
        selectedRoom = scanner.nextLine();

        boolean found = false;

        for (Room room : availableRooms) { // checks if room exists in available rooms array list and room availability
            // array is updated
            if (room.getRoomName().equals(selectedRoom)) { // checks if room exists
                found = true;

                if (room.isReservationStartingEndingOn(checkInDate, false) || checkInDate == 1) { // checks if it
                                                                                                  // overlaps with
                    // an existing reservation,
                    // then marks the day as
                    // unavailable
                    room.setRoomAvailability(checkInDate, false);
                }
                if (room.isReservationStartingEndingOn(checkOutDate, true) || checkOutDate == 31) { // checks if it
                                                                                                    // overlaps
                    // with an existing
                    // reservation, then marks
                    // the day as unavailable
                    room.setRoomAvailability(checkOutDate, false);
                }

                for (int day = checkInDate + 1; day < checkOutDate; day++) { // marks the days in between as unavailable
                    room.setRoomAvailability(day, false);
                }


                room.updateNDaysAvailable(); // updates the number of days available in the room

                System.out.print("\nDiscount Code: ");
                String discountCode = scanner.nextLine();
                Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room, multiplierDatabase); // creates
                // a
                // new
                // reservation
                // object
                reservation.applyDiscount(discountCode);
                room.addReservation(reservation); // adds new reservation to room's reservation list
                updateEstimateEarnings(reservation.getTotalPrice(), true); // updates the hotel's estimated earnings
                this.allHotelReservations(); // adds new reservation to hotel's reservation list
                System.out.println("\n*** Successfully booked Room " + selectedRoom + " for " + guestName + "! ***\n");
                reservation.printReceipt(this.hotelName);
                break;
            }
        }

        if (!found) { // room does not exist in available rooms array list
            System.out.println("Room not found.");
            return; // returns to menu
        }
    }

    public int isValidReservationGUI(String guestName, int checkInDate, int checkOutDate, String roomToBook, String discountCode, ArrayList<Room> availableRooms) {

        if (checkInDate >= checkOutDate) {
            return -1; // invalid date range
        }
        else if (guestName == null || guestName.equals("")) {
            return -2; // invalid guest name
        }
        else if (discountCode == null || discountCode.equals("")) {
            return -3; // no discount code used
        }
        else if (!discountCode.equals("I_WORK_HERE") && !discountCode.equals("STAY4_GET1") && !discountCode.equals("PAYDAY")) {
            return -4; // invalid discount code
        }
        else if (!availableRooms.stream().anyMatch(room -> room.getRoomName().equals(roomToBook))) {
            return -5; // room not found
        } 
        return 1; // valid reservation
    }

    public String bookRoomGUI(String guestName, int checkInDate, int checkOutDate, String roomToBook, String discountCode, int isValidDiscount) {
        

        // get available rooms for the specified date range
        ArrayList<Room> availableRooms = this.checkRoomAvailability(checkInDate, checkOutDate);
    
        // check if the selected room is available
        Room roomToBookObject = null;
        for (Room room : availableRooms) {
            if (room.getRoomName().equals(roomToBook)) {
                roomToBookObject = room;
                break;
            }
        }
    
        if (roomToBookObject == null) {
            return ""; // room not found or not available
        }
    
        // book the room

        if (roomToBookObject.isReservationStartingEndingOn(checkInDate, false) || checkInDate == 1) { // checks if it
            // overlaps with an existing reservation, then marks the day as unavailable
            roomToBookObject.setRoomAvailability(checkInDate, false);
        }
        if (roomToBookObject.isReservationStartingEndingOn(checkOutDate, true) || checkOutDate == 31) { // checks if it
            // overlaps with an existing reservation, then marks the day as unavailable
            roomToBookObject.setRoomAvailability(checkOutDate, false);
        }

        for (int day = checkInDate + 1; day < checkOutDate; day++) { // marks the days in between as unavailable
            roomToBookObject.setRoomAvailability(day, false);
        }


        roomToBookObject.updateNDaysAvailable(); // updates the number of days available in the room
    
        Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, roomToBookObject, multiplierDatabase);
        isValidDiscount = reservation.applyDiscount(discountCode); 
        roomToBookObject.addReservation(reservation);
        updateEstimateEarnings(reservation.getTotalPrice(), true);
        this.allHotelReservations();
        
        // return booking receipt for display after a successful reservation
        String bookingReceipt = printBookingReceipt(reservation);
        return bookingReceipt;
    }
    
    /**
     * Collects and adds all reservations across all rooms in a hotel.
     */
    public void allHotelReservations() {
        allReservations.clear();
        for (Room room : rooms) {
            allReservations.addAll(room.getReservations());
        }
    }

    public ArrayList<Reservation> getAllReservations() {
        return this.allReservations;
    }

    /**
     * Handles the process of removing rooms in a hotel. Prompts the user to enter
     * the room number they wish to remove and removes it if the room exists and
     * does not hold an active reservation.
     */
    public void removeRooms() {

        scanner = new Scanner(System.in);

        boolean roomFound = false;
        boolean roomRemoved = false;
        int i = 0;
        String roomName;

        System.out.println("\n.------------------------------------------.");
        System.out.println("|                  ROOMS                   |");
        System.out.println(".------------------------------------------.");
        for (Room room : rooms) {
            if (room.getNReservations() == 0)
                System.out.format("|              [-][ROOM %s]              |\n", room.getRoomName());
            else
                System.out.format("|              [*][ROOM %s]              |\n", room.getRoomName());
        }
        System.out.println("|  - no reservation  |  * has reservation  |");
        System.out.println(".------------------------------------------.");

        System.out.print("\n> Enter the room number to remove: ");
        roomName = scanner.nextLine();

        /*
         * if (roomNum == -1) {
         * System.out.println("Invalid input. Please enter a valid room number.");
         * } else {
         * break;
         * }
         */

        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) { // if room exists in hotel

                roomFound = true;

                if (room.getNReservations() == 0) { // if room doesnt have a reservation
                    System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                    char confirm = scanner.next().charAt(0);
                    if (confirm == 'Y' || confirm == 'y') {
                        rooms.remove(i);
                        this.nRooms--;
                        System.out.println("Room " + roomName + " was successfully removed!");
                        roomRemoved = true;
                        break;
                    } else if (confirm == 'N' || confirm == 'n') {
                        System.out.println("Modification cancelled.");
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }
            i++;
        }
        if (roomFound) {
            if (!roomRemoved) {
                System.out.println("This room currently has a reservation and can't be removed.");
            }
            /*
             * else{//update room nums
             *
             * int roomCount = 1;
             * for (Room room : rooms) {
             * room.setRoomNum(100 + roomCount);
             * roomCount++;
             *
             * }
             * }
             */
        } else {
            System.out.println("Room does not exist.");
        }
    }

    // gui implementation
    public int removeRoomFromHotel (String roomName) {
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomName().equals(roomName)) { // if room exists in hotel
                if (room.getNReservations() == 0) { // if room doesnt have a reservation
                    iterator.remove(); // safely remove the room using iterator
                    this.nRooms--;
                    return 1; // exit after removing the first matching room
                }
            }
        }
        return -1; // room not found or has an active reservation
    }
    

    /**
     * Removes a reservation from the hotel by asking the user for their
     * unique Booking ID.
     */
    public void removeReservation() {
        boolean reservationFound = false;

        // scanner.nextLine(); // buffer
        System.out.print("\nPlease enter your unique booking ID to remove your reservation: ");
        String bookingID = scanner.nextLine();

        for (Room room : rooms) {

            for (int i = 0; i < room.getReservations().size(); i++) {
                if (room.getReservations().get(i).getBookingID().equals(bookingID)) { // check if booking ID matches a
                    // reservation made by iterating through
                    // reservation array
                    reservationFound = true;
                    System.out.println("Reservation found!");
                    System.out.print("\nWould you like to proceed with this modification? [Y/N]: ");
                    char confirm = scanner.next().charAt(0);

                    if (confirm == 'Y' || confirm == 'y') {

                        int checkInDate = room.getReservations().get(i).getCheckInDate(); // retrieve check-in date
                        int checkOutDate = room.getReservations().get(i).getCheckOutDate(); // retrieve check-out date

                        this.updateEstimateEarnings(room.getReservations().get(i).getTotalPrice(), false); // removes
                                                                                                           // reservation
                        // total price from
                        // earnings
                        for (int day = checkInDate; day <= checkOutDate; day++) { // sets back the days in between to
                                                                                  // available
                            room.setRoomAvailability(day, true); // updates room availability on dates
                        }

                        room.updateNDaysAvailable(); // updates room days available

                        room.removeReservation(i); // removes the reservation instance
                        this.allHotelReservations(); // updates reservation list
                        System.out.println("Your reservation has been successfully removed.");
                        break;
                    } else if (confirm == 'N' || confirm == 'n') {
                        System.out.println("Modification cancelled.");
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;
                }
            }

            if (reservationFound)
                break;
        }

        if (!reservationFound) {
            System.out.println("This reservation does not exist.");
        }
    }

    public int removeReservationGUI(String bookingID, int checkInDate, int checkOutDate) {
        for (Room room : rooms) {
            for (int i = 0; i < room.getReservations().size(); i++) {
                if (room.getReservations().get(i).getBookingID().equals(bookingID)) { // check if booking ID matches a
                    // reservation made by iterating through
                    // reservation array
                    checkInDate = room.getReservations().get(i).getCheckInDate(); // retrieve check-in date
                    checkOutDate = room.getReservations().get(i).getCheckOutDate(); // retrieve check-out date

                    this.updateEstimateEarnings(room.getReservations().get(i).getTotalPrice(), false); // removes
                                                                                                           // reservation
                    // total price from
                    // earnings
                    for (int day = checkInDate; day <= checkOutDate; day++) { // sets back the days in between to
                                                                                // available
                        room.setRoomAvailability(day, true); // updates room availability on dates
                    }

                    room.updateNDaysAvailable(); // updates room days available

                    room.removeReservation(i); // removes the reservation instance
                    this.allHotelReservations(); // updates reservation list
                    System.out.println("Your reservation has been successfully removed.");
                    return 1; // reservation removed successfully
                }
            }
        }
        return -1; // reservation not found
    }

    /**
     * Updates the base price of a room given that no room in the hotel
     * is reserved and that the new price entered by the user is not
     * lower than 100.00.
     */
    public void updateRoomPrice() {

        boolean isReserved = false;

        for (Room room : rooms) {
            if (room.getNReservations() > 0) { // checks if there are any rooms that have been reserved
                isReserved = true; // by immediately setting this variable to true
            }
        }

        if (isReserved == false) { // if there are no rooms reserved, the user is prompted to enter a new room base
                                   // price
            System.out.println("The current base price for a room is: " + this.baseRate);

            double newPrice;

            while (true) {
                System.out.print("\nEnter the new base price for a room: ");
                if (scanner.hasNextDouble()) {
                    newPrice = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline character
                    break;
                } else {
                    scanner.next(); // clear the invalid input// signals that the input was invalid
                }
            }

            if (newPrice >= 100.00) { // if the new price is greater than or equal to 100.00, the room price is
                                      // updated
                System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                char c = scanner.nextLine().charAt(0);
                if (c == 'Y' || c == 'y') {
                    setBaseRate(newPrice); // update room price for each room type in room class
                    this.baseRate = newPrice; // updates base price in hotel class
                    System.out.println("\nThe new base price for a room has been updated to " +
                            this.baseRate + "!");
                } else if (c == 'N' || c == 'n') {
                    System.out.println("Modification cancelled.");
                } else {
                    System.out.println("Invalid input.");
                }
            } else {
                System.out.println("\nInvalid room price. Please enter a value greater than 100.00.");
            }
        } else {
            System.out.println("\nUnable to update room base price due to active reservations in this hotel.");
        }

    }

    public void printMultiplierDatabase(){

        System.out.println(".------------------------------------------------------.");
        System.out.println("|                   DATE ROOM RATES                    |");
        System.out.println(".------------------------------------------------------.");
        System.out.println("|           Day             |           Rate           |");
        System.out.println(".------------------------------------------------------.");

        for(int i = 0; i < 30; i++){
            System.out.format("|          %2d - %-2d          |           %3d%%           |\n",i+1, i+2, multiplierDatabase[i]);
        }
        System.out.println("|                                                      |");
        System.out.println(".------------------------------------------------------.");
    }

    public String getMultiplierDatabaseToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(".------------------------------------------------------.");
        sb.append("\n|                   DATE ROOM RATES                    |");
        sb.append("\n.------------------------------------------------------.");
        sb.append("\n|           Day             |           Rate           |");
        sb.append("\n.------------------------------------------------------.");

        for (int i = 0; i < 30; i++) {
            if (i <= 8) {
                sb.append(String.format("\n|          %2d - %-2d            |           %3d%%           |", i + 1, i + 2, multiplierDatabase[i]));
            } else if (i == 9 || i == 10) {
                sb.append(String.format("\n|          %2d - %-2d          |           %3d%%           |", i + 1, i + 2, multiplierDatabase[i]));
            } else {
                sb.append(String.format("\n|          %2d - %-2d          |           %3d%%           |", i + 1, i + 2, multiplierDatabase[i]));
            }
            }    
        sb.append("\n|                                                      |");
        sb.append("\n.------------------------------------------------------.");

        return sb.toString();
    }


    public void datePriceModifier(){

        int userChoice, newRate;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which day do you wish to modify?");

        while(true){

            System.out.print("Enter the starting day number: ");
            userChoice = scanner.nextInt();

             // Clear the newline character left by nextInt
            scanner.nextLine();

            if(userChoice >= 1 && userChoice <= 30){

                System.out.print("Enter the percentage you wish to modify it to (within 50%-150%): ");
                newRate = scanner.nextInt();

                // Clear the newline character left by nextInt
                scanner.nextLine();

                //check for validity
                if(newRate >= 50 && newRate <= 150){
                    System.out.print("Would you like to proceed with this modification? [Y/N]: ");
                    char mod = scanner.nextLine().charAt(0);
                    if (mod == 'Y' || mod == 'y') {
                        multiplierDatabase[userChoice-1] = newRate;
                        System.out.println("\nThe new rate for day " + userChoice + " has been updated to " +
                                newRate + "!");
                        return;
                    } else if (mod == 'N' || mod == 'n') {
                        System.out.println("Modification cancelled.");
                        return;
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
                else{
                    System.out.println("Invalid. Input a rate between 50% and 150%");
                }
            }else
                System.out.println("Invalid. Please try again.");

        }
        
    }

    public void datePriceModifierGUI(int startDay, int newRate){
        multiplierDatabase[startDay-1] = newRate;
    }

    /*
    public void updateMultiplierDatabase(int startDate, int endDate, int multiplier){
        for (int i = startingDate; i < endingDate; i++) {
            this.multiplierDatabase[i + 1] = multiplier;
        }
    }

    public void modifyDatePrice(int startDate, int endDate) {
      boolean isValidDateRange;
        
        System.out.println("The current base price for a room is: " + this.baseRate);
        System.out.println("Please enter the start and end dates for price modification.");

        // input dates
        System.out.print("\nStart on: ");
        int startingDate = getIntInput();
        if (startingDate == -1){
            System.out.println("Please enter a valid date.");
            return;
        }
        else {
            System.out.print("End on: ");
            int endingDate = getIntInput();
            if (endingDate == -1){
                System.out.println("Please enter a valid date.");
                return;
            }
            else {
                // check for valid date range
                if (startingDate > endingDate || startingDate == endingDate){
                    isValidDateRange = false;
                }
                else {
                    isValidDateRange = true;
                }

                if (isValidDateRange = true){
                    System.out.println("\nEnter multiplier: "); // integer input
                    int multiplier = getIntInput();
                    if (multiplier == -1){
                        System.out.println("Invalid value. Try again.");
                        return;
                    }
                    else if (multiplier >= 50 && multiplier <= 150){
                        // update database array to specified price modifier
                        updateMultiplierDatabase(startDate, endDate, multiplier);
                            
                        // make separate method for calculating new price
                        reservation.computeTotalPrice();
                        }
                    else {
                        System.out.println("Please enter a value between 50 and 150.");
                        return;
                    }
                }
                else {
                    System.out.println("Please enter a valid date range.");
                    return;
                }
            }
        }        
    }
    */

    /**
     * Updates the estimated earnings of a hotel by adding the total price
     * of active reservations when new reservations are made and subtracting from it
     * when
     * reservations are deleted.
     *
     * @param reservationTotalPrice total price of all reservations across a hotel
     * @param newBooking            represents a reservation
     */
    public void updateEstimateEarnings(double reservationTotalPrice, boolean newBooking) {
        if (newBooking == true)
            this.estimateEarnings = this.estimateEarnings + reservationTotalPrice;
        else
            this.estimateEarnings = this.estimateEarnings - reservationTotalPrice;

    }

    /**
     * Retrieves the total number of reservations across a hotel.
     *
     * @return size of array list representing all reservations
     */
    public int totalReservations() {
        return allReservations.size();
    }

    /*
        returns true when discount code is applied
        @param  discountCode is the discount code entered by the user
    */

    /**
     * Determines if a user's input is a valid integer.
     * Returns an integer if the input is valid.
     * Prints an error message and returns -1 otherwise.
     *
     * @return user's input if valid, -1 if not
     */
    private int getIntInput() {

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
     * Retrieves the price of a room in a hotel.
     *
     * @return price of a room as a double
     */
    /*
     * public double getRoomPrice() {
     * return roomPrice;
     * }
     */

     public double getBaseRate(){
        return this.baseRate;
     }

    /**
     * Retrieves the number of rooms available on a given date.
     *
     * @param date the date for which the availability of a room must be checked
     * @return number of rooms with no active reservations
     */
    public int roomsAvailableOnDate(int date) {

        int nRoomsAvailable = 0;

        for (Room room : rooms) {
            if (room.isAvailable(date)) {
                nRoomsAvailable++; // increment number of rooms available
            }
        }

        return nRoomsAvailable;
    }

    /**
     * Retrieves the name of a hotel.
     *
     * @return hotel name as a String
     */
    public String getHotelName() {
        return this.hotelName;
    }

    /**
     * This method retrieves the number of rooms in a hotel.
     *
     * @return number of rooms as an int
     */
    public int getNRooms() {
        return this.nRooms;
    }

    /**
     * Sets the name of a hotel.
     *
     * @param hotelName name of hotel to be set
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Sets the price of a room in a hotel.
     *
     * @param roomPrice price of a room as a double
     */

    public void setBaseRate(double baseRate) {

        for (Room room : rooms) {
            room.setRoomPrice(baseRate);
        }

        this.baseRate = baseRate;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getHotelInformation() {
        String formattedBaseRate = String.format("%.2f", this.baseRate);
        StringBuilder info = new StringBuilder("\n     Hotel Name: " + hotelName +
                                               "\n     Number of Rooms: " + this.nRooms +
                                               "\n     Base Rate: " + formattedBaseRate +
                                               "\n     Estimate Earnings: " + this.estimateEarnings);
        /*
        for (Room room : rooms) {
            info.append(room.getRoomInformation()).append("\n");
        }
        */
        return info.toString();
    }

    // will be used for remove rooms functionality
    public String getRoomInfoForRemoval() {
        StringBuilder sb = new StringBuilder();
        
        // Append header
        sb.append("\n                   .------------------------------------------.\n");
        sb.append("                   |                     ROOMS                   |\n");
        sb.append("                   .------------------------------------------.\n");
        
        // Append room details
        for (Room room : rooms) {
            if (room.getNReservations() == 0) {
                sb.append(String.format("                   |              [-][ROOM %s]              |\n", room.getRoomName()));
            } else {
                sb.append(String.format("                   |              [*][ROOM %s]              |\n", room.getRoomName()));
            }
        }
        
        // Append footer
        sb.append("                   .------------------------------------------.\n");

        return sb.toString();
    }
    

    // will be used in simulate booking gui
    // specifically, in the text area of the eastern part of the frame
    public String printAvailableRoomsToBook() {
        StringBuilder sb = new StringBuilder();
        // Append header
        sb.append("\n                   .------------------------------------------.\n");
        sb.append("                   |                     ROOMS                   |\n");
        sb.append("                   .------------------------------------------.\n");
        
        // Append room details
        for (Room room : rooms) {
            if (room.getNReservations() == 0) {
                sb.append(String.format("                   |              [-][ROOM %s]              |\n", room.getRoomName()));
            }
        }
        // Append footer
        sb.append("                   .------------------------------------------.\n");

        return sb.toString();
    }

    // used in to simulate a booking - the booking receipt display
    public String printBookingReceipt(Reservation reservation) {
        if (reservation == null) {
            return "No booking found.";
        }
    
        // Debugging statements
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room Number: " + reservation.getRoomName());
        System.out.println("Check-In Date: " + reservation.getCheckInDate());
        System.out.println("Check-Out Date: " + reservation.getCheckOutDate());
        System.out.println("Total Price: " + reservation.getTotalPrice());
        System.out.println("Booking ID: " + reservation.getBookingID());
    

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Guest Name: %-17s\n", reservation.getGuestName()));
        sb.append(String.format("Check-in Date: %-14d\n", reservation.getCheckInDate()));
        sb.append(String.format("Check-out Date: %-13d\n", reservation.getCheckOutDate()));
        sb.append(String.format("Room Number: %-16s\n", reservation.getRoomName()));
        sb.append(String.format("Total Price: %-15.2f\n", reservation.getTotalPrice()));
        sb.append(String.format("Booking ID: %-16s\n", reservation.getBookingID()));

        return sb.toString();
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }
}
