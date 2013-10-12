import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
/**
 * Tests for RoomNavigation
 *
 * @author projectdelphai@gmail.com (Reuben Castelino)
 */
@RunWith(JUnit4.class)
public class RoomNavigationTest {
  private final RoomNavigation rnav = new RoomNavigation();
    @BeforeClass
    public static void before() {
      Loading loading = new Loading("./testdata/");
      loading.prepGameData();
    }

    @Test
    public void increaseIndex() {
      int expected = 0;
      int actual = rnav.increaseIndex(3);
      assertEquals("Failure - index was not increased", expected, actual);
    }

    @Test
    public void moveThroughLockedDoors() {
    }

    @AfterClass
    public static void after() {
      File dir = new File("./testdata");
      for(File file: dir.listFiles()) file.delete();
    }

}
