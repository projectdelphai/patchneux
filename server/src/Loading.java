import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Loading {
  public void prepGameData() {
    // filename, coordinate, orientation, name, left, right, ahead, behind,
    List room1 = Arrays.asList("./data/room0,0", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(room1);
    List room2 = Arrays.asList("./data/room-1,0", "-1,0", "N", "A Blank Room", "a blank wall", "the neux", "a blank wall", "a blank wall");
    checkThenCreate(room2);
    List room3 = Arrays.asList("./data/room1,0", "1,0", "N", "Home", "the neux", "a blank wall", "a blank wall", "a blank wall");
    checkThenCreate(room3);
    List current = Arrays.asList("./data/current", "0,0", "N", "The Neux", "a blank room", "your home", "a currently closed metro", "the exit");
    checkThenCreate(current);
  }

  public void checkThenCreate(List dataList) {
    File file = new File(dataList.get(0).toString());
    if(!file.exists()) {
      createFile(dataList);
    }
  }

  public void createFile(final List dataList) {
    File file = new File(dataList.get(0).toString());
    LinkedHashMap<String, Object> rawGameData = new LinkedHashMap<String, Object>()
    {{
       put("coordinate", dataList.get(1));
       put("orientation", dataList.get(2));
       put("name", dataList.get(3));
       put("left", dataList.get(4));
       put("right", dataList.get(5));
       put("ahead", dataList.get(6));
       put("behind", dataList.get(7));
     }};
    Gson gson = new Gson();
    String defaultGameData = gson.toJson(rawGameData, new TypeToken<LinkedHashMap<String,Object>>(){}.getType());
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
