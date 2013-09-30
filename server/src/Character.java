import java.io.*;
import java.util.*;
import flexjson.JSONDeserializer;

public class Character {
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
}
