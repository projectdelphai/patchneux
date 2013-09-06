import java.net.*;
import java.io.*;

public class Server implements Runnable {
  private Socket server;

  public Server(Socket server) {
    this.server = server;
  }

  public void run() {
    try {
      Loading prep = new Loading();
      prep.prepGameData();
      DataOutputStream out = new DataOutputStream(server.getOutputStream());
      out.writeUTF("What is your name?");
      DataInputStream in = new DataInputStream(server.getInputStream());
      String user_name = in.readUTF();
      Parser parser = new Parser();
      String intro = parser.intro(user_name);
      out.writeUTF(intro);
      while(true) {
        String client_message = in.readUTF();
        out.writeUTF(client_message);
      }
    } catch (EOFException e) {
    } catch (IOException e) { e.printStackTrace(); 
    }
  }

  public static void main(String args[]) {
    try {
      ServerSocket listener = new ServerSocket(6006);
      
      while(true) {
        Socket server = listener.accept();
        Runnable r = new Server(server);
        new Thread(r).start();
      }
    } catch(IOException e) { e.printStackTrace(); }
  }
}
