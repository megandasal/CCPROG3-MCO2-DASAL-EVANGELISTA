import java.util.Arrays;
import java.util.ArrayList;

public abstract class Room {

    String roomType;
    String roomName; // what do i make this
    private int nDaysAvailable;
    private boolean[] availability; // represents the availability of a room for each day
    private ArrayList<Reservation> roomReservations = new ArrayList<Reservation>(); // keeps track of all the
                                                                                    // reservations in a room
    protected double roomPrice;
    protected double multiplier; // add multiplier

    /**
     * Constructs a Room object.
     * 
     * @param roomCount number of rooms in a hotel
     * @param roomPrice - the price of a room upon instantiation
     */
    public Room(int roomCount, double roomPrice, String roomType) {
        this.roomType = roomType;
        roomName = Integer.toString(100 + roomCount) + roomType.substring(0, 1); // temp
        this.availability = new boolean[31];
        Arrays.fill(availability, true);
        nDaysAvailable = availability.length;
    }

    /**
     * Displays a room's name, cost per night, and its availability across the whole
     * month.
     */
    public void printRoomInformation() {
        // print room info
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                   ROOM INFORMATION                   |");
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                                                      |");
        System.out.format("|   Room Name: %-39s |\n", this.roomName);
        System.out.format("|   Room Type: %-39s |\n", this.roomType); // incorporate encapsulation..?
        System.out.format("|   Cost per Night: %-34s |\n", this.roomPrice);
        System.out.format("|   Number of Days Available: %-24s |\n", this.nDaysAvailable);
        System.out.println("|                                                      |");
        // print availability of the room for each day
        System.out.println(".------------------------------------------------------.");
        System.out.println("|                     AVAILABILITY                     |");
        System.out.println(".------------------------------------------------------.");
        System.out.println("|            Day            |          Status          |");
        System.out.println(".------------------------------------------------------.");
        // iterate over each day's availability
        for (int day = 0; day < this.availability.length; day++) {
            String dayStr = String.format("%2d", day + 1);
            String reserved = availability[day] ? "Available" : "Not Available";
            // handle formatting for day and status column to center text
            if (availability[day] == true) {
                if (day < 9) {
                    System.out.format("|            %-2s             |         %-13s    |\n", dayStr, reserved);
                } else {
                    System.out.format("|             %-2s            |         %-13s    |\n", dayStr, reserved);
                }
            } else {
                if (day < 9) {
                    System.out.format("|            %-2s             |       %-13s      |\n", dayStr, reserved);
                } else {
                    System.out.format("|             %-2s            |       %-13s      |\n", dayStr, reserved);
                }
            }
        }
        System.out.println(".------------------------------------------------------.");

    }

    /**
     * Gets the availability of a room on a specified date.
     * 
     * @param day the day to check the availability of a room
     * @return an element of a boolean array pertaining to the availability of a room given a date
     */
    public boolean isAvailable(int day) {
        return availability[day - 1];
    }

    /**
     * Checks if a guest has checked in or checked out on a specified date
     * 
     * @param date     the date to check if a guest has checked in/out of a room
     * @param starting determines if a guest has checked in on a given date
     * @return true if a guest has checked in or checked out on a date
     */
    public boolean isReservationStartingEndingOn(int date, boolean starting) {
        if (starting == true) { // checks if there is a check in on a specified date
            for (Reservation reservation : roomReservations) {
                if (reservation.getCheckInDate() == date) {
                    return true; // return true if found
                }
            }
        }
        if (starting == false) { // checks if there is a check out on a specified date
            for (Reservation reservation : roomReservations) {
                if (reservation.getCheckOutDate() == date) {
                    return true;
                }
            }
        }
        return false; // return false otherwise
    }

    /**
     * Updates the availability of a room in a boolean array.
     */
    public void updateNDaysAvailable() {

        int ctr = 0;

        for (int i = 0; i < 31; i++) {
            if (this.availability[i] == true) // iterate through the boolean array and count the number of days that are
                                              // available
                ctr++;
        }

        this.nDaysAvailable = ctr;
    }

