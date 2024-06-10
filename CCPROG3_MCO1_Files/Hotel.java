import java.util.Scanner;
import java.util.ArrayList;

public class Hotel {

    private String hotelName;
    private int nRooms;
    Scanner scanner = new Scanner(System.in);
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private double roomPrice;
    private double estimateEarnings;

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
            for (int i = 0; i < newRooms; i++) {
                nRooms++;
                rooms.add(new Room(nRooms, this));
            }
        } else {
            System.out.println("Invalid.");
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

    public void bookRoom(Room room, int checkInDate, int checkOutDate) {
        for(int day = checkInDate; day <= checkOutDate; day++){
            room.roomAvailability(day, false);
        }


        updateEstimateEarnings(checkOutDate-checkInDate);//update the hotel's estimate earnings per month based on newly made reservation
        //NOTE: ASK FOR HOW TO COMPUTE, DOES IT COUNT DAYS OR THE NIGHTS
    } //move to reservation class

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void printReceipt(Room room, int checkInDate, int checkOutDate, String guestName){ //move to reservation class + NO LINK TO ROOM YET
        System.out.println(".-----------------------------------------------.");
        // Print the header
        System.out.printf("│          %7s HOTEL RECEIPT                │\n", hotelName); //change to hotel name
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
        System.out.printf("│ \tTotal Price: $%-32.2f │\n", roomPrice*(checkInDate-checkOutDate+1));

        // Print the bottom border
        System.out.println(".-----------------------------------------------.");
    }

    public int getRoomAvailability(int date, int roomName){

        int roomsAvaialble = 0;

        for(Room rooms : rooms){

            if(rooms.getRoomNum() == roomName){
                if(rooms.isAvailable(date)){
                    roomsAvaialble++;
                }
            }

        }

        return roomsAvaialble;

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

    public void viewRoomInfo(){
        System.out.print("Room Number: ");
        int roomNum = scanner.nextInt();
        for(Room room : rooms){
            if(room.getRoomNum() == roomNum){
                room.printRoomInformation(); //should i print room info here or from room class //answr
            }
        }
    }

}
