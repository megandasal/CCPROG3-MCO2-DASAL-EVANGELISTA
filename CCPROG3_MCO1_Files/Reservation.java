import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Reservation{ 
  private String guestName;
  private int checkInDate;
  private int checkOutDate;
  private int roomNum;
  private double roomPrice;
  private double totalPrice;
  private String bookingID;

  /**
   * Constructs a Reservation object.
   * @param guestName name of guest who made a reservation
   * @param checkInDate date a guest checked in
   * @param checkOutDate date a guest checked out
   * @param roomNum room number the reservation is made under
   * @param price the cost of the reservation
   */
  public Reservation(String guestName, int checkInDate, int checkOutDate, int roomNum, double price){
    this.roomNum = roomNum;
    this.guestName = guestName;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.roomPrice = price;
    this.totalPrice = this.roomPrice * (checkOutDate - checkInDate);
    this.bookingID = this.generateBookingID(roomNum);
  }

  /**
   * Prints out information about a reservation.
   */
  public void printReservationInformation(){
    System.out.println("\n.------------------------------.");
    System.out.println("|     * RESERVATION INFO *     |");
    System.out.println("|                              |");
    System.out.format("| Guest Name: %-16s |\n", this.guestName);
    System.out.format("| Booking ID: %-16s |\n", this.bookingID);
    System.out.format("| Room: %-22d |\n", this.roomNum);
    System.out.format("| Check In: %-18d |\n", this.checkInDate);
    System.out.format("| Check Out: %-17d |\n", this.checkOutDate);
    System.out.format("| Cost per night: $%-11.2f |\n", this.roomPrice); 
    System.out.format("| Total Price: $%-14.2f |\n", this.totalPrice);
    System.out.println(".------------------------------.");
  }

  /**
   * Generates a random booking ID for a successful reservation.
   * @param roomNumber the room number a reservation is made under
   * @return string of 6 random integers concatenated with the given room number
   */
  public String generateBookingID(int roomNumber){
    int length = 6; // length of randomly generated integer
    Random random = new Random();
    String roomNum = String.valueOf(roomNumber); // convert the integer roomNumber to a string for concatenation
    StringBuilder sb = new StringBuilder(length); // used for string concatenation

    for (int i = 0; i < length; i++) {
        sb.append(random.nextInt(10)); // append a random digit (0-9) to the string
    }

    String randomInt = sb.toString();
    String bookingID = randomInt + roomNum; // concatenate the random integer and the room number

    return bookingID;
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
  public void printReceipt(String hotelName) { // move
                                                                                                               // to
                                                                                                               // reservation
    // class + NO LINK TO ROOM
    // YET
    System.out.println(".-----------------------------------------------.");
    // Print the header
    System.out.printf("|          %7s HOTEL RECEIPT                |\n", hotelName); // change to hotel name
    // Print the middle border
    System.out.println(".-----------------------------------------------.");

    // Print hotel and guest information
    System.out.printf("| Guest Name: %-33s |\n", this.guestName);
    System.out.printf("| Booking ID: %-33s |\n", this.bookingID);
    System.out.println("|                                               |");
    System.out.printf("| Check-in:   %-33s |\n", this.checkInDate);
    System.out.printf("| Check-out:  %-33s |\n", this.checkOutDate);

    System.out.println("|                                               |");
    System.out.printf("| Price/Night: $%-31.2f |\n", this.roomPrice);
    System.out.println("|---------------------------                    |");
    System.out.printf("| Total Price: $%-31.2f |\n", this.totalPrice);

    // Print the bottom border
    System.out.println(".-----------------------------------------------.");
  }

  /**
   * Retrieves the reservation's room number.
   * @return room number
   */
  public int getRoomNum(){
    return this.roomNum;
  }

  /**
   * Retrieves the name of the guest who made the reservation.
   * @return guest name
   */
  public String getGuestName(){
    return guestName;
  }

  /**
   * Retrieves the date a guest checked into the hotel.
   * @return check-in date
   */
  public int getCheckInDate(){
      return checkInDate;
  }

  /**
   * Retrieves the date a guest checked out of the hotel.
   * @return check-out date
   */
  public int getCheckOutDate(){
    return checkOutDate;
  }

  /**
   * Retrieves the total cost of each night a guest stays in a hotel.
   * @return total cost/price of all nights stayed
   */
  public double getTotalPrice(){
    return this.totalPrice;
  }

  /**
   * Retrieves the unique booking ID generated for a reservation.
   * @return booking ID
   */
  public String getBookingID() {
    return bookingID;
  }
}
