public class DeluxeRoom extends Room {

  public DeluxeRoom(int roomCount, double baseRate){
    super(roomCount, baseRate + (baseRate*.20), "Deluxe");
  }
}
