import java.util.Scanner;
import java.util.ArrayList;

public class Hotel {

  private String hotelName;
  private int nRooms;
  Scanner scanner = new Scanner(System.in);
  private ArrayList<Room> rooms = new ArrayList<Room>();
  private double roomPrice;
  private double estimateEarnings;
  private ArrayList<Reservation> allReservations = new ArrayList<Reservation>();
  private int roomCtr; // counts how many rooms have been added so far, even those removed. this will
                       // be used for unique naming of rooms

  /**
   * Constructs a new Hotel object with a given hotel name.
   * Initializes the hotel object with one room, a room base price of 1299.0,
   * and estimated earnings of 0.
   * @param hotelName name of the hotel
   */
  public Hotel(String hotelName) { // constructor
    this.hotelName = hotelName;
    this.nRooms = 1; // instantiate into roomList
    this.roomPrice = 1299.0;
    rooms.add(new Room(1, this));
    this.estimateEarnings = 0;
    this.roomCtr = 1;
  }

  /**
   * Updates the estimated earnings of a hotel by adding the total price
   * of active reservations when new reservations are made and subtracting from it when
   * reservations are deleted.
   * @param reservationTotalPrice total price of all reservations across a hotel
   * @param newBooking represents a reservation
   */
  public void updateEstimateEarnings(double reservationTotalPrice, boolean newBooking) {
    if (newBooking == true)
      this.estimateEarnings = this.estimateEarnings + reservationTotalPrice;
    else
      this.estimateEarnings = this.estimateEarnings - reservationTotalPrice;

  }

  /**
   * Retrieves the estimated earnings of a hotel.
   * @return estimated earnings as a double
   */
  public double getEstimateEarnings() {
    return this.estimateEarnings;
  }

  /**
   * Sets the name of a hotel.
   * @param hotelName name of hotel to be set
   */
  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  /**
   * Retrieves the name of a hotel.
   * @return hotel name as a String
   */
  public String getHotelName() {
    return this.hotelName;
  }

  /**
   * This method retrieves the number of rooms in a hotel.
   * @return number of rooms as an int
   */
  public int getNRooms() {
    return this.nRooms;
  }

  /**
   * Determines if a user's input is a valid integer.
   * Returns an integer if the input is valid.
   * Prints an error message and returns -1 otherwise.
   * @return user's input if valid, -1 if not
   */
  private int getIntInput() {
    if (scanner.hasNextInt()) {
      int input = scanner.nextInt();
      scanner.nextLine(); // consume the newline character
      return input;
    } else {
      //System.out.println("Invalid input. Please enter an integer.");
      scanner.next(); // clear the invalid input
      return -1; // signals that the input was invalid
    }
  }

