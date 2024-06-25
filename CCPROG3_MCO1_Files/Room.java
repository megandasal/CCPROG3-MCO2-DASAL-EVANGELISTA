import java.util.Arrays;
import java.util.ArrayList;

public class Room {

  private int roomNum;
  private Hotel hotel; //gives access to hotel the room is under

  private boolean[] availability; // represents the availability of a room for each day
  private int nDaysAvailable;
  private ArrayList<Reservation> roomReservations = new ArrayList<Reservation>();
  private ArrayList<String> bookingIDs = new ArrayList<>();

  /**
   * Constructs a Room object.
   * @param roomCount number of rooms in a hotel
   * @param hotel Hotel object associated with the room
   */
  public Room(int roomCount, Hotel hotel) {
    roomNum = 100 + roomCount; // should update when a room is deleted
    this.availability = new boolean[31];
    Arrays.fill(availability, true);
    this.hotel = hotel;
    nDaysAvailable = availability.length;
  }

  /**
   * Retrieves a room number in a hotel.
   * @return room number
   */
  public int getRoomNum() {
    return this.roomNum; // turn into string
  }

  /**
   * Gets the availability of a room on a specified date.
   * @param day the day to check the availability of a room
   * @return an element of a boolean array pertaining to the availability of a room
   * given a date
   */
  public boolean isAvailable(int day) {
    return availability[day - 1]; 
  }

  /**
   * Displays a room's name, cost per night, and its availability across the whole month.
   */
  public void printRoomInformation(){
    //information about selected room (name, cost per night, and availabilty across the whole month) just iterate through rooms and print out the information of the selected room
    
    System.out.println("\nRoom Name: " + this.roomNum);
    System.out.println("Cost per Night: " + this.hotel.getRoomPrice());
    System.out.println("Number of Days Available: " + this.nDaysAvailable);
    /*DAYS AVAILABLE*/

    System.out.println("\nAvailability for Room " + this.getRoomNum() + ":");
    for (int day = 0; day < this.availability.length; day++) {
        if (this.availability[day]) {
            System.out.println("Day " + (day + 1) + ": Available");
        } else {
            System.out.println("Day " + (day + 1) + ": Not Available");
        }
    }
    
  } 

  public boolean isReservationStartingEndingOn(int date, boolean starting) {
    if(starting == true){ //checks if there is a check in on a specified date
      for (Reservation reservation : roomReservations) {
          if (reservation.getCheckInDate() == date) {
              return true;
          }
      }
    }
    if(starting == false){ //checks if there is a check out on a specified date
      for (Reservation reservation : roomReservations) {
          if (reservation.getCheckOutDate() == date) {
              return true;
          }
      }
    }
      return false;
  }

  /**
   * Increments the number of days a room is available.
   */
  public void incrementDaysAvailable() {
    nDaysAvailable++;
  }

  /**
   * Decrements the number of days a room is available.
   */
  public void decrementDaysAvailable() {
    nDaysAvailable--;
  }
  
  /**
  * Sets the availability of a room on a specified date and updates the number of days a room is available.
  * @param date the date to set the availability of the room on
  * @param availability the availability of the room
  */
  public void setRoomAvailability(int date, boolean availability){
      this.availability[date - 1] = availability;
  }

  /**
   * Retrieves the number of days a room is not reserved.
   * @return number of days a room is available
   */
  public int getNDaysAvailable(){
    return this.nDaysAvailable;
  }
  
  /**
   * Adds a reservation to array list of room reservations.
   * @param reservation a Reservation object containing the guest's name, 
   * check-in and check-out dates, room number, room price, total room price, and
   * unique booking ID
   */
  
  public void addReservation(Reservation reservation) {
    roomReservations.add(reservation);
  }

  /**
   * Retrieves the list of reservations made across all rooms.
   * @return an array list containing the reservations from all rooms
   */
  public ArrayList<Reservation> getReservations() {
    return roomReservations;
  }

  /**
   * Removes a reservation from the array list of reservations at a given index.
   * @param i the index of the reservation to remove
   */
  public void removeReservation(int i) {
    roomReservations.remove(i);
  }

}
