import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
/**
 * Tests for RoomNavigation
 *
 * @author projectdelphai@gmail.com (Reuben Castelino)
 */
public class RoomNavigationTest {
  private final RoomNavigation rnav = new RoomNavigation("./testdata/");
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
    public void increaseIndex() {
      int expected = 0;
      int actual = rnav.increaseIndex(3);
      assertEquals("Failure - index was not increased", expected, actual);
    }

    @Test
    public void moveThroughLockedDoors() {
      String actual = rnav.move("left");
      actual = rnav.move("right");
      String expected = "The door is locked.";
      assertEquals("Failure - moved through locked door", expected, actual);
    }

    @Test 
    public void currentCoordinate() {
      String expected = "0,0";
      String actual = rnav.currentCoordinate();
      assertEquals("Failure - current coordinate is not correct", expected, actual);
    }

    @Test
    public void teleportToExistingRoom() {
      String response = rnav.teleportToRoom("1,0");
      String actual = rnav.currentCoordinate();
      String expected = "1,0";
      assertEquals("Failure - current coordinate is not correct", expected, actual);      
    }

    @Test 
    public void teleportToNonExistingRoom() {
      String actual = rnav.teleportToRoom("0,1");
      String expected = "No room exists here.";
      assertEquals("Failure - current coordinate is not correct", expected, actual);      
    }

    @AfterClass
    public static void after() {
      File dir = new File("./testdata");
      for(File file: dir.listFiles()) file.delete();
    }

}