    /**
     * Adds a reservation to the array list of room reservations.
     * 
     * @param reservation a Reservation object containing the guest's name,
     *                    check-in and check-out dates, room number, room price,
     *                    total room price, and
     *                    unique booking ID
     */
    public void addReservation(Reservation reservation) {
        roomReservations.add(reservation);
    }

    /**
     * Removes a reservation from the array list of reservations at a given index.
     * 
     * @param i the index of the reservation to remove
     */
    public void removeReservation(int i) {
        roomReservations.remove(i);
    }

    /**
     * Retrieves the list of reservations made across all rooms.
     * 
     * @return an array list containing the reservations from all rooms
     */
    public ArrayList<Reservation> getReservations() {
        return roomReservations;
    }

    /**
     * Retrieves a room number in a hotel.
     * 
     * @return room number
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Retrieves the number of days a room is reserved.
     * 
     * @return number of days a room is booked
     */
    public int getNReservations() {
        return this.roomReservations.size();
    }

    /**
     * Retrieves the price of a room.
     * @return the price of a room
     */
    public double getRoomPrice() {
        return this.roomPrice;
    }

    /**
     * Retrieves the room type.
     * @return the room type
     */
    public String getRoomType() {
        return this.roomType;
    }

    /**
     * Sets the price of a room.
     * @param baseRate the base rate of a room
     */
    public void setRoomPrice(double baseRate) {
        this.roomPrice = baseRate * this.multiplier;
    }

    /**
     * Sets the availability of a room on a specified date and updates the number of
     * days a room is available.
     * 
     * @param date         the date to set the availability of the room to
     * @param availability the availability of the room
     */
    public void setRoomAvailability(int date, boolean availability) {
        this.availability[date - 1] = availability;
    }

    private boolean isAvailable;

    /**
     * Retrieves a room's information.
     * @return a string containing a room's name, type, price, and availability status
     */
    public String getRoomInformation() {
        return "Room Name: " + roomName + ", Room Type: " + roomType + ", Room Price: " + roomPrice + ", Availability: " + (isAvailable ? "Available" : "Not Available");
    }

    /**
     * Retrieves a room's information for viewing through the GUI.
     * @return a string containing a room's name, type, price, and number of days available
     */
    public String getRoomInfoForViewing() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nRoom Name: " + this.roomName + "\nRoom Type: " + this.roomType + "\nCost per Night: " + this.roomPrice + "\nNumber of Days Available: " + this.nDaysAvailable);
        return sb.toString();
    }

    // will be used for viewing room info - view hotel -> view room
    /**
     * Retrieves the availability of a room for viewing through the GUI.
     * @return a string containing the availability of a room for each day
     */
    public String getRoomAvailabilityForViewing() {
        StringBuilder sb = new StringBuilder();
        sb.append("                    .------------------------------------------------------.\n");
        sb.append("                   |                 Day            |              Status           |\n");
        sb.append("                    .------------------------------------------------------.\n");
    
        boolean[] availability = this.availability;
    
        for (int day = 0; day < 31; day++) {
            String dayStr = String.format("%2d", day + 1);
            String status = availability[day] ? "Available" : "Reserved";
            
            if (day < 9 && status.equals("Available")) {
                sb.append(String.format("                    |                %-2s               |            %-13s     |\n", dayStr, status));
            } else if (day < 9 && status.equals("Reserved")) {
                sb.append(String.format("                    |                %-2s               |            %-13s   |\n", dayStr, status));
            }
            else if (day >= 10 && status.equals("Available")) {
                sb.append(String.format("                    |                 %-2s             |            %-13s     |\n", dayStr, status));
            }
            else {
                sb.append(String.format("                    |                %-2s               |            %-13s   |\n", dayStr, status));
            }
        }
    
        sb.append("                    .------------------------------------------------------.\n");
        return sb.toString();
    }
}
