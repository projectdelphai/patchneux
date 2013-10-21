import java.io.*;
import java.util.*;

import flexjson.JSONSerializer;

public class Loading {
  private static String dataFolder;
  
  public Loading(String... arg) {
    if (arg.length != 0) {
      dataFolder = arg[0];
    } else {
      dataFolder = "./data/";
    }
  }

  public void prepGameData() {
    createRooms();
    createProfile();
    createNPCs();
  }

  public void createRooms() {
    // filename, coordinate, orientation, name, left, right, ahead, behind,
    List room4 = Arrays.asList(dataFolder+"room-1,1", "-1,1", "N", "A Room with a Book", "a blank wall", "a currently closed metro", "a blank room", "A Room With a Lock (locked)");
    checkThenCreate(room4);
    List room1 = Arrays.asList(dataFolder+"room0,0", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(room1);
    List room2 = Arrays.asList(dataFolder+"room-1,0", "-1,0", "N", "A Room With a Lock", "a blank wall", "the neux", "A Room with a Book (locked)", "a blank wall");
    final HashMap guideDetails = new HashMap()
    {{
       put("category", "npc");
       put("description", "a helpful Guide to help you out");
    }};
    HashMap itemMap = new HashMap()
    {{
       put("type", "items");
       put("Guide", guideDetails);
    }};
    checkThenCreate(room2, itemMap);
    List room3 = Arrays.asList(dataFolder+"room1,0", "1,0", "N", "Home", "the neux", "a blank wall", "a blank wall", "a blank wall");
    final HashMap appleDetails = new HashMap()
    {{
       put("name", "apple");
       put("category", "food");
       put("description", "a juicy red apple ripe for consumption (+5)");
       put("healthIncrease", 5);
    }};
    itemMap = new HashMap()
    {{
       put("type", "items");
       put("apple", appleDetails);
    }};
    checkThenCreate(room3, itemMap);
    List current = Arrays.asList(dataFolder+"current", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(current);
  }

  public void createProfile() {
    final HashMap emptyHashMap = new HashMap()
    {{
       put("type", "empty");
    }};
    HashMap profileData = new HashMap()
    {{
       put("inventory1", emptyHashMap);
       put("inventory2", emptyHashMap);
       put("inventory3", emptyHashMap);
       put("inventory4", emptyHashMap);
       put("inventory5", emptyHashMap);
       put("inventory6", emptyHashMap);
       put("health", "1");
       put("attack", "1");
       put("defense", "1");
       put("strength", "1");
    }};
    String fileName = dataFolder+"profile";
    File file = new File(fileName);
    if(!file.exists()) {
      writeFile(fileName, profileData);
    }
  }

  public void createNPCs() {
    final HashMap emptyHashMap = new HashMap()
    {{
       put("type", "empty");
    }};
    final HashMap keyDetails = new HashMap()
    {{
       put("name", "key");
       put("category", "key");
       put("id", "1");
       put("description", "a mysterious old key");
    }};
    final HashMap key = new HashMap()
    {{
       put("type", "key");
       put("details", keyDetails);
    }};
    final HashMap dialogue = new HashMap()
    {{
       put("greet", "Hello! I'm feeling a little hungry, do you have anything to sate my hunger?");
       put("apple", "Thanks! I found this key during my travels, please accept it as my thanks.");
       put("key", "You want me to hold this for a while? Alright, here's an apple in exchange.");
    }};
    final HashMap switches = new HashMap()
    {{
       put("key", "apple");
       put("apple", "key");
    }};
    final List acceptItems = Arrays.asList("key", "apple");
    final List giveItems = Arrays.asList("key", "apple");
    HashMap guideData = new HashMap()
    {{
       put("name", "Guide");
       put("inventory1", key);
       put("inventory2", emptyHashMap);
       put("inventory3", emptyHashMap);
       put("inventory4", emptyHashMap);
       put("health", "1");
       put("attack", "1");
       put("defense", "1");
       put("strength", "1");
       put("dialogue", dialogue);
       put("switches", switches);
       put("accepts", acceptItems);
       put("gives", giveItems);
    }};
    String fileName = dataFolder+"npcGuide";
    File file = new File(fileName);
    if(!file.exists()) {
      writeFile(fileName, guideData);
    }
  }

  public void checkThenCreate(List dataList, HashMap... optMaps) {
    File file = new File(dataList.get(0).toString());
    if(!file.exists()) {
      if (optMaps.length != 0) {
        createFile(dataList, optMaps[0]);
      } else {
        createFile(dataList);
      }
    }
  }
  
  public void createFile(final List dataList, HashMap... optMaps) {
    HashMap rawGameData = new HashMap()
    {{
       put("coordinate", dataList.get(1));
       put("orientation", dataList.get(2));
       put("name", dataList.get(3));
       put("left", dataList.get(4));
       put("right", dataList.get(5));
       put("ahead", dataList.get(6));
       put("behind", dataList.get(7));
    }};
    if (optMaps.length != 0) {
      for (int i=0; i < optMaps.length; i++) {
        if (optMaps[i] != null) {
          rawGameData.put("items", optMaps[i]);
        }
      }
    }
    writeFile(dataList.get(0).toString(), rawGameData);
  }

  public void writeFile(String fileName, HashMap rawGameData) {
    File file = new File(fileName);
    JSONSerializer serializer = new JSONSerializer();
    String defaultGameData = serializer.serialize(rawGameData);
    try {
      file.getParentFile().mkdirs();
      PrintWriter writer = new PrintWriter(file, "UTF-8");
      writer.println(defaultGameData);
      writer.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
