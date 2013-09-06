import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    String serverName = "localhost";
    Scanner query;
    query = new Scanner(System.in);

    int port = 6006;
    try {
      Socket client = new Socket(serverName, port);
      
      while(true) {
        DataInputStream in = new DataInputStream(client.getInputStream());
        System.out.println(in.readUTF());
        
        String response = query.next();
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(response);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
