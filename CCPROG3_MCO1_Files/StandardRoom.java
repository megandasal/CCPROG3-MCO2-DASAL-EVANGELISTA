public class StandardRoom extends Room {

  private static final double STANDARD_MULTIPLIER = 1.0;

  public StandardRoom(int roomCount, double baseRate) {
    super(roomCount, baseRate * STANDARD_MULTIPLIER, "Standard");
    this.roomPrice = baseRate * STANDARD_MULTIPLIER;
    this.multiplier = STANDARD_MULTIPLIER;
  }

}
