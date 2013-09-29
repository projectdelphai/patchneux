import java.util.*;
import java.io.*;

public class Parser {
  public String intro(String user_name) {
    RoomNavigation nav = new RoomNavigation();
    HashMap roomData = nav.getRoomData("./data/current");
    String intro = new RoomNavigation().currentRoomIntro();
    return intro;
  }

  public String parse(DataInputStream in, DataOutputStream out, String clientMessage) {
    String response = clientMessage;
    RoomNavigation nav = new RoomNavigation();
    if (clientMessage.startsWith("g")) {
      clientMessage = clientMessage.substring(1);
      List options = Arrays.asList("right", "left", "ahead", "behind");
      if (options.contains(clientMessage)) {
        response = nav.move(clientMessage);
      } else {
        response = "That is an invalid option";
      }
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
