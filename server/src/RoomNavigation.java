import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class RoomNavigation {
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
    HashMap roomData = new HashMap();
    roomData=(HashMap) gson.fromJson(json, roomData.getClass());
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

  public String getNewCoordinate(String direction, String oldCoordinate) {
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

  public Boolean blankWall(String moveDirection, HashMap roomData) {
    if (roomData.get(moveDirection).equals("a blank wall")) {
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

  public List orientCardinalRose(HashMap roomData) {
    List rawCardinalRose = Arrays.asList("N", "E", "S", "W");
    int rawCRIndex = rawCardinalRose.indexOf(roomData.get("orientation"));
    List cardinalRose = orientList(rawCardinalRose, rawCRIndex);
    return cardinalRose;
  }

  public String determineCardinalDirectionToMove(String moveDirection, HashMap roomData, final List cardinalRose) {
    HashMap<String,String> directionToCardinal = new HashMap<String,String>()
    {{
       put("ahead", cardinalRose.get(0).toString());
       put("right", cardinalRose.get(1).toString());
       put("behind", cardinalRose.get(2).toString());
       put("left", cardinalRose.get(3).toString());
    }};
    String orientation = roomData.get("orientation").toString();
    String moveCardinalDirection = directionToCardinal.get(moveDirection);
    return moveCardinalDirection;
  }

  public HashMap connectDirectionsToCardinal(List cardinalRose, String moveCardinalDirection) {
    int cardinalIndex = cardinalRose.indexOf(moveCardinalDirection);
    List sortedCardinalRose = orientList(cardinalRose, cardinalIndex);
    List directions = Arrays.asList("ahead", "right", "behind", "left");
    HashMap DirectionToCardinal = new HashMap();
    for (int i = 0; i < directions.size(); i++) {
      DirectionToCardinal.put(directions.get(i), sortedCardinalRose.get(i));
    }
    return DirectionToCardinal;
  }

  public String move(String moveDirection) {
    HashMap roomData = getRoomData("./data/current");
    List cardinalRose = orientCardinalRose(roomData);
    if (blankWall(moveDirection, roomData)) {
      String response = "There is no room there. Try again";
      return response;
    }
    String moveCardinalDirection = determineCardinalDirectionToMove(moveDirection,roomData,cardinalRose);
    String newCoordinate = getNewCoordinate(moveCardinalDirection, roomData.get("coordinate").toString());
    if (!coordinateValid(newCoordinate)) {
      String response = "You are not allowed to go in here. Try again";
      return response;
    }
    int cardinalIndex = cardinalRose.indexOf(moveCardinalDirection);
    List sortedCardinalRose = orientList(cardinalRose, cardinalIndex);
    HashMap DirectionToCardinal = connectDirectionsToCardinal(cardinalRose, moveCardinalDirection);
    HashMap newRoomData = getRoomData("./data/room"+newCoordinate);
    List newCurrentRoom = Arrays.asList("./data/current", newCoordinate, sortedCardinalRose.get(0), newRoomData.get("name"), getAdjacentRoom(newRoomData, DirectionToCardinal.get("left")), getAdjacentRoom(newRoomData, DirectionToCardinal.get("right")), getAdjacentRoom(newRoomData, DirectionToCardinal.get("ahead")), getAdjacentRoom(newRoomData, DirectionToCardinal.get("behind")));
    Loading loading = new Loading();
    loading.createFile(newCurrentRoom);
    String response = currentRoomIntro();
    return response;
  }

  public String createRoom(DataInputStream in, DataOutputStream out, String clientMessage) {
    String roomName = "Blank Room";
    String roomSide = "left";
    try {
      out.writeUTF("What is the name of the new room?END");
      roomName = in.readUTF();
      out.writeUTF("On which side do you want to create this room?END");
      roomSide = in.readUTF();
    } catch (IOException e) {
      e.printStackTrace();
    }
    HashMap roomData = getRoomData("./data/current");
    List cardinalRose = orientCardinalRose(roomData);
    String moveCardinalDirection = determineCardinalDirectionToMove(roomSide,roomData,cardinalRose);
    String newCoordinate = getNewCoordinate(moveCardinalDirection, roomData.get("coordinate").toString());
    if (coordinateValid(newCoordinate)) {
      String response = "You are not allowed to create a room here. Try again";
      return response;
    }
    String N = getNameOfRoom("N", newCoordinate);
    String E = getNameOfRoom("E", newCoordinate);
    String W = getNameOfRoom("W", newCoordinate);
    String S = getNameOfRoom("S", newCoordinate);
    List newRoomData = Arrays.asList("./data/room"+newCoordinate, newCoordinate, "N", roomName, W, E, N, S);
    Loading loading = new Loading();
    loading.createFile(newRoomData);
    updateAdjacentRooms(newCoordinate, roomName, roomSide);
    return "Room created";
  }

  public void updateAdjacentRooms(String newCoordinate, String roomName, String roomSide) {
    List sideRooms = Arrays.asList(getNewCoordinate("N", newCoordinate), getNewCoordinate("W", newCoordinate), getNewCoordinate("E", newCoordinate), getNewCoordinate("S", newCoordinate));
    int index = 0;
    HashMap oppositeCardinals = new HashMap()
    {{
       put("N", "S");
       put("W", "E");
       put("E", "W");
       put("S", "N");
    }};
    HashMap CardinalToDirection = new HashMap()
    {{
       put("N", "ahead");
       put("W", "left");
       put("E", "right");
       put("S", "behind");
    }};
    List orderOfCardinals = Arrays.asList("N", "W", "E", "S");
    for (Object sideRoomCoordinate : sideRooms) {
      if (coordinateValid(sideRoomCoordinate.toString())) {
        HashMap newRoomData = getRoomData("./data/room"+sideRoomCoordinate);
        newRoomData.put(CardinalToDirection.get(oppositeCardinals.get(orderOfCardinals.get(index))), roomName);
        Loading loading = new Loading();
        loading.writeFile("./data/room"+sideRoomCoordinate, newRoomData);
        HashMap currentRoomData = getRoomData("./data/current");
        currentRoomData.put(roomSide, roomName);
        loading.writeFile("./data/current", currentRoomData);
      } else {
        index = index+1;
      }
    }
  }

  public String getNameOfRoom(String cardinalDirection, String coordinate) {
    String newCoordinate = getNewCoordinate(cardinalDirection, coordinate);
    String nameOfRoom = "a blank wall";
    if (!coordinateValid(newCoordinate)) {
      return nameOfRoom;
    } else {
      HashMap tmpRoomData = getRoomData("./data/room"+newCoordinate);
      nameOfRoom = tmpRoomData.get("name").toString();
      return nameOfRoom;
    }
  }
}
