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

  public Hotel(String hotelName) { // constructor
    this.hotelName = hotelName;
    this.nRooms = 1; // instantiate into roomList
    this.roomPrice = 1299.0;
    rooms.add(new Room(1, this));
    this.estimateEarnings = 0;
  }

  public void updateEstimateEarnings(double reservationTotalPrice, boolean newBooking) {

    /*
     * update esrimate earnings by adding the total price of the reservation when a
     * new reservation is made and subtracts when reservation is deleted
     */
    if (newBooking == true)
      this.estimateEarnings = this.estimateEarnings + reservationTotalPrice;
    else
      this.estimateEarnings = this.estimateEarnings - reservationTotalPrice;

  }

  public double getEstimateEarnings() {
    return this.estimateEarnings;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public String getHotelName() {
    return this.hotelName;
  }

  public int getNRooms() {
    return this.nRooms;
  }

  public void addRooms() { // turn into roomlist

    if (nRooms == 1)
      System.out.println("There is currently " + nRooms + " room available.");
    else
      System.out.println("There are currently " + nRooms + " rooms available.");

    System.out.print("How many rooms would you like to add? ");
    int newRooms = scanner.nextInt();

    /* REVISE THIS PART INTO A LOOP */

    if (nRooms + newRooms < 1 || nRooms + newRooms > 50) {
      System.out.println("Invalid number of rooms. Please enter a number between 1 and 50.");
    }

    if (rooms.size() + newRooms < 50) {
      System.out.print("Would you like to proceed with this modification? [Y/N]: ");
      char confirm = scanner.next().charAt(0);
      if (confirm == 'Y' || confirm == 'y') {
        for (int i = 0; i < newRooms; i++) {
          nRooms++;
          rooms.add(new Room(nRooms, this));
        }
        System.out.println(newRooms + " rooms have been added to the hotel.");
      } else if (confirm == 'N' || confirm == 'n') {
        System.out.println("Modification cancelled.");
      } else {
        System.out.println("Invalid input.");
      }
    }
  }

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

  public int totalReservations() {
    return allReservations.size();
  }

  public boolean bookRoom() {

    boolean bookingIsSuccessful = false;
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter name to book reservation: ");
    String guestName = scanner.nextLine();

    System.out.print("Enter Check In reservation: "); // NO VALIDATION
    int checkInDate = scanner.nextInt();

    System.out.print("Enter Check Out Date: "); // NO VALIDATION
    int checkOutDate = scanner.nextInt();

    scanner.nextLine(); // buffer

    if (checkInDate > checkOutDate) {
      System.out.println("Invalid Dates.");
      return bookingIsSuccessful; // shouldnt print hotel is not available
    }

    ArrayList<Room> availableRooms = this.checkRoomAvailability(checkInDate, checkOutDate);

    for (Room room : availableRooms) {
      System.out.println("- " + room.getRoomNum());
    }

    if (availableRooms.size() == 0) {
      System.out.println("There are no rooms available on the given dates.");
      return bookingIsSuccessful; // returns false
    }

    System.out.print("Enter room number to book: ");
    int selectedRoom = scanner.nextInt();

    boolean found = false;

    for (Room room : availableRooms) {
      if (room.getRoomNum() == selectedRoom) {
        found = true;

        for (int day = checkInDate; day < checkOutDate; day++) { // ex. reservation day 1-6, only until day 5 is
                                                                 // reserved
          room.roomAvailability(day, false);
        }

        Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room.getRoomNum(), roomPrice);
        room.addReservation(reservation);
        updateEstimateEarnings(reservation.getTotalPrice(), true);
        this.printReceipt(room, checkInDate, checkOutDate, guestName, reservation.getBookingID());
        break;
      }

      else {
        System.out.println("Invalid");
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

  public void setRoomPrice(double roomPrice) {
    this.roomPrice = roomPrice;
  }

  public double getRoomPrice() {
    return roomPrice;
  }

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
    System.out.printf("│ \tTotal Price: $%-32.2f │\n", roomPrice * (checkInDate - checkOutDate));

    // Print the bottom border
    System.out.println(".-----------------------------------------------.");
  }

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

  public int roomsAvailableOnDate(int date) {

    int nRoomsAvailable = 0;

    for (Room room : rooms) {
      if (room.isAvailable(date)) {
        nRoomsAvailable++;
      }
    }

    return nRoomsAvailable;
  }

  public void viewRooms() {
    System.out.println("ROOMS");
    for (Room room : rooms) {
      System.out.println("- Room " + room.getRoomNum());
    }
  }

  public void printHotelInformation() {
    System.out.println("Hotel Name: " + this.getHotelName());
    System.out.println("Number of Rooms: " + this.getNRooms());
    System.out.println("Room Price: " + this.getRoomPrice());
    System.out.println("Estimate Earnings: " + this.getEstimateEarnings());
  }

  /* whats this for idnt remmemerb */
  public void viewRoomInfo() {
    System.out.print("Room Number: ");
    int roomNum = scanner.nextInt();
    for (Room room : rooms) {
      if (room.getRoomNum() == roomNum) {
        room.printRoomInformation(); // should i print room info here or from room class
      }
    }
  }

  public void allHotelReservations() { // collects all the reservations in all the rooms
    allReservations.clear();
    for (Room room : rooms) {
      allReservations.addAll(room.getReservations());
    }
  }

  public void printAllReservations() { // prints all reservations made across the hotel

    this.allHotelReservations();
    System.out.println("All Reservations:");
    for (int i = 0; i < allReservations.size(); i++) {
      System.out.println(
          (i + 1) + ". [ROOM " + allReservations.get(i).getRoomNum() + "] " + allReservations.get(i).getGuestName());
    }
  }

  public void viewReservationInfo() {
    System.out.print("View Detals for Reservation Number: ");
    int reservationNum = scanner.nextInt();
    allReservations.get(reservationNum - 1).printReservationInformation();
    // view information about selected reservation (guest information, room
    // information, check-in and check-out dates, total price of booking and
    // breakdown of cost per night)
  }

  public void removeRooms() {

    boolean roomFound = false;
    boolean roomRemoved = false;
    int i = 0;

    for (Room room : rooms) {
      if (room.getNDaysAvailable() == 31)
        System.out.println("- Room " + room.getRoomNum());
      else
        System.out.println("* Room " + room.getRoomNum());
    }

    System.out.println("\n- no reservation          * has reservation");

    System.out.print("\n> Enter the room number to remove: ");
    int roomNum = scanner.nextInt();

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

  public void updateRoomPrice() {
    for (Room room : rooms) {
      if (room.getNDaysAvailable() == 31) {
        System.out.println("The current base price for a room is: " + this.roomPrice);
        System.out.print("\n> Enter the new price for a room: ");

        double newPrice = scanner.nextDouble();
        if (newPrice >= 100.00) {
          System.out.print("Would you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);
          if (confirm == 'Y' || confirm == 'y') {
            System.out.println("Successfully updated the room price to " + newPrice + ".");
            setRoomPrice(newPrice);
            break;
          } else if (confirm == 'N' || confirm == 'n') {
            System.out.println("Modification cancelled.");
          } else {
            System.out.println("Invalid input.");
          }

        } else {
          System.out.println("Invalid room price. Please enter a value greater than 100.00.");
        }
      } else {
        System.out.println("Unable to update room price due to currently active reservations in this hotel.");
        break;
      }

    }
  }

  /* aiella kung mabasa mo to. nababaliw na ako */

  public void removeReservation() {
    boolean reservationFound = false;

    System.out.print("Please enter your unique booking ID to remove your reservation: ");
    scanner.nextLine(); // buffer
    String bookingID = scanner.nextLine();

    for (Room room : rooms) {

      for (int i = 0; i < room.getReservations().size(); i++) {
        if (room.getReservations().get(i).getBookingID().equals(bookingID)) {
          reservationFound = true;
          this.updateEstimateEarnings(room.getReservations().get(i).getTotalPrice(), false);
          room.removeReservation(i);
          System.out.println("Your reservation has been successfully removed.");
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
