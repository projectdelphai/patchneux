import java.io.*;
import java.util.*;

public class NPC {
  private IOMethods meta = new IOMethods();

  private static String dataFolder;
  String name;

  public NPC(String... arg) {
    // arg 0 will be the name and arg 1 will be the dataFolder
    if (arg.length != 1) {
      dataFolder = arg[1];
    } else {
      dataFolder = "./data/";
    }

    HashMap npcData = meta.getData(dataFolder+"npc"+arg[0]);
    this.name = npcData.get("name").toString();
  }

  public List getItems() {
    HashMap npcData = meta.getData(dataFolder+"npc"+name);
    List npcItems = new ArrayList();
    String item;
    for (Map.Entry<String, HashMap> npcEntry : ((HashMap<String, HashMap>)npcData).entrySet()) {
      if (npcEntry.getKey().startsWith("inventory")) {
        if (!(npcEntry.getValue().get("type")).equals("empty")) {
          HashMap details = (HashMap)npcEntry.getValue().get("details");
          item = details.get("name").toString();
          npcItems.add(item);
        }
      }
    }
    return npcItems;
  }

  public String talk(String message) {
    HashMap npcData = meta.getData(dataFolder+"npc"+name);
    String response = "Sorry, I can't help you";
    for (Map.Entry<String, HashMap> npcEntry : ((HashMap<String, HashMap>)npcData).entrySet()) {
      if (npcEntry.getKey().startsWith("dialogue")) {
        HashMap<String,String> dialogueData = npcEntry.getValue();
        for (Map.Entry<String, String> dialogueEntry : dialogueData.entrySet()) {
          if (dialogueEntry.getKey().equals(message)) {
            response = dialogueEntry.getValue();
          }
        }
      }
    }
    return response;
  }

  public String giveNPC(String itemName) {
    Item itemClass = new Item(dataFolder);
    List itemData = itemClass.validItem(itemName, "profile");
    if (itemData.get(0).equals("true")) {
      String response;
      itemClass.dropItem(itemName, "profile");
      itemClass.pickUpItem(itemName, "npc"+name);
      HashMap<String,HashMap> npcData = meta.getData(dataFolder+"npc"+name);
      HashMap<String,String> switches = npcData.get("switches");
      String secondItemName = switches.get(itemName);
      itemClass.dropItem(secondItemName, "npc"+name);
      itemClass.pickUpItem(secondItemName, "profile");
      return talk(itemName);
    } else {
      return "You don't have this item";
    }
  }
}

