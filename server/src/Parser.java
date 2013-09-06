import java.util.*;

public class Parser {
  public String intro(String user_name) {
    List coordinate = new ArrayList();
    coordinate.add(0);
    coordinate.add(0);
    Navigation nav = new Navigation();
    HashMap roomData = nav.getRoomData(coordinate);
    System.out.println(roomData);

    String intro = "Welcome to your neux, "+ user_name + ". Your neux looks like a hexagon. Ahead is the metro which is currently closed. Behind you is the exit. To your right is a blank wall and to your left is a blank wall. Your four diagonal portals are unassigned. What would you like to do?";
    return intro;
  }
}
