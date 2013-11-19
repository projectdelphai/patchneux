import java.io.*;
import java.util.*;

public class Trap {
    private static String dataFolder;
    private IOMethods meta = new IOMethods();

    public Trap(String... arg) {
        if (arg.length != 0) {
            dataFolder = arg[0];
        } else {
            dataFolder = "./data/";
        }
    }

    public String lookAtItem(String target) {
        List doors = Arrays.asList("right", "left", "ahead", "behind");
        String response = "You could not look at the target";
        if (doors.contains(target)) {
            HashMap roomData = meta.getData(dataFolder+"current");
            String destination = roomData.get(target).toString();
            List trapList = new ArrayList();
            if (roomData.get(target+"traps") != null) {
                HashMap rawTrapList = (HashMap<String,HashMap>)roomData.get(target+"traps");
                rawTrapList = (HashMap<String,HashMap>)rawTrapList.get("traps");
                List order = new ArrayList(rawTrapList.values());
                Collections.sort(order);
                String highestTrapKey = order.get(0).toString();
                rawTrapList = (HashMap)rawTrapList.get(highestTrapKey);
                String trapName = rawTrapList.get("name").toString();
                trapList.add(trapName);
            }
            StringBuilder sb = new StringBuilder();
            for (String s : (List<String>)trapList)
            {
                sb.append(s);
                sb.append(",");
            }
            response = "The "+target+" door leads to "+destination+". The traps on this door are: "+sb.toString();
        }
        return response;
    }

}
