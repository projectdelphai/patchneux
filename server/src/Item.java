import java.io.*;
import java.util.*;
import flexjson.JSONDeserializer;

public class Item {
  public List validItem(String itemName, String fileName) {
    String rawRoomJson = new IOMethods().readJsonFile(fileName);
    HashMap roomData = new JSONDeserializer<HashMap>().deserialize(rawRoomJson);
    List response = Arrays.asList("false");
    if (fileName.equals("./data/profile")) {
      for (Map.Entry<String, HashMap> profileEntry : ((HashMap<String, HashMap>)roomData).entrySet()) {
        if (profileEntry.getKey().startsWith("inventory")) {
          if (profileEntry.getValue().get("type").equals(itemName)) {
            response = Arrays.asList("true", profileEntry.getValue().get("type"), profileEntry.getValue(), profileEntry.getKey());
          }
        }
      }
    } else {
      HashMap itemMap = new RoomNavigation().getItemMap(roomData);
      if (itemMap != null) {
        if (itemMap.size() > 0) {
          for (Map.Entry<String, HashMap> roomItemsEntry : ((HashMap<String, HashMap>)itemMap).entrySet()) {
            if (roomItemsEntry.getKey().equals(itemName)) {
              response = Arrays.asList("true", roomItemsEntry.getKey(), roomItemsEntry.getValue());
            }
          }
        }
      }
    }
    return response;
  }

  public void removeItem(String itemName, String fileName) {
    String rawRoomJson = new IOMethods().readJsonFile("./data/current");
    HashMap roomData = new JSONDeserializer<HashMap>().deserialize(rawRoomJson);
    HashMap itemMap = new RoomNavigation().getItemMap(roomData);
    itemMap.remove("apple");
    roomData.put("items", itemMap);
    Loading loading = new Loading();
    loading.writeFile(fileName, roomData);
    if (fileName.equals("./data/current")) {
      String actualRoomName = "./data/room"+roomData.get("coordinate");
      rawRoomJson = new IOMethods().readJsonFile(actualRoomName);
      roomData = new JSONDeserializer<HashMap>().deserialize(rawRoomJson);
      roomData.put("items", itemMap);
      loading.writeFile(actualRoomName, roomData);
    }
  }

  public String info(String itemName) {
    List itemInfo = validItem(itemName, "./data/current");
    if (itemInfo.get(0).equals("true")) {
      return ((String)((HashMap)itemInfo.get(2)).get("description"));
    } else {
      return "This item does not exist. Sorry!";
    }
  }

  public String pickUp(String itemName) {
    final List itemInfo = validItem(itemName, "./data/current");
    String response = "This item does not exist";
    if (itemInfo.get(0).equals("true")) {
      String rawProfileJson = new IOMethods().readJsonFile("./data/profile");
      HashMap profileData = new JSONDeserializer<HashMap>().deserialize(rawProfileJson);
      String emptyInventory = null;
      for (Map.Entry<String, HashMap> profileEntry : ((HashMap<String, HashMap>)profileData).entrySet()) {
        if (profileEntry.getKey().startsWith("inventory")) {
          if (profileEntry.getValue().get("type").equals("empty")) {
            emptyInventory = profileEntry.getKey();
          }
        }
      }
      if (emptyInventory != null) {
        HashMap itemForFile = new HashMap()
        {{
           put("type", itemInfo.get(1));
           put("details", itemInfo.get(2));
        }};
        profileData.put(emptyInventory, itemForFile);
        Loading loading = new Loading();
        loading.writeFile("./data/profile", profileData);
        response =  "You have picked up the " + itemInfo.get(1) + ".";
        removeItem((String)itemInfo.get(1), "./data/current");
      } else {
        return "Your inventory is full. You cannot pick up anything more.";
      }
    }
    return response;
  }

  public String dropItem(String itemName) {
    HashMap roomData = new RoomNavigation().getRoomData("./data/profile");
    List itemData = validItem(itemName, "./data/profile");
    if (itemData.get(0).equals("true")) {
      roomData.remove(itemData.get(3));
      HashMap emptyInventory = new HashMap()
      {{
         put("type", "empty");
      }};
      roomData.put(itemData.get(3), emptyInventory);
      new Loading().writeFile("./data/profile", roomData);
      roomData = new RoomNavigation().getRoomData("./data/current");
      HashMap itemMap = new RoomNavigation().getItemMap(roomData);
      itemMap.put(itemData.get(1), itemData.get(2));
      roomData.put("items", itemMap);
      new Loading().writeFile("./data/current", roomData);
      String actualRoomName = "./data/room"+roomData.get("coordinate");
      roomData = new RoomNavigation().getRoomData(actualRoomName);
      itemMap = new RoomNavigation().getItemMap(roomData);
      itemMap.put(itemData.get(1), itemData.get(2));
      roomData.put("items", itemMap);
      new Loading().writeFile(actualRoomName, roomData);
      return "You have dropped the item onto the ground";
    } else {
      return "You don't have this item to drop.";
    }
  }
}
