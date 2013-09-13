import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class Navigation {
  public HashMap getRoomData(String fileName) {
    String line = null;
    String json = line;
    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null) {
        json = line;
      }
      bufferedReader.close();
    } catch(FileNotFoundException ex) {
      System.out.println( "Unable to open file '" + fileName + "'");
    } catch(IOException e) {
      e.printStackTrace();
    }
    Gson gson = new Gson();
    HashMap<String,Object> roomData = new HashMap<String, Object>();
    roomData=(HashMap<String,Object>) gson.fromJson(json, roomData.getClass());
    return roomData;
  }

  public String currentRoomIntro() {
    HashMap roomData = getRoomData("./data/current");
    String intro = "Welcome to "+roomData.get("name") +". Ahead is "+ roomData.get("ahead") + ". Behind you is "+roomData.get("behind") +". To your right is " +roomData.get("right") + " and to your left is " + roomData.get("left") + ". What would you like to do?";
    return intro;
  }

  public Integer increaseIndex(Integer index) {
    int newIndex = new Integer(index+1);
    if (index == 3) {
      newIndex = 0;
    }
    return newIndex;
  }

  public List orientList(List rawList, Integer index) {
    List newList = new ArrayList();
    newList.add(rawList.get(index));
    index = increaseIndex(index);
    newList.add(rawList.get(index));
    index = increaseIndex(index);
    newList.add(rawList.get(index));
    index = increaseIndex(index);
    newList.add(rawList.get(index));
    index = increaseIndex(index);
    return newList;
  }

  public String moveCoordinate(String direction, String oldCoordinate) {
    String[] parts = oldCoordinate.split(",");
    int x = Integer.parseInt(parts[0]);
    int y = Integer.parseInt(parts[1]);
    if (direction == "N") {
      y = y + 1;
    } else if (direction == "S") {
      y = y - 1;
    } else if (direction == "W") {
      x = x - 1;
    } else if (direction == "E") {
      x = x + 1;
    }
    String newCoordinate = x+","+y;
    return newCoordinate;
  }

  public String getAdjacentRoom(HashMap newRoomData, Object cardinalPoint) {
    cardinalPoint = cardinalPoint.toString();
    String response = "";
    if (cardinalPoint == "N") {
      response = newRoomData.get("ahead").toString();
    } else if (cardinalPoint == "S") {
      response = newRoomData.get("behind").toString();
    } else if (cardinalPoint == "W") {
      response = newRoomData.get("left").toString();
    } else if (cardinalPoint == "E") {
      response = newRoomData.get("right").toString();
    }
    return response;
  }

  public Boolean blankWall(String parsedClientMessage, HashMap roomData) {
    String direction = "";
    if (parsedClientMessage.equals("forward")) {
      direction = "ahead";
    } else if (parsedClientMessage.equals("backward")) {
      direction = "behind";
    } else {
      direction = parsedClientMessage;
    }
    if (roomData.get(direction).equals("a blank wall")) {
      return true;
    } else {
      return false;
    }
  }

  public Boolean coordinateValid(String coordinate) {
    File file = new File("./data/room"+coordinate);
    if(file.exists()) {
      return true;
    } else {
      return false;
    }
  }
      
  public String move(String client_message) {
    String parsedClientMessage = client_message.substring(1);
    // figure out cardinal point to move 
    HashMap roomData = getRoomData("./data/current");
    if (blankWall(parsedClientMessage, roomData)) {
      String response = "There is no room there. Try again";
      return response;
    }
    List rawCardinalRose = Arrays.asList("N", "E", "S", "W");
    List directions = Arrays.asList("forward", "right", "backward", "left");
    int rawCRIndex = rawCardinalRose.indexOf(roomData.get("orientation"));
    final List cardinalRose = orientList(rawCardinalRose, rawCRIndex);

    HashMap<String,String> directionToCardinal = new HashMap<String,String>()
    {{
       put("forward", cardinalRose.get(0).toString());
       put("right", cardinalRose.get(1).toString());
       put("backward", cardinalRose.get(2).toString());
       put("left", cardinalRose.get(3).toString());
    }};
    String orientation = roomData.get("orientation").toString();
    String moveDirection = directionToCardinal.get(parsedClientMessage);
    // move coordinate
    String newCoordinate = moveCoordinate(moveDirection, roomData.get("coordinate").toString());
    if (!coordinateValid(newCoordinate)) {
      String response = "You are not allowed to go in here. Try again";
      return response;
    }
    int cardinalIndex = cardinalRose.indexOf(moveDirection);
    List sortedCardinalRose = orientList(cardinalRose, cardinalIndex);
    HashMap map = new HashMap();
    for (int i = 0; i < directions.size(); i++) {
      map.put(directions.get(i), sortedCardinalRose.get(i));
    }
    HashMap newRoomData = getRoomData("./data/room"+newCoordinate);
    List newCurrentRoom = Arrays.asList("./data/current", newCoordinate, sortedCardinalRose.get(0), newRoomData.get("name"), getAdjacentRoom(newRoomData, map.get("left")), getAdjacentRoom(newRoomData, map.get("right")), getAdjacentRoom(newRoomData, map.get("forward")), getAdjacentRoom(newRoomData, map.get("backward")));
    Loading loading = new Loading();
    loading.createFile(newCurrentRoom);
    String response = currentRoomIntro();
    return response;
  }
}
