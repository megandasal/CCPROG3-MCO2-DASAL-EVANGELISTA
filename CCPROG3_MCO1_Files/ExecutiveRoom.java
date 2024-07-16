public class ExecutiveRoom extends Room {

  public ExecutiveRoom(int roomCount, double baseRate){
    super(roomCount, baseRate + (baseRate*.35), "Executive");
  }

}
