import java.net.*;
import java.io.*;

public class Server extends Thread {
  private ServerSocket listener;

  public Server(int port) throws IOException {
    listener = new ServerSocket(port);
    //listener.setSoTimeout(10000);
  }

  public void run() {
    while(true) {
      try {
        Socket server = listener.accept();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF("Thank you for connecting to patchneux. What is your name?");

        DataInputStream in = new DataInputStream(server.getInputStream());
        String user_name = in.readUTF();
        out.writeUTF("Welcome to patchneux, " + user_name + ". Your neux looks like a hexagon. Ahead is the metro (closed for business). Behind you is the exit. To your right is a blank wall and to your left is a blank wall. Your four diagonal portals are unassigned. What would you like to do?");
        while(true) {
          String client_message = in.readUTF();
          out.writeUTF(client_message);
        }
        // server.close();
      }
      catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    int port = 6006;
    try {
      Thread t = new Server(port);
      t.start();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
