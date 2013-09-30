import java.io.*;
import java.util.*;
import flexjson.JSONDeserializer;

public class IOMethods {
  public String readJsonFile(String fileName) {
    String line = null;
    String json = line;
    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null) {
        json = line;
      }
      bufferedReader.close();
    } catch(FileNotFoundException ex) {
      System.out.println( "Unable to open file '" + fileName + "'");
    } catch(IOException e) {
      e.printStackTrace();
    }
    return json;
  }
}
