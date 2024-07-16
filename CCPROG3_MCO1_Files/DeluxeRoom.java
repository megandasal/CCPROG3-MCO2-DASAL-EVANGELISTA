public class DeluxeRoom extends Room {

  private static final double DELUXE_MULTIPLIER = 1.2;
  
  public DeluxeRoom(int roomCount, double baseRate){
    super(roomCount, baseRate * DELUXE_MULTIPLIER, "Deluxe");
    this.roomPrice = baseRate * DELUXE_MULTIPLIER;
    this.multiplier = DELUXE_MULTIPLIER;
  }

}
