import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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

  public void updateEstimateEarnings(int nDays){

    this.estimateEarnings = nDays * this.roomPrice;
    
  }

  public double getEstimateEarnings(){
    return this.estimateEarnings;
  }

  public void setHotelName(String newHotelName) {
    this.hotelName = newHotelName;
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
      if (confirm == 'Y' || confirm == 'y'){
        for (int i = 0; i < newRooms; i++) {
          nRooms++;
          rooms.add(new Room(nRooms, this)); 
        }
        System.out.println(newRooms + " rooms have been added to the hotel.");
      }   
      else if (confirm == 'N' || confirm == 'n'){
        System.out.println("Modification cancelled.");
      }
      else {
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

    return availableRooms; //returns the list of rooms available
  }

  public int totalReservations(){
    return allReservations.size();
  }

  public String generateBookingID(){
    int length = 4; // length of randomly generated integer
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length); // for string concatenation

    for (int i = 0; i < length; i++) {
        sb.append(random.nextInt(10)); // append a random digit (0-9) to the string
    }

    String randomInt = sb.toString();
    return randomInt;
  }

  public boolean bookRoom() {

    boolean bookingIsSuccessful = false;
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter name to book reservation: ");
    String guestName = scanner.nextLine();

    System.out.print("Enter Check In reservation: "); //NO VALIDATION
    int checkInDate = scanner.nextInt();

    System.out.print("Enter Check Out Date: "); //NO VALIDATION
    int checkOutDate = scanner.nextInt(); 

    scanner.nextLine(); //buffer

    if(checkInDate > checkOutDate){
        System.out.println("Invalid Dates.");
        return bookingIsSuccessful; // shouldnt print hotel is not available
    }                

    ArrayList<Room> availableRooms = this.checkRoomAvailability(checkInDate, checkOutDate); 

    for(Room room : availableRooms){
      System.out.println("- " + room.getRoomNum());
    }

    if(availableRooms.size() == 0){
      System.out.println("There are no rooms available on the given dates.");
      return bookingIsSuccessful; //returns false
    }

    System.out.print("Enter room number to book: ");
    int selectedRoom = scanner.nextInt();
    

    boolean found = false;
      
            for(Room room : availableRooms){
              if(room.getRoomNum() == selectedRoom){
                found = true;
                
                for(int day = checkInDate; day <= checkOutDate; day++){ //MOVE INTO A SEPARATE METHOD
                  room.roomAvailability(day, false);
                }

                Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate,room.getRoomNum(), roomPrice);
                room.addReservation(reservation);
                updateEstimateEarnings(checkOutDate-checkInDate);
                this.printReceipt(room,checkInDate,checkOutDate, guestName);
                String bookingID = generateBookingID() + room.getRoomNum();
                System.out.println("Your booking ID is " + bookingID + ".");
              }
  
              else{
                System.out.println("Invalid");
              }
            }

        if(!found){
          System.out.println("Room not found.");
          return bookingIsSuccessful;
        }// turn into loop so that the user chooses a valid room index
    //update the hotel's estimate earnings per month based on newly made reservation
      //NOTE: ASK FOR HOW TO COMPUTE, DOES IT COUNT DAYS OR THE NIGHTS
    return bookingIsSuccessful;
  } 

  public void setRoomPrice(double roomPrice) {
    this.roomPrice = roomPrice;
  }

  public double getRoomPrice() {
    return roomPrice;
  }

  public void printReceipt(Room room, int checkInDate, int checkOutDate, String guestName){ //move to reservation class + NO LINK TO ROOM YET
    System.out.println(".-----------------------------------------------.");
    // Print the header
    System.out.printf("│          %7s HOTEL RECEIPT                │\n", this.hotelName); //change to hotel name
    // Print the middle border
    System.out.println(".-----------------------------------------------.");

    // Print hotel and guest information
    System.out.printf("│ Guest Name: %-33s │\n", guestName);
    System.out.println("|                                               |");
    System.out.printf("│ Check-in:   %-33s │\n", checkInDate);
    System.out.printf("│ Check-out:  %-33s │\n", checkOutDate);
    System.out.println("|                                               |");
    System.out.printf("│ Price/Night: $%-31.2f │\n", roomPrice);
    System.out.println("|---------------------------                    |");
    System.out.printf("│ \tTotal Price: $%-32.2f │\n", roomPrice*(checkInDate-checkOutDate));

    // Print the bottom border
    System.out.println(".-----------------------------------------------.");
  }

  public int getRoomAvailability(int date, int roomName){

    int roomsAvailable = 0;
    
    for(Room rooms : rooms){

      if(rooms.getRoomNum() == roomName){
        if(rooms.isAvailable(date)){
          roomsAvailable++;
        }
      }
      
    }

    return roomsAvailable;
    
  }

  public int roomsAvailableOnDate(int date){

    int nRoomsAvailable = 0;
    
    for(Room room : rooms){
      if(room.isAvailable(date)){
        nRoomsAvailable++;
      }
    }

    return nRoomsAvailable;
  }

  public void viewRooms(){
    System.out.println("ROOMS");
    for(Room room : rooms){
        System.out.println("- Room " + room.getRoomNum());
    }
  }

  public void printHotelInformation(){
    System.out.println("Hotel Name: " + this.getHotelName()); 
    System.out.println("Number of Rooms: " + this.getNRooms());
    System.out.println("Room Price: " + this.getRoomPrice());
    System.out.println("Estimate Earnings: " + this.getEstimateEarnings());
  }

  /*whats this for idnt remmemerb*/
  public void viewRoomInfo(){
    System.out.print("Room Number: "); 
    int roomNum = scanner.nextInt();
    for(Room room : rooms){
      if(room.getRoomNum() == roomNum){
          room.printRoomInformation(); //should i print room info here or from room class 
      }
    }
  }

  public void allHotelReservations(){ //collects all the reservations in all the rooms
    
    for(Room room : rooms){
      allReservations.addAll(room.getReservations());
    }
  }
  
  public void printAllReservations(){     //prints all reservations made across the hotel
    
    allHotelReservations();
    System.out.println("All Reservations:");
    for(int i = 0; i < allReservations.size();i++){
      System.out.println( (i + 1) + ". [ROOM " + allReservations.get(i).getRoomNum() + "] " + allReservations.get(i).getGuestName());
    }
  }
  
  public void viewReservationInfo(){
    System.out.print("View Detals for Reservation Number: ");
    int reservationNum = scanner.nextInt();
    allReservations.get(reservationNum-1).printReservationInformation();
     //view information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night)
  }
  
  public void changeHotelName(){
        System.out.print("> Enter the hotel name you wish to change: ");
        String oldHotelName = scanner.nextLine();
        if (oldHotelName.isEmpty()) {
            oldHotelName = scanner.nextLine();
        }
        if (oldHotelName.equals(getHotelName())) {
            System.out.print("> Enter the new hotel name: ");
            String newHotelName = scanner.nextLine();

            System.out.print("\nWould you like to proceed with this modification? [Y/N]: ");
            char confirm = scanner.next().charAt(0);
            if (confirm == 'Y' || confirm == 'y') {
                setHotelName(newHotelName);
                System.out.println("Hotel " + oldHotelName + " was successfully changed to " + newHotelName + "!");
            }
            else if (confirm == 'N' || confirm == 'n') {
                System.out.println("Modification cancelled.");
            }
            else {
                System.out.println("Invalid input.");
            }
        }
        else {
            System.out.println("Hotel not found.");
        }
    }

  public void removeRooms() {
    boolean roomRemoved = false;

    System.out.print("\n> Enter the room number to remove: ");
    int roomNum = scanner.nextInt();

    Iterator<Room> iterator = rooms.iterator();

    while (iterator.hasNext()) {
        Room room = iterator.next();
        if (room.getRoomNum() == roomNum && room.getNDaysAvailable() == 31) {
          System.out.print("Would you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);
          if (confirm == 'Y' || confirm == 'y'){
            iterator.remove();  // safely remove elements
            roomRemoved = true;
          }   
        }
    }
    if (roomRemoved) {
        System.out.println("\nRoom " + roomNum + " has been removed.");
    } else {
        System.out.println("\nUnable to remove room.");
    }
  }

  public void updateRoomPrice(){
    for (Room room : rooms){
      if (room.getNDaysAvailable() == 31){
        System.out.println("The current base price for a room is: " + this.roomPrice);
        System.out.print("\n> Enter the new price for a room: ");

        double newPrice = scanner.nextDouble();
        if (newPrice >= 100.00){
          System.out.print("Would you like to proceed with this modification? [Y/N]: ");
          char confirm = scanner.next().charAt(0);
          if (confirm == 'Y' || confirm == 'y'){
            System.out.println("Successfully updated the room price to: " + newPrice);
            setRoomPrice(newPrice);
            break;
          }
          else if (confirm == 'N' || confirm == 'n'){
            System.out.println("Modification cancelled.");
          }
          else {
            System.out.println("Invalid input.");
          }
          
        }
        else {
          System.out.println("Invalid room price. Please enter a value greater than 100.00.");
        }
      }
      else {
        System.out.println("Unable to update room price due to currently active reservations in this hotel.");
        break;
    }
    
    }
  }
  
  public void removeReservation(){
    /* notes for meg:
      1. prompt user to enter reservation number
      2. make new arraylist ????????? for booking IDs para macompare against a reservation if valid siya
      3. ilalagay ata yung booking id sa reservation class
      */
  }

}
  
  // public boolean removeReservation(){
  //   //Scanner scanner = new Scanner(System.in);
  //   boolean successfulRemoval = false;

  //   System.out.println("You have chosen to remove a reservation.");
  //   System.out.println("Please select a room to remove a reservation from: ");
  //   //viewRooms();

  //   /*

  //   [ ] 1. print reserved rooms
  //   [ ] 2. ask user to select a room
    
  //   */

  //   for(Room room : rooms){
  //     // change condition to accommodate reservation ID
  //     if (room.getRoomNum() == roomNum){
  //         if (room.getN)
  //     }
      
  //     else {
  //         System.out.prinlt("Room not found.");
  //     }

  //     }
    
  //   System.out.print("\n> Enter room number: ");
  //   int roomNum = scanner.nextInt();

    
    
  //   return successfulRemoval;
  // }
