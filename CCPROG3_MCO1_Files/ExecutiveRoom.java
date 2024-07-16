public class ExecutiveRoom extends Room {

  private static final double EXECUTIVE_MULTIPLIER = 1.35;

  public ExecutiveRoom(int roomCount, double baseRate){
    super(roomCount, baseRate * EXECUTIVE_MULTIPLIER, "Executive");
    this.roomPrice = baseRate * EXECUTIVE_MULTIPLIER;
    this.multiplier = EXECUTIVE_MULTIPLIER;
  }

}
