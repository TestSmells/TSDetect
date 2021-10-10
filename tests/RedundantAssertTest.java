import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedundantAssertTest {

    @Test
    public void testTrue01() {
        assertEquals(true, true);
    }


    public void testTrue02() {
        boolean x = true;
        assertEquals(x, true);
    }

    public void testTrue03() {
        assertTrue(true);
    }

    public void testTrue04() {
        assertTrue(!false);
    }

    @Test
    public void testTrue05() {
        assertEquals(4, 4);
    }

    @Test
    public void alwaysFalse01() {
        assertFalse(false);
    }

    @Test
    public void alwaysFalse02() {
        assertFalse(!true);
    }

    @Test
    public void alwaysNull01() {
        assertNull(null);
    }
}
