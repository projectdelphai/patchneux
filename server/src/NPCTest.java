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
public class NPCTest {
  private final RoomNavigation rnav = new RoomNavigation("./testdata/");
  private final NPC npc = new NPC("Guide", "./testdata/");
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
  public void createNPCObject() {
    String expected = "Guide";
    String actual = npc.name;
    assertEquals("Failure - npc name does not match", expected, actual);
  }
 
  @Test
  public void npcItemCheck() {
    List expected = Arrays.asList("key");
    List actual = npc.getItems();
    assertEquals("Failure - npc items were not properly recorded", expected, actual);
  }
  
  @Test
  public void npcGreet() {
    String expected = "Hello! I'm feeling a little hungry, do you have anything to sate my hunger?";
    String actual = npc.talk("greet");
    assertEquals("Failure - could not greet npc", expected, actual);
  }

  @Test
  public void npcGiveApple() {
    String response = rnav.move("right");
    response = item.pickUpItem("apple", "profile");
    response = rnav.teleportToRoom("-1,0");
    String expected = "Thanks! I found this key during my travels, please accept it as my thanks.";
    String actual = npc.giveNPC("apple");
    List characterItems = character.getItems();
    List npcItems = npc.getItems();
    assertEquals("Failure - could not trade with npc", expected, actual);
    assertTrue("Failure - does not have key", characterItems.contains("key"));
    assertFalse("Failure - still holds apple", characterItems.contains("apple"));
    assertTrue("Failure - npc does not have the apple", npcItems.contains("apple"));
    assertFalse("Failure - npc still has key", npcItems.contains("key"));
    npc.giveNPC("key");
    rnav.teleportToRoom("1,0");
    item.dropItem("apple", "profile");
  }
  
  @AfterClass
  public static void after() {
    File dir = new File("./testdata");
    for(File file: dir.listFiles()) file.delete();
  }
}
