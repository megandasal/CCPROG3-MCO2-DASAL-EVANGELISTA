import java.util.ArrayList;

public class HRSDriver {
    public static void main(String args[]) {
        ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
        int hotelCount = 0;
        HotelReservationGUI gui = new HotelReservationGUI(hotelList);
        HRSController controller = new HRSController(gui, hotelList, hotelCount);
    }
}
