import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;
/**
 * Tests for NPC
 *
 * @author projectdelphai@gmail.com (Reuben Castelino)
 */
public class ItemTest {
  private RoomNavigation rnav = new RoomNavigation("./testdata/");
  private final Character character = new Character("./testdata/");
  private final Item item = new Item("./testdata/");

  @BeforeClass
  public static void beforeClass() {
    Loading loading = new Loading("./testdata/");
    loading.prepGameData();
  }
  
  @Before
  public void beforeTest() {
    String response = rnav.teleportToRoom("0,0");
  }
  
  @Test
  public void unlockRoom() {
    String response = rnav.move("right");
    response = item.pickUpItem("apple", "profile");
    rnav.teleportToRoom("-1,0");
    NPC npc = new NPC("Guide", "./testdata/");
    npc.giveNPC("apple");
    HashMap key = new HashMap()
    {{
         put("key", "key");
    }};
    response = item.applyItem(key,"ahead");
    String expected = "Welcome to A Room with a Book. Ahead is a blank room. Behind you is A Room With a Lock (locked). To your right is a currently closed metro and to your left is a blank wall.";
    String actual = rnav.move("ahead");
    assertEquals("Failure - key did not unlock the door.", expected, actual);
  }
 
  @AfterClass
  public static void after() {
    File dir = new File("./testdata");
    for(File file: dir.listFiles()) file.delete();
  }
}
