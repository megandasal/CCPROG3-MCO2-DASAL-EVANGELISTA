import java.util.Scanner;
import java.util.ArrayList;

public class Reservation{
    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private int numNights;

    public Reservation(String guestName, int checkInDate, int checkOutDate){
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void Reserve(String guestName, int checkInDate, int checkOutDate){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name to book reservation: ");
        guestName = scanner.nextLine();

        System.out.println("Enter day of reservation: ");
        checkInDate = scanner.nextInt();



    }

    public void calculateNumNights(){
        // calculate number of nights
    }

    public String getGuestName(){
        return guestName;
    }

    public void setGuestName(String guestName){
        this.guestName = guestName;
    }

    public int getCheckInDate(){
        return checkInDate;
    }

    public void setCheckInDate(int checkInDate){
        this.checkInDate = checkInDate;
    }

    public int getCheckOutDate(){
        return checkOutDate;
    }

    public void setCheckOutDate(int checkOutDate){
        this.checkOutDate = checkOutDate;
    }

    public int getNumNights(){
        return numNights;
    }

    public void setNumNights(int numNights){
        this.numNights = numNights;
    }

}
