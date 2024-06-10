/*
SPECIFICATIONS CHECKLIST:

  [X] - FOR NOT YET IMPLEMENTED
  [_] - FOR ONGOING
  [/] - FOR IMPLEMENTED
  
  1. Create Hotel
  
    [/] Initialize New Hotel - Hotel Count must start at 0 and name must be UNIQUE.
    [/] Create Rooms - 0...50 rooms, each room has a unique name. 
    [/] Set Room Base Price to default price of 1,299.0 (this will be set in Room class constructor) (note: this is effective for all rooms in the hotel)
    [/] Reserve a Room 
        a. BASIC INFORMATION: guest name, check-in date, check-out date, and link too room information.   
            - reminder: check logic for reserving room from days n to n
        b. PRICE: total price and breakdown of cost per night
    
  2. View Hotel
  
    [/] HIGH LEVEL INFORMATION
        [/] hotel name
        [/] total number of rooms
        [FOR CHECKING] estimate earnings per month (i.e. sum of total price across all reservations)
        
    [X] LOW LEVEL INFORMATION
        [/] number of available and booked rooms for a selected date
        [FOR CHECKING] information about selected room (name, cost per night, and availabilty across the whole month)
        note: REVISE COMPUTATION 
        [_] information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night) (note: this just                  displays the same information as the one in the 'reservation ticket")
        
  3. Manage Hotel - User must be prompted to confirm modification before it is made or else it will be discarded
        [X] Change name of hotel
        [X] Add room(s)
        [X] Remove room(s)
            note: can only remove rooms that do not have active reservations 
        [X] Update base price for rooms 
            - can only update room price if there are no reservations
            - new price must be >= 100.0
        [X] Remove reservation
        [X] Remove hotel
            
  
  4. Simulate Booking
      [_] Select hotel and specify information (note: specify information hasnt been incorporated yet)
      [/] Select room 
        note: just display a list of the rooms available and user can select from those
      [X] Validation of Check in and Check out dates
      [/] Update status of room

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

    private static void createHotel(ArrayList<Hotel> hotelList){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Hotel Name: ");
        String hotelName = scanner.nextLine();

        if (isUniqueName(hotelList, hotelName)) {
            Hotel hotel = new Hotel(hotelName);
            hotelList.add(hotel);
            hotelCount++;
            System.out.println(hotelName + " was created!");

            // add code for room creation here (note: MIN OF 1 ROOM AND MAX OF 50 ROOMS)
            hotel.addRooms();

            System.out.println("Number of rooms: " + hotel.getNRooms());

            //print rooms made

        } else
            System.out.println("This hotel name is already taken."); // loop asking for hotel name
        // revise so that it only repeats from asking for a new hotel name rather than
        // going back to menu
    }

    private static void viewHotel(ArrayList<Hotel> hotelList){

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < hotelCount; i++) {
            System.out.println(i + 1 + ". " + hotelList.get(i).getHotelName());
        }

        System.out.print("Which Hotel do you wish to view? ");
        String name = scanner.nextLine();

        boolean found = false;

        for (Hotel hotel : hotelList) {
            if (hotel.getHotelName().equals(name)) {
                /*DETAILS CAN BE PRINTED IN HOTEL CLASS*/
                System.out.println("Hotel Name: " + hotel.getHotelName());
                System.out.println("Number of Rooms: " + hotel.getNRooms());
                System.out.println("Room Price: " + hotel.getRoomPrice());
                System.out.println("Estimate Earnings: " + hotel.getEstimateEarnings());

                /*FIX THIS PART, CLEAN UP*/
                System.out.println(".-----------------------------------------.");
                System.out.println("|\t\t\t\tSELECT\t\t\t\t|");
                System.out.println("|\t [DATE]     [ROOM]     [RESERVATION]\t |");
                System.out.println(".-----------------------------------------.");

                System.out.println("Choice: ");
                String choice = scanner.nextLine();

                switch (choice){
                    case "DATE":
                        System.out.print("Date: ");
                        int date = scanner.nextInt();
                        System.out.print("Number of rooms available on " + date + ": " + hotel.roomsAvailableOnDate(date));
                        break;
                    case "ROOM":
                        hotel.viewRooms();
                        hotel.viewRoomInfo();
                        //information about selected room (name, cost per night, and availabilty across the whole month) just iterate through rooms and print out the information of the selected room
                        break;

                    case "RESERVATION":
                        //information about selected reservation (guest information, room information, check-in and                  check-out dates, total price of booking and breakdown of cost per night)
                        break;
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Hotel not found");
        }
    }

    private static void simulateBooking(ArrayList<Hotel> hotelList){
        /*
         * [/] 1. ask for check in and check out dates
         * [/] 2. print which rooms are available on those given days
         * [/] 3. user chooses a room from the list
         * [/] 4. reservation is made and room availability is updated
         * [/] 5. reservation details are printed
         */

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please select a hotel to make a reservation. Available hotels:");
        for (int i = 0; i < hotelList.size(); i++) {
            System.out.println((i + 1) + ". " + hotelList.get(i).getHotelName());
        }

        System.out.print("In which hotel do you wish to book a room? ");
        String hotelChoice = scanner.nextLine();

        System.out.print("Input Name: "); //help idk fix the prompt
        String guestName = scanner.nextLine(); // call reservation class setter

        boolean hotelFound = false;
        for(Hotel hotel : hotelList){
            if(hotel.getHotelName().equals(hotelChoice)){
                System.out.println(
                        "You have selected the " + hotel.getHotelName() + " Hotel!\n");

                System.out.print("> Check In Date: ");
                int checkInDay = scanner.nextInt(); // call reservation/hotel class setter

                System.out.print("> Check Out Date: ");
                int checkOutDay = scanner.nextInt(); // call reservation/hotel class setter

                if(checkInDay > checkOutDay){
                    System.out.println("Invalid Dates.");
                    break; // shouldnt print hotel is not available
                }

                ArrayList<Room> availableRooms = hotel.checkRoomAvailability(checkInDay, checkOutDay);

                for(int i = 0; i < availableRooms.size(); i++){
                    System.out.println(i + 1 + ". " + availableRooms.get(i).getRoomNum());
                }

                if(availableRooms.size() > 0){
                    System.out.print("Choose a room to book: ");
                    int roomIndex = scanner.nextInt();
                    scanner.nextLine();


                    if (roomIndex >= 1 && roomIndex <= availableRooms.size()){

                        Room selectedRoom = availableRooms.get(roomIndex - 1);

                        hotel.bookRoom(selectedRoom,checkInDay,checkOutDay);

                        hotel.printReceipt(selectedRoom,checkInDay,checkOutDay, guestName);

                    }
                    else
                        System.out.println("Invalid"); // turn into loop so that the user chooses a valid room index
                }

                else{
                    System.out.println("\nThere are not rooms available on the given dates.");
                }

                hotelFound = true;
                break;
            }
        }

        if(!hotelFound){
            System.out.println("Hotel not found.");
        }
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

                    createHotel(hotelList);

                    break;
                case "View Hotel":
                    viewHotel(hotelList);
                    break;
                case "Manage Hotel":
                    // manage hotel
                    System.out.println("Manage Hotel has been chosen"); // will delete

                    break;
                case "Simulate Booking":
                    simulateBooking(hotelList);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }

            choice = ""; // repeats menu
        }

    }
}
