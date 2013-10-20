import java.io.*;
import java.util.*;

public class NPC {
  private IOMethods meta = new IOMethods();

  private static String dataFolder;
  String name;
  List items;

  public NPC(String... arg) {
    // arg 0 will be the name and arg 1 will be the dataFolder
    if (arg.length != 1) {
      dataFolder = arg[1];
    } else {
      dataFolder = "./data/";
    }

    HashMap npcData = meta.getData(dataFolder+"npc"+arg[0]);

    this.name = npcData.get("name").toString();
    this.items = getItems(npcData);
  }

  public List getItems(HashMap npcData) {
    List npcItems = new ArrayList();
    for (Map.Entry<String, HashMap> npcEntry : ((HashMap<String, HashMap>)npcData).entrySet()) {
      if (npcEntry.getKey().startsWith("inventory")) {
        if (!npcEntry.getValue().get("type").equals("empty")) {
          HashMap details = (HashMap)npcEntry.getValue().get("details");
          String item = details.get("name").toString();
          npcItems.add(item);
        }
      }
    }
    return npcItems;
  }
}

