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
 * Tests for Trap
 *
 * @author projectdelphai@gmail.com (Reuben Castelino)
 */
public class TrapTest {
  private RoomNavigation rnav = new RoomNavigation("./testdata/");
  private final Character character = new Character("./testdata/");
  private final Item item = new Item("./testdata/");
  private final Trap trap = new Trap("./testdata/");

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
  public void lookAtDoor() {
      rnav.move("left");
      String expected = "The right door leads to A Room with a Book (locked). The traps on this door are: ";
      String actual = trap.lookAtItem("right");
      assertEquals("Failure - You did not correctly look at the door", expected, actual);
  }

  @Test
  public void layTrapOnDoor() {
      rnav.move("left");
      final HashMap trapMap = new HashMap()
      {{
           put("name", "StatDrop");
           put("effect", "health-1");
      }};
      HashMap highTrapMap = new HashMap()
      {{
           put("trapBag", trapMap);
      }};
      item.applyItem(highTrapMap,"right");
      String expected = "The right door leds to A Room with a Book (locked). The traps on this door are: StatDrop,";
      String actual = trap.lookAtItem("right");
      assertEquals("Failure - The trap was not correctly laid", expected, actual);
  }

  @AfterClass
  public static void after() {
    File dir = new File("./testdata");
    for(File file: dir.listFiles()) file.delete();
  }
}
