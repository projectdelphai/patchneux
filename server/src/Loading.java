import java.io.*;
import java.util.*;

import flexjson.JSONSerializer;

public class Loading {
  public void prepGameData() {
    // filename, coordinate, orientation, name, left, right, ahead, behind,
    List room1 = Arrays.asList("./data/room0,0", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(room1);
    List room2 = Arrays.asList("./data/room-1,0", "-1,0", "N", "A Blank Room", "a blank wall", "the neux", "a blank wall", "a blank wall");
    checkThenCreate(room2);
    List room3 = Arrays.asList("./data/room1,0", "1,0", "N", "Home", "the neux", "a blank wall", "a blank wall", "a blank wall");
    final HashMap appleDetails = new HashMap()
    {{
       put("category", "food");
       put("description", "a juicy red apple ripe for consumption (+5)");
       put("healthIncrease", 5);
    }};
    HashMap itemMap = new HashMap()
    {{
       put("type", "items");
       put("apple", appleDetails);
    }};
    checkThenCreate(room3, itemMap);
    List current = Arrays.asList("./data/current", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(current);
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
        if (optMaps[i].get("type") == "items") {
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
