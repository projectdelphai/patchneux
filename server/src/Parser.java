import java.util.*;

public class Parser {
  public String intro(String user_name) {
    Navigation nav = new Navigation();
    HashMap roomData = nav.getRoomData("./data/current");
    String intro = "Welcome to "+roomData.get("name") +", "+ user_name + ". Your neux looks like a hexagon. Ahead is "+ roomData.get("ahead") + ". Behind you is "+roomData.get("behind") +". To your right is " +roomData.get("right") + " and to your left is " + roomData.get("left") + ". What would you like to do?";
    return intro;
  }

  public String parse(String client_message) {
    String response = client_message;
    if (client_message.startsWith("g")) {
      Navigation nav = new Navigation();
      response = nav.move(client_message);
    } else {
      return response;
    }
    return response;
  }
}
