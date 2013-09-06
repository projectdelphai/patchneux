import java.io.*;
import java.util.*;

public class Navigation {
  public HashMap getRoomData(List coordinate) {
    File f = new File("room"+coordinate.get(0)+","+coordinate.get(1));
    HashMap roomData = new HashMap();
    roomData.put("Coordinate", coordinate);
    return roomData;
  }
}