  /**
   * Adds 1 to 50 rooms to a hotel.
   */
  public void addRooms() { // turn into roomlist

    if (nRooms == 50) {
      System.out.println("The hotel has reached its maximum capacity of 50 rooms.");
      return;
    } else {
      if (nRooms == 1)
        System.out.println("There is currently " + nRooms + " room available.");
      else
        System.out.println("There are currently " + nRooms + " rooms available.");

      int newRooms;
      while (true) {
        System.out.println("\nHow many rooms would you like to add?");
        System.out.print("> Enter amount: ");
        newRooms = getIntInput();
        if (newRooms >= 0 && nRooms + newRooms <= 50) {
          System.out.print("\nWould you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);
          if (confirm == 'Y' || confirm == 'y') { // turn into method
            for (int i = 0; i < newRooms; i++) {
              nRooms++;
              roomCtr++;
              rooms.add(new Room(roomCtr, this));
            }
            if (newRooms == 1)
              System.out.println("\n" + newRooms + " room has been added to this hotel.");
            else
              System.out.println("\n" + newRooms + " rooms have been added to this hotel.");
            break;
          } else if (confirm == 'N' || confirm == 'n') {
            System.out.println("\nModification cancelled.");
            break;
          } else {
            System.out.println("\nInvalid input.");
          }
        } else {
          System.out.println("\nInvalid number of rooms. Please enter a number between 1 and " + (50 - nRooms) + ".");
        }
      }
    }

  }

  /**
   * Checks the availability of a room for reservation given a check-in and check-out date.
   * @param checkInDate check-in date
   * @param checkOutDate check-out date
   * @return array list of Room objects available for specified date range
   */
  public ArrayList<Room> checkRoomAvailability(int checkInDate, int checkOutDate) {

    /*
     * [/] 1. check if room is available from check in to check out date
     * [/] 2. if room is available, print room number
     * [/] 3. repeat until all rooms in hotel are checked
     */
    boolean available;
    ArrayList<Room> availableRooms = new ArrayList<Room>();

    for (Room room : rooms) {
      available = true;


      for (int day = checkInDate; day <= checkOutDate; day++) {
        if (room.isAvailable(day) == false) {
          day = checkInDate;
          available = false;
          break;
        }
      }

      if (available) {
        availableRooms.add(room);
      }
      
      
    }

    return availableRooms; // returns the list of rooms available
  }

  /**
   * Retrieves the total number of reservations across a hotel.
   * @return size of array list representing all reservations 
   */
  public int totalReservations() {
    return allReservations.size();
  }
  
  /**
   * Handles booking process of a room in a given hotel.
   * User is prompted to enter their name, check-in and check-out dates,
   * and room number they wish to reserve. Creates a reservation and updates the
   * hotel's estimated earnings.
   * @return true if a booking is successful, false otherwise
   */
  public boolean bookRoom() {

    boolean bookingIsSuccessful = false;
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter name to book reservation: ");
    String guestName = scanner.nextLine();

    int checkInDate;
    int checkOutDate;
    
    while(true){
      System.out.print("\nEnter Check In Date: "); 
      checkInDate = getIntInput();

      if(checkInDate < 1 || checkInDate > 30){
        System.out.println("Invalid input. Please enter a valid check in date.");
      }else{
        break;
      }
    }

    while(true){
      System.out.print("Enter Check Out Date: "); 
      checkOutDate = getIntInput();

      if(checkOutDate <= checkInDate || checkOutDate > 31) //checks if check out date is valid
        System.out.println("Invalid input. Please enter a valid check out date.");
      else
        break;
      
    }
    
    ArrayList<Room> availableRooms = this.checkRoomAvailability(checkInDate, checkOutDate); //if this is used elsewhere, make it a method
    if (availableRooms.size() > 0){
      System.out.println("\n--- Available Rooms ---");
      System.println("\n");
      for (Room room : availableRooms) {
        System.out.println("\t      " + room.getRoomNum());
      }
      System.out.println("\n-----------------------");
    }
    else if (availableRooms.size() == 0) {
      System.out.println("\nThere are no rooms available on the given dates.");
      return bookingIsSuccessful; // returns false
    }

    int selectedRoom;

    while(true){
      System.out.print("\nEnter room number to book: ");
      selectedRoom = getIntInput();

      if(selectedRoom != -1){
        break;
      }
    }

    boolean found = false;

    for (Room room : availableRooms) {
      if (room.getRoomNum() == selectedRoom) {
        found = true;

        if(room.isReservationStartingEndingOn(checkInDate,false) || checkInDate == 1){ //checks if it overlaps with an existing reservation, then marks the day as unavailable
          room.setRoomAvailability(checkInDate, false);
        }
        if(room.isReservationStartingEndingOn(checkOutDate,true) || checkOutDate == 31){ //checks if it overlaps with an existing reservation, then marks the day as unavailable
          room.setRoomAvailability(checkOutDate, false);
        }

        for (int day = checkInDate + 1; day < checkOutDate ; day++) { //marks the days in between as unavailable
          room.setRoomAvailability(day, false);
        }

        room.updateNDaysAvailable(); 

        Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room.getRoomNum(), roomPrice);
        room.addReservation(reservation);
        updateEstimateEarnings(reservation.getTotalPrice(), true);
        this.allHotelReservations();
        System.out.println("\n*** Successfully booked Room " + selectedRoom + " for " + guestName + "! ***\n");
        this.printReceipt(room, checkInDate, checkOutDate, guestName, reservation.getBookingID());
        break;
      }
    }

    if (!found) {
      System.out.println("Room not found.");
      return bookingIsSuccessful;
    } // turn into loop so that the user chooses a valid room index
    // update the hotel's estimate earnings per month based on newly made
    // reservation
    // NOTE: ASK FOR HOW TO COMPUTE, DOES IT COUNT DAYS OR THE NIGHTS
    return bookingIsSuccessful;
  }

  /**
   * Sets the price of a room in a hotel.
   * @param roomPrice price of a room as a double
   */
  public void setRoomPrice(double roomPrice) { //is this used?
    this.roomPrice = roomPrice;
  }

  /**
   * Retrieves the price of a room in a hotel.
   * @return price of a room as a double
   */
  public double getRoomPrice() {
    return roomPrice;
  }

