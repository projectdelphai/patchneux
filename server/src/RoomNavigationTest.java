import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for RoomNavigation
 *
 * @author projectdelphai@gmail.com (Reuben Castelino)
 */
@RunWith(JUnit4.class)
public class RoomNavigationTest {

    @Test
    public void increaseIndex() {
      RoomNavigation rnav = new RoomNavigation();
      int expected = 0;
      int actual = rnav.increaseIndex(3);
      assertEquals("Failure - index was not increased", expected, actual);
    }

}
