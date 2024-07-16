import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Reservation {
    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private String roomName;
    private double roomPrice;
    private double totalPrice;
    private String bookingID;
    private Room roomLink; // links(gives access to) the reservation to the information of the room it is
                           // in
    private String coupon;

    /**
     * Constructs a Reservation object.
     * 
     * @param guestName    name of guest who made a reservation
     * @param checkInDate  date a guest checked in
     * @param checkOutDate date a guest checked out
     * @param room         Room object associated with the reservation made
     */
    public Reservation(String guestName, int checkInDate, int checkOutDate, Room room) {
        this.roomName = room.getRoomName();
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomPrice = room.getRoomPrice();
        this.totalPrice = this.roomPrice * (checkOutDate - checkInDate);
        this.bookingID = this.generateBookingID();
        this.coupon = "None";
        roomLink = room;
    }

    /**
     * Prints out information about a reservation.
     */
    public void printReservationInformation() {
        System.out.println("\n.-------------------------------.");
        System.out.println("|     * RESERVATION INFO *      |");
        System.out.println("|                               |");
        System.out.format("| Guest Name: %-17s |\n", this.guestName);
        System.out.format("| Booking ID: %-17s |\n", this.bookingID);
        System.out.format("| Room: %-23s |\n", this.roomName);
        System.out.format("| Check In: %-19d |\n", this.checkInDate);
        System.out.format("| Check Out: %-18d |\n", this.checkOutDate);
        System.out.format("| Cost per night: $%-12.2f |\n", this.roomPrice);
        System.out.format("| Discount Code: $%-14.2f |\n", this.coupon);
        System.out.format("| Total Price: $%-15.2f |\n", this.totalPrice);
        System.out.println("|                               |");
        System.out.println(".-------------------------------.");
    }

    /**
     * Generates a random booking ID for a successful reservation.
     * 
     * @return string of 6 random integers concatenated with the given room number
     */
    public String generateBookingID() {
        int length = 6; // length of randomly generated integer
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length); // used for string concatenation

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // append a random digit (0-9) to the string
        }

        String randomInt = sb.toString();
        String bookingID = randomInt + this.roomName; // concatenate the random integer and the room number

        return bookingID;
    }

    /**
     * Prints the contents of a receipt after a guest makes a booking in a hotel.
     * Displays all booking information.
     * 
     * @param hotelName name of hotel
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
        System.out.printf("| Discount Code: %-30s |\n", this.coupon);
        System.out.println("|---------------------------                    |");
        System.out.printf("| Total Price: $%-31.2f |\n", this.totalPrice);

        // Print the bottom border
        System.out.println(".-----------------------------------------------.");
    }

    public void applyDiscount(String discountCode) {
        // 1. check for validity
        // 2. apply discount and reflect changes on reservation receipt

        /*
         * [/] STAY4_GET1 - reservation has 5 days or more, the first day if reservation
         * is free
         * [/] PAYDAY - gives 7% discount to the overall price if reservation covers
         * (but not as checkout), either day 15 or 30
         */

        switch (discountCode) {
            case "I_WORK_HERE":
                this.totalPrice = this.totalPrice * .90;
                System.out.println("Discount code applied!");
                this.coupon = "I_WORK_HERE";
                break;

            case "STAY4_GET1":
                if (this.checkOutDate - this.checkInDate >= 4) { // 5 days or 4 nights
                    this.totalPrice = this.totalPrice - roomPrice;
                    System.out.println("You have 5 days or more to stay. Your first day is free!");
                    this.coupon = "STAY4_GET1";
                    return;
                } else
                    System.out.println("Your reservation doesn't qualify for this discount code.");
                break;
            case "PAYDAY":
                boolean valid = false;
                for (int i = this.checkInDate; i < this.checkOutDate; i++) {
                    if (i == 15 || i == 30) {
                        valid = true;
                    }
                }
                if (valid) {
                    this.totalPrice = this.totalPrice * .93;
                    System.out.println("It's Payday! You have a 7% discount on your total price!");
                    this.coupon = "PAYDAY";
                    return;
                } else {
                    System.out.println("Your reservation doesn't qualify for this discount code.");
                }
                break;
            default:
                System.out.println("This is an invalid discount code.");
                break;
        }

    }

    /**
     * Retrieves the reservation's room number.
     * 
     * @return room number
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Retrieves the name of the guest who made the reservation.
     * 
     * @return guest name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Retrieves the date a guest checked into the hotel.
     * 
     * @return check-in date
     */
    public int getCheckInDate() {
        return checkInDate;
    }

    /**
     * Retrieves the date a guest checked out of the hotel.
     * 
     * @return check-out date
     */
    public int getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Retrieves the total cost of each night a guest stays in a hotel.
     * 
     * @return total cost/price of all nights stayed
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Retrieves the unique booking ID generated for a reservation.
     * 
     * @return booking ID
     */
    public String getBookingID() {
        return bookingID;
    }
}