  /**
   * Prints the contents of a receipt after a guest makes a booking in a hotel.
   * Displays all booking information.
   * @param room Room object associated with the booking
   * @param checkInDate guest's check-in date
   * @param checkOutDate guest's check-out date
   * @param guestName name where reservation is made under
   * @param bookingID unique string of randomly generated numbers assigned to each booking
   */
  public void printReceipt(Room room, int checkInDate, int checkOutDate, String guestName, String bookingID) { // move
                                                                                                               // to
                                                                                                               // reservation
    // class + NO LINK TO ROOM
    // YET
    System.out.println(".-----------------------------------------------.");
    // Print the header
    System.out.printf("│          %7s HOTEL RECEIPT                │\n", this.hotelName); // change to hotel name
    // Print the middle border
    System.out.println(".-----------------------------------------------.");

    // Print hotel and guest information
    System.out.printf("│ Guest Name: %-33s │\n", guestName);
    System.out.printf("│ Booking ID: %-33s │\n", bookingID);
    System.out.println("|                                               |");
    System.out.printf("│ Check-in:   %-33s │\n", checkInDate);
    System.out.printf("│ Check-out:  %-33s │\n", checkOutDate);

    System.out.println("|                                               |");
    System.out.printf("│ Price/Night: $%-31.2f │\n", roomPrice);
    System.out.println("|---------------------------                    |");
    System.out.printf("│ Total Price: $%-31.2f │\n", roomPrice * (checkInDate - checkOutDate));

    // Print the bottom border
    System.out.println(".-----------------------------------------------.");
  }

  /**
  * Retrieves the availability of a specific room on a given date.
  * @param date the date for which the availability of a room must be checked
  * @param roomName name/number of room to be checked
  * @return number of rooms with no active reservations
  */

  // NO USAGE
  public int getRoomAvailability(int date, int roomName) {

    int roomsAvailable = 0;

    for (Room rooms : rooms) {

      if (rooms.getRoomNum() == roomName) {
        if (rooms.isAvailable(date)) {
          roomsAvailable++;
        }
      }

    }
    return roomsAvailable;
  }

  /**
   * Retrieves the number of rooms available on a given date.
   * @param date the date for which the availability of a room must be checked
   * @return number of rooms with no active reservations
   */
  public int roomsAvailableOnDate(int date) {

    int nRoomsAvailable = 0;

    for (Room room : rooms) {
      if (room.isAvailable(date)) {
        nRoomsAvailable++;
      }
    }

    return nRoomsAvailable;
  }

  /**
   * Displays all rooms in a hotel.
   */
  public void viewRooms() {
    System.out.println("\n--- Available Rooms ---");
    System.println("\n");
    for (Room room : availableRooms) {
      System.out.println("\t      " + room.getRoomNum());
    }
    System.out.println("\n-----------------------");
  }

  /**
   * Displays the information of a hotel: name, number of rooms, price of rooms,
   * and estimated earnings.
   */
  public void printHotelInformation() {
    System.out.println("\n.------------------------------.");
    System.out.println("|        * HOTEL INFO *        |");
    System.out.println("|                              |");
    System.out.format("| Hotel Name: %-16s |\n", this.getHotelName());
    System.out.format("| Number of Rooms: %-12s |\n", this.getNRooms());
    System.out.format("| Room Price: %-16d |\n", this.getRoomPrice());
    System.out.format("| Estimate Earnings: %-9d |\n", this.getEstimateEarnings());
    System.out.println(".------------------------------.");
  }
  
