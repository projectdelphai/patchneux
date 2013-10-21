import java.util.*;
import java.io.*;

public class Parser {
  public String intro(String user_name) {
    String intro = new RoomNavigation().currentRoomIntro();
    return intro;
  }

  public String parse(DataInputStream in, DataOutputStream out, String clientMessage) {
    String response = clientMessage;
    if (clientMessage.startsWith("go")) {
      clientMessage = clientMessage.substring(2);
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
      response = new Item().pickUpItem(clientMessage.substring(1), "profile");
    } else if (clientMessage.startsWith("drop")) {
      response = new Item().dropItem(clientMessage.substring(4), "profile");
    } else if (clientMessage.startsWith("lookaround")) {
      response = new RoomNavigation().currentRoomIntro();
    } else if (clientMessage.startsWith("teleport")) {
      response = new RoomNavigation().teleportToRoom(clientMessage.substring(8));
    } else if (clientMessage.startsWith("greet")) {
      NPC npc = new NPC(clientMessage.substring(5));
      response = npc.talk("greet");
    } else if (clientMessage.startsWith("give")) {
      String itemName = clientMessage.substring(4).split("to")[0];
      String npcName = clientMessage.split("to")[1];
      NPC npc = new NPC(npcName);
      response = npc.giveNPC(itemName);
    } else {
      return response;
    }
    return response;
  }
}
