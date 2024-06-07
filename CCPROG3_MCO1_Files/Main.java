/*
SPECIFICATIONS CHECKLIST:

  [X] - FOR NOT YET IMPLEMENTED
  [_] - FOR ONGOING
  [/] - FOR IMPLEMENTED
  
  1. Create Hotel
  
    [/] Initialize New Hotel - Hotel Count must start at 0 and name must be UNIQUE.
    [_] Create Rooms - 0...50 rooms, each room has a unique name. 
    [X] Set Room Base Price to default price of 1,299.0 (this will be set in Room class constructor) (note: this is effective for all rooms in the hotel)
    [X] Reserve a Room 
        a. BASIC INFORMATION: guest name, check-in date, check-out date, and link too room information.    
        b. PRICE: total price and breakdown of cost per night
    
  2. View Hotel
  
    [X] HIGH LEVEL INFORMATION
        [X] hotel name
        [X] total number of rooms
        [X] estimate earnings per month (i.e. sum of total price across all reservations)
        
    [X] LOW LEVEL INFORMATION
        [X] number of available and booked rooms for a selected date
        [X] information about selected room (name, cost per night, and availabilty across the whole month)
        [X] information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night) (note: this just                  displays the same information as the one in the 'reservation ticket")
        
  3. Manage Hotel - User must be prompted to confirm modification before it is made or else it will be discarded
        [X] Change name of hotel
        [X] Add room(s)
        [X] Remove room(s)
            note: can only remove rooms that have active reservations
        [X] Update base price for rooms 
            - can only update room price if there are no reservations
            - new price must be >= 100.0
        [X] Remove reservation
        [X] Remove hotel
            
  
  4. Simulate Booking
      [X] Select hotel and specify information
      [X] Select room 
        note: just display a list of the rooms available and user can select from those
      [X] Validation of Check in and Check out dates
      [X] Update status of room

  TASKS:
   
    [ ] 

*/

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static int hotelCount = 0;

    private static void printMenu() {
        System.out.println("\n.------------------------------------------------------.");
        System.out.println("|\t\t\t\t\t   MAIN MENU  \t\t\t\t\t   |");
        System.out.println("|\t\t\t\tcurrent hotel count: " + hotelCount + "\t\t\t\t   |"); // WILL REMOVE, THIS IS JUST
                                                                                            // FOR CHECKING
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t   |");
        System.out.println("|\t [Create Hotel]\t\t\t\t\t\t[View Hotel]   |");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t   |");
        System.out.println("|\t [Manage Hotel]  \t\t      [Simulate Booking]   |");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t   |");
        System.out.println("'------------------------------------------------------'");
    }

    private static boolean isUniqueName(ArrayList<Hotel> hotelList, String hotelName) {

        for (Hotel hotel : hotelList) { // iterates through hotelList to check if hotelName already exists
            if (hotel.getHotelName().equals(hotelName)) { // in the case that hotel name already exists
                System.out.println("This hotel name is already taken.");
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Hotel> hotelList = new ArrayList<Hotel>();

        while (true) {

            printMenu();

            System.out.print("> Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "Create Hotel":

                    System.out.print("Hotel Name: ");
                    String hotelName = scanner.nextLine();

                    if (isUniqueName(hotelList, hotelName)) {
                        Hotel Hotel = new Hotel(hotelName);
                        hotelList.add(Hotel);
                        hotelCount++;
                        System.out.println(hotelName + " was created!");

                        // add code for room creation here (note: MIN OF 1 ROOM AND MAX OF 50 ROOMS)
                        System.out.print("Enter number of rooms for this hotel: ");
                        int nRooms = scanner.nextInt();
                        if (nRooms >= 1 && nRooms <= 50) {
                            Hotel.setNRooms(nRooms);
                        } else {
                            System.out.println("Invalid number of rooms. Please enter a number between 1 and 50.");
                        }

                        System.out.println("Number of rooms: " + Hotel.getNRooms());

                    } else
                        System.out.println("This hotel name is already taken."); // loop asking for hotel name
                    // revise so that it only repeats from asking for a new hotel name rather than
                    // going back to menu

                    break;
                case "View Hotel":
                    // view hotel

                    for (int i = 0; i < hotelCount; i++) {
                        System.out.println(i + 1 + ". " + hotelList.get(i).getHotelName());
                    }

                    System.out.println("Which Hotel do you wish to view?");
                    String name = scanner.nextLine();

                    boolean found = false;

                    for (Hotel Hotel : hotelList) {
                        if (Hotel.getHotelName().equals(name)) {
                            System.out.println("Hotel Name: " + Hotel.getHotelName());
                            System.out.println("Number of Rooms: " + Hotel.getNRooms());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Hotel not found");
                    }

                    // will delete
                    break;
                case "Manage Hotel":
                    // manage hotel
                    System.out.println("Manage Hotel has been chosen"); // will delete
                    break;
                case "Simulate Booking":
                    // simulate hotel
                    System.out.println("Simulate Booking has been chosen"); // will delete
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }

            choice = ""; // repeats menu
        }

    }
}
