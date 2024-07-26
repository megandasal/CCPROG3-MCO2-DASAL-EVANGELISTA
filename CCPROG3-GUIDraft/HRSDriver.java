import java.util.ArrayList;

public class HRSDriver {
    public static void main(String args[]) {
        ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
        IntWrapper hotelCount = new IntWrapper(0);
        HotelReservationGUI gui = new HotelReservationGUI(hotelList, hotelCount);
        HRSController controller = new HRSController(gui, hotelList, hotelCount);
    }
}
