import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Reservation{ 
  private String guestName;
  private int checkInDate;
  private int checkOutDate;
  private int numNights;
  private int roomNum;
  private double roomPrice;
  private double totalPrice;
  private String bookingID;

  public Reservation(String guestName, int checkInDate, int checkOutDate, int roomNum, double price){
    this.roomNum = roomNum;
    this.guestName = guestName;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.roomPrice = price;
    this.totalPrice = this.roomPrice * (checkOutDate - checkInDate);
    this.bookingID = this.generateBookingID(roomNum);
  }

  public void printReservationInformation(){
    System.out.println("Guest Name: " + this.guestName);
    System.out.println("Booking ID: " + this.bookingID);
    System.out.println("Room: " + this.roomNum);
    System.out.println("Check In: " + this.checkInDate);
    System.out.println("Check Out: " + this.checkOutDate);
    System.out.println("Cost per night: $" + this.roomPrice);
    System.out.println("Total Price: $" + this.totalPrice);


    //view information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night)
  }
  
  public int getRoomNum(){
    return this.roomNum;
  }
  
  public void calculateNumNights(){
    // calculate number of nights
  } 
  
  public String getGuestName(){
    return guestName;
  }

  public int getCheckInDate(){
    return checkInDate;
  } 

  public int getCheckOutDate(){
    return checkOutDate;
  }

  public int getNumNights(){
    return numNights;
  }

  public double getTotalPrice(){
    return this.totalPrice;
  }

  public String getBookingID() {
    return bookingID;
  }

  public String generateBookingID(int roomNumber){
    int length = 6; // length of randomly generated integer
    Random random = new Random();
    String roomNum = String.valueOf(roomNumber); // convert the integer roomNumber to a string for concatenation
    StringBuilder sb = new StringBuilder(length); // for string concatenation

    for (int i = 0; i < length; i++) {
        sb.append(random.nextInt(10)); // append a random digit (0-9) to the string
    }

    String randomInt = sb.toString();
    String bookingID = randomInt + roomNum; 

    return bookingID;
  }

}
