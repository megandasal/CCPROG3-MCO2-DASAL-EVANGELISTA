import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Room {

  private int roomNum;
  private Hotel hotel; //gives access to hotel the room is under

  private boolean[] availability; // represents the availability of a room for each day
  private int nDaysAvailable;
  private ArrayList<Reservation> roomReservations = new ArrayList<Reservation>();
  private ArrayList<String> bookingIDs = new ArrayList<>();

  public Room(int roomCount, Hotel hotel) {
    roomNum = 100 + roomCount; // should update when a room is deleted
    this.availability = new boolean[31];
    Arrays.fill(availability, true);
    this.hotel = hotel;
    nDaysAvailable = availability.length;
  }

  public int getRoomNum() {
    return this.roomNum; // turn into string
  }

  public boolean isAvailable(int day) {
    return availability[day]; //REVISE LOOPS FOR DAY TO BE DAY - 1, RIGHT NOW IT'S CONSIDERING DAY 1 = INDEX 0
  }
  
  public void printRoomInformation(){
    //information about selected room (name, cost per night, and availabilty across the whole month) just iterate through rooms and print out the information of the selected room
    
    System.out.println("Room Name: " + this.roomNum);
    System.out.println("Cost per Night: " + this.hotel.getRoomPrice());
    System.out.println("Availability: " + this.nDaysAvailable + " days"); //# of days available or print the days available?
    
  }

  public void roomAvailability(int date, boolean availability){
      this.availability[date] = availability;

      if(availability == true){
        this.nDaysAvailable++;
      }
      if(availability == false){
        this.nDaysAvailable--;
      }
  }
  
  public int getNDaysAvailable(){
    return this.nDaysAvailable;
  }

  public void addReservation(Reservation reservation) {
    roomReservations.add(reservation);
  }

  public ArrayList<Reservation> getReservations() {
    return roomReservations;
  }

  /*
  public void addBookingID(String bookingID){ //whats this for
    bookingIDs.add(bookingID);
  } 

  public void removeBookingID(String bookingID) { //whats this for
      bookingIDs.remove(bookingID);
  }

  public ArrayList<String> getBookingIDs() {
    return bookingIDs;
  }
  */

  //revise
  /*
  public void removeReservationByID(String bookingID) {
    for (Reservation reservation : roomReservations) {
          if (getBookingIDs().equals(bookingID)) {
              roomReservations.remove(reservation);
          }
      }
  }
  */

}
