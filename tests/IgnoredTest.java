import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

public class IgnoredTest {

    @Disabled("Disabled due to flakiness")
    public void IgnoredTest01() {
        boolean x = 2 + 3 == 5;

        assertTrue(x);
    }

    @Ignore("disabled for now")
    public void IgnoredTest02() {
        assertEquals("Hello", "Hello");
    }

    // This is smelly test since it has an assert statement, but the name does not start with 'test'
    public void Ignored_Test03() {
        assertEquals("Hello", "Hello");
    }

    // This is smelly test since it has an assert statement, but the name does not start with 'test'
    public void xxTest04() {
        assertTrue(true);
    }

}
