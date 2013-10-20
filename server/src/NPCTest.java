import static org.junit.Assert.assertEquals;

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
  private final Character character = new Character();
  private final Item item = new Item();

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
    List actual = npc.items;
    assertEquals("Failure - npc items were not properly recorded", expected, actual);
  }
/*
  @Test
  public void npcGreet() {
    String expected = "Hello! I'm feeling a little hungry, do you have anything to sate my hunger?";
    String actual = npc.greet();
    assertEquals("Failure - could not greet npc", expected, actual);
  }

  @Test
  public void npcGiveApple() {
    rnav.teleport("1,0");
    item.pickUp("apple");
    rnav.teleport("-1,0");
    String expected = "Thanks! I found this key during my travels, please accept it as my thanks.";
    String actual = npc.give("apple");
    HashMap characterItems = character.items;
    HashMap npcItems = npc.items;
    assertEquals("Failure - could not trade with npc", expected, actual);
    assertThat("Failure - could not get personal items", characterItems.contains("key"));
    assertFalse("Failure - still holds apple", characterItems.contains("apple"));
    assertThat("Failure - npc does not have the apple", npcItems.contains("apple"));
    assertFalse("Failure - npc still has key", npcItems.contains("key"));
    npc.give("key");
    rnav.teleport("1,0");
    item.dropItem("apple")
  }
*/ 
  @AfterClass
  public static void after() {
    File dir = new File("./testdata");
    for(File file: dir.listFiles()) file.delete();
  }
}