  /**
   * Prompts user to enter a room number and displays the corresponding room's
   * information if it exists. Prints an error message otherwise.
   */
  public void viewRoomInfo() {
    /*
     * CASES
     * 1. input is not a number
     * 2. room number exists
    */

    boolean roomExists = false;
    int roomNum;
    
    while (true) {

      System.out.print("Room Number: ");
      roomNum = getIntInput();

      if(roomNum != -1)
        break;
    
    }
    
    for (Room room : rooms) {
      if (room.getRoomNum() == roomNum) {
        roomExists = true;
        room.printRoomInformation();
      }
    }

    if(roomExists == false)
      System.out.println("Room does not exist.");
    return;
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

  /**
   * Displays all reservations made across a hotel.
   */
  public void printAllReservations() {

    this.allHotelReservations();
    System.out.println("All Reservations:");
    for (int i = 0; i < allReservations.size(); i++) {
      System.out.println(
          (i + 1) + ". [ROOM " + allReservations.get(i).getRoomNum() + "] " + allReservations.get(i).getGuestName());
    }
  }

  /**
   * Prompts the user to enter their unique reservation number. Displays the corresponding
   * reservation information if it exists. Prints an error message otherwise.
   */
  public void viewReservationInfo() {
    /*
     * CASES
     * 1. input is not a number
     * 2. input is not a valid room number
     * 3. room number exists
     */

    while (true) {

      System.out.print("\nView Details for Reservation Number: ");
      int reservationNum = getIntInput();

      if (reservationNum < 1 || reservationNum > allReservations.size()) {
        System.out.println("Invalid input. Please enter a valid reservation number.");
      } else {
        allReservations.get(reservationNum - 1).printReservationInformation();
        break;
      }
    }
    // view information about selected reservation (guest information, room
    // information, check-in and check-out dates, total price of booking and
    // breakdown of cost per night)
  }

  /**
   * Handles the process of removing rooms in a hotel. Prompts the user to enter
   * the room number they wish to remove and removes it if the room exists and
   * does not hold an active reservation.
   */
  public void removeRooms() {

    boolean roomFound = false;
    boolean roomRemoved = false;
    int i = 0;
    int roomNum;

    for (Room room : rooms) {
      if (room.getNDaysAvailable() == 31)
        System.out.println("- Room " + room.getRoomNum());
      else
        System.out.println("* Room " + room.getRoomNum());
    }

    System.out.println("\n- no reservation    |    * has reservation");

    while(true){
      System.out.print("\n> Enter the room number to remove: ");
      roomNum = getIntInput();

      if(roomNum != -1){
        break;
      }
    }

    for (Room room : rooms) {
      if (room.getRoomNum() == roomNum) {

        roomFound = true;

        if (room.getNDaysAvailable() == 31) {
          System.out.print("Would you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);
          if (confirm == 'Y' || confirm == 'y') {
            rooms.remove(i);
            this.nRooms--;
            System.out.println("Room " + roomNum + " was successfully removed!");
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

  /**
   * Updates the base price of a room given that no room in the hotel
   * is reserved and that the new price entered by the user is not
   * lower than 100.00.
   */
  public void updateRoomPrice() {
    boolean isReserved = false;
    
    for (Room room : rooms){
      if (room.getNDaysAvailable() < 31){
        isReserved = true;
      }
    }

    if (isReserved == false){
      System.out.println("The current base price for a room is: " + this.roomPrice);
      
      double newPrice;
      
      while(true){
        System.out.print("\nEnter the new base price for a room: ");
        if (scanner.hasNextDouble()) {
          newPrice = scanner.nextDouble();
          scanner.nextLine(); // consume the newline character
          break;
        } else {
          System.out.println("Invalid input. Please enter a valid price.");
          scanner.next(); // clear the invalid input// signals that the input was invalid
        }
      }

      
      if (newPrice >= 100.00){
        System.out.print("Would you like to proceed with this modification? [Y/N]: ");
        char c = scanner.nextLine().charAt(0);
        if (c == 'Y' || c == 'y'){
          setRoomPrice(newPrice);
          System.out.println("\nThe new base price for a room has been updated to " + this.roomPrice + "!");
        }
        else if (c == 'N' || c == 'n'){
          System.out.println("Modification cancelled.");
        }
        else{
          System.out.println("Invalid input.");
        }
      }
      else{
        System.out.println("\nInvalid room price. Please enter a value greater than 100.00.");
      }
    }
    else{
      System.out.println("\nUnable to udpate room base price due to active reservations in this hotel.");
    }
  }

  /**
   * Removes a reservation from the hotel by asking the user for their
   * unique Booking ID.
   */
  public void removeReservation() {
    boolean reservationFound = false;
    
    //scanner.nextLine(); // buffer
    System.out.print("\nPlease enter your unique booking ID to remove your reservation: ");
    String bookingID = scanner.nextLine();

    for (Room room : rooms) {

      for (int i = 0; i < room.getReservations().size(); i++) {
        if (room.getReservations().get(i).getBookingID().equals(bookingID)) {
          reservationFound = true;
          System.out.println("Reservation found!");
          System.out.print("\nWould you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);

          if (confirm == 'Y' || confirm == 'y') {

            int checkInDate =  room.getReservations().get(i).getCheckInDate();
            int checkOutDate = room.getReservations().get(i).getCheckOutDate();
            
            this.updateEstimateEarnings(room.getReservations().get(i).getTotalPrice(), false); // removes reservation
                                                                                               // total price from
                                                                                               // earnings
            for (int day = checkInDate + 1; day < checkOutDate; day++) { //sets back the days in between to available
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

}
