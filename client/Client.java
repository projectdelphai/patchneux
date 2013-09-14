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
        String serverMessage = in.readUTF();
        if (serverMessage.endsWith("END")) {
          serverMessage = serverMessage.substring(0, serverMessage.length() - 3);
          System.out.println(serverMessage);
        } else {
          while(!serverMessage.endsWith("END")) {
            System.out.println(serverMessage);
            serverMessage = in.readUTF();
          }
          serverMessage = serverMessage.substring(0, serverMessage.length() - 3);
          System.out.println(serverMessage);
        }
        
        String response = query.next();
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(response);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
