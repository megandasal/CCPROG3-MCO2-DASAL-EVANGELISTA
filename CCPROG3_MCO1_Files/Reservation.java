import java.util.Scanner;
import java.util.ArrayList;

public class Reservation{ 
  private String guestName;
  private int checkInDate;
  private int checkOutDate;
  private int numNights;
  private int roomNum;
  private double roomPrice;
  private double totalPrice;

  public Reservation(String guestName, int checkInDate, int checkOutDate, int roomNum, double price){
    this.roomNum = roomNum;
    this.guestName = guestName;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.numNights = checkOutDate - checkInDate; //never used
    this.roomPrice = price;
    this.totalPrice = this.roomPrice * (numNights);
  }

  public void printReservationInformation(){
    System.out.println("Guest Name: " + this.guestName);
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

}
