import java.io.*;
import java.util.*;
import flexjson.JSONDeserializer;

public class Item {
  private static String dataFolder;
  private IOMethods meta = new IOMethods();

  public Item(String... arg) {
    if (arg.length != 0) {
      dataFolder = arg[0];
    } else {
      dataFolder = "./data/";
    }
  }

  public List validItem(String itemName, String fileName) {
    HashMap roomData = meta.getData(dataFolder+fileName);
    List response = Arrays.asList("false");
    if (fileName.contains("profile") || fileName.contains("npc")) {
      for (Map.Entry<String, HashMap> profileEntry : ((HashMap<String, HashMap>)roomData).entrySet()) {
        if (profileEntry.getKey().startsWith("inventory")) {
          if (profileEntry.getValue().get("type").equals(itemName)) {
            response = Arrays.asList("true", profileEntry.getValue().get("type"), profileEntry.getValue().get("details"), profileEntry.getKey());
          }
        }
      }
    } else {
      HashMap itemMap = new RoomNavigation(dataFolder).getItemMap(roomData);
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
    HashMap roomData = meta.getData(dataFolder+"current");
    HashMap itemMap = new RoomNavigation(dataFolder).getItemMap(roomData);
    itemMap.remove(itemName);
    roomData.put("items", itemMap);
    Loading loading = new Loading();
    loading.writeFile(dataFolder+fileName, roomData);
    if (fileName.equals(dataFolder+"current")) {
      String actualRoomName = dataFolder+"room"+roomData.get("coordinate");
      roomData = meta.getData(actualRoomName);
      roomData.put("items", itemMap);
      loading.writeFile(actualRoomName, roomData);
    }
  }

  public String info(String itemName) {
    List itemInfo = validItem(itemName, "current");
    if (itemInfo.get(0).equals("true")) {
      return ((String)((HashMap)itemInfo.get(2)).get("description"));
    } else {
      return "This item does not exist. Sorry!";
    }
  }

  public String pickUpItem(String itemName, String target) {
    final List itemInfo = validItem(itemName, "current");
    String response = "This item does not exist";
    if (itemInfo.get(0).equals("true")) {
      HashMap profileData = meta.getData(dataFolder+target);
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
        loading.writeFile(dataFolder+target, profileData);
        response =  "You have picked up the " + itemInfo.get(1) + ".";
        removeItem((String)itemInfo.get(1), dataFolder+"current");
      } else {
        return "Your inventory is full. You cannot pick up anything more.";
      }
    }
    return response;
  }

  public String dropItem(String itemName, String holder) {
    HashMap roomData = meta.getData(dataFolder+holder);
    List itemData = validItem(itemName, holder);
    if (itemData.get(0).equals("true")) {
      roomData.remove(itemData.get(3));
      HashMap emptyInventory = new HashMap()
      {{
         put("type", "empty");
      }};
      roomData.put(itemData.get(3), emptyInventory);
      new Loading().writeFile(dataFolder+holder, roomData);
      roomData = meta.getData(dataFolder+"current");
      HashMap itemMap = new RoomNavigation(dataFolder).getItemMap(roomData);
      itemMap.put(itemData.get(1), itemData.get(2));
      roomData.put("items", itemMap);
      new Loading().writeFile(dataFolder+"current", roomData);
      String actualRoomName = dataFolder+"room"+roomData.get("coordinate");
      roomData = meta.getData(actualRoomName);
      itemMap = new RoomNavigation(dataFolder).getItemMap(roomData);
      itemMap.put(itemData.get(1), itemData.get(2));
      roomData.put("items", itemMap);
      new Loading().writeFile(actualRoomName, roomData);
      return "You have dropped "+itemName+" onto the ground";
    } else {
      return "You don't have this item to drop.";
    }
  }

  public void changeDoorValue(String target, String targetName, String newTargetName, HashMap roomData) {
    List doors = Arrays.asList("right", "left", "ahead", "behind");
    roomData.put(target, newTargetName);
    Loading loading = new Loading();
    loading.writeFile(dataFolder+"current", roomData);
    HashMap actualRoomData = new IOMethods().getData(dataFolder+"room"+roomData.get("coordinate"));
    String hashMapKey;
    for (Map.Entry entry : ((HashMap<String,String>)actualRoomData).entrySet()) {
      if (doors.contains(entry.getKey())) {
        if (entry.getValue().equals(targetName)) {
          hashMapKey = entry.getKey().toString();
          actualRoomData.put(hashMapKey, newTargetName);
        }
      }
    }
    loading.writeFile(dataFolder+"room"+roomData.get("coordinate"), actualRoomData);
  }

  public String applyItem(String itemName, String target) {
    String response = "Target does not exist.";
    List itemData = validItem(itemName, "profile");
    if (itemData.get(0).equals("false")) {
        response = "You do not have this item to apply to "+target;
    }
    List doors = Arrays.asList("right", "left", "ahead", "behind");
    if (doors.contains(target)) {
      response = "You cannot modify this door.";
      HashMap roomData = new IOMethods().getData(dataFolder+"current");
      String targetName = roomData.get(target).toString();
      if (targetName.contains("(locked)")) {
          String newTargetName = targetName.replace("(locked)", "(unlocked)");
          changeDoorValue(target, targetName, newTargetName, roomData);
          response = "You have unlocked the door";
      } else if (targetName.contains("(unlocked)")) {
          String newTargetName = targetName.replace("(unlocked)", "(locked)");
          changeDoorValue(target, targetName, newTargetName, roomData);
          response = "You have locked the door";
      }
    }
    return response;
  }
}
