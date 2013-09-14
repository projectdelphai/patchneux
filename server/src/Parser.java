import java.util.*;
import java.io.*;

public class Parser {
  public String intro(String user_name) {
    RoomNavigation nav = new RoomNavigation();
    HashMap roomData = nav.getRoomData("./data/current");
    String intro = "Welcome to "+roomData.get("name") +", "+ user_name + ". Your neux looks like a hexagon. Ahead is "+ roomData.get("ahead") + ". Behind you is "+roomData.get("behind") +". To your right is " +roomData.get("right") + " and to your left is " + roomData.get("left") + ". What would you like to do?";
    return intro;
  }

  public String parse(DataInputStream in, DataOutputStream out, String clientMessage) {
    String response = clientMessage;
    RoomNavigation nav = new RoomNavigation();
    if (clientMessage.startsWith("g")) {
      clientMessage = clientMessage.substring(1);
      response = nav.move(clientMessage);
    } else if (clientMessage.startsWith("c")) {
      clientMessage = clientMessage.substring(1);
      if (clientMessage.startsWith("r")) {
        response = nav.createRoom(in, out, clientMessage);
      } else {
        return "Invalid query. Try again";
      }
    } else {
      return response;
    }
    return response;
  }
}
