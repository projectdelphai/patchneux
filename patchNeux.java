import java.util.Scanner;

public class patchNeux {

  public static void main(String[] args) {

    String user_name;
    String stored_name;
    Scanner in;

    in = new Scanner(System.in);
    System.out.println("Enter your login name");
    user_name = in.next();
    stored_name = "test";

    if (user_name.equals(stored_name)) {
      System.out.println("Welcome to your neux, " + user_name + ". Your neux looks like a hexagon. Ahead is the metro (which is closed for business). Behind you is the exit. To your right is a blank wall and to your left is a blank wall. Your four diagonal portals are unassigned. What would you like to do?");
    } else {
      System.out.println("You have provided the wrong username. ");
      System.exit(0);
    }


  }

}
