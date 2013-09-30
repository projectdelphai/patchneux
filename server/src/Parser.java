import java.util.*;
import java.io.*;

public class Parser {
  public String intro(String user_name) {
    String intro = new RoomNavigation().currentRoomIntro();
    return intro;
  }

  public String parse(DataInputStream in, DataOutputStream out, String clientMessage) {
    String response = clientMessage;
    if (clientMessage.startsWith("g")) {
      clientMessage = clientMessage.substring(1);
      List options = Arrays.asList("right", "left", "ahead", "behind");
      if (options.contains(clientMessage)) {
        response = new RoomNavigation().move(clientMessage);
      } else {
        response = "That is an invalid option";
      }
    } else if (clientMessage.startsWith("c")) {
      clientMessage = clientMessage.substring(1);
      if (clientMessage.startsWith("r")) {
        response = new RoomNavigation().createRoom(in, out, clientMessage);
      } else {
        return "Invalid query. Try again";
      }
    } else if (clientMessage.startsWith("aboutme")) {
      response = new Character().aboutMe();
    } else if (clientMessage.startsWith("i")) {
      response = new Item().info(clientMessage.substring(1));
    } else if (clientMessage.startsWith("p")) {
      response = new Item().pickUp(clientMessage.substring(1));
    } else {
      return response;
    }
    return response;
  }
}
