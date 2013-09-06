import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Loading {
  public void prepGameData() {
    File neuxFile = new File("./data/room0,0");
    if(!neuxFile.exists()) {
      LinkedHashMap<String, Object> rawGameData = new LinkedHashMap<String, Object>()
      {{
         put("coordinate", "0,0");
         put("sides", 6);
         put("metro", "currently closed");
         put("left", "a blank wall");
         put("right", "a blank wall");
         put("portal1", "unassigned");
         put("portal2", "unassigned");
         put("portal3", "unassigned");
         put("portal4", "unassigned");
      }};
      Gson gson = new Gson();
      String defaultGameData = gson.toJson(rawGameData, new TypeToken<LinkedHashMap<String,Object>>(){}.getType());
      System.out.println(rawGameData);
      System.out.println(defaultGameData);
      try {
        neuxFile.getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(neuxFile, "UTF-8");
        writer.println(defaultGameData);
        writer.close();
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }
}
