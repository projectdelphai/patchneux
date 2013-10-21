import java.io.*;
import java.util.*;
import flexjson.JSONDeserializer;

public class Character {
  private IOMethods meta = new IOMethods();

  private static String dataFolder;
  List items;

  public Character(String... arg) {
    if (arg.length != 0) {
      dataFolder = arg[0];
    } else {
      dataFolder = "./data/";
    }
  }

  public String aboutMe() {
    String json = new IOMethods().readJsonFile("./data/profile");
    HashMap profileData = new HashMap();
    profileData = new JSONDeserializer<HashMap>().deserialize(json);
    String response = "Your health is at " + profileData.get("health") + " hp. Your attack level is " + profileData.get("attack") + ". Your defense level is " + profileData.get("defense") + ". Your strength level is " + profileData.get("strength") + ". ";
    String items = "";
    int totalInventorySlots = 0;
    int emptyInventorySlots = 0;
    for (Map.Entry<String, HashMap> entry : ((HashMap<String, HashMap>)profileData).entrySet()) {
      if (entry.getKey().startsWith("inventory")) {
        totalInventorySlots = totalInventorySlots + 1;
        if (entry.getValue().get("type").equals("empty")) {
          emptyInventorySlots = emptyInventorySlots + 1;
        } else {
          items = items + entry.getValue().get("type") + ", ";
        }
      }
    }
    response = response + "You have " + String.valueOf(totalInventorySlots) + " total inventory slots. " + String.valueOf(emptyInventorySlots) + " slots are empty.";
    if (items.length() > 0) {
      response = response + " You current inventory holds a[n]: " + items + ".";
    }
    return response;
  }

  public List getItems() {
    HashMap npcData = meta.getData(dataFolder+"profile");
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
