import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Redundant Assertion, which means the
 * assert is comparing 2 same booleans
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#RedundantAssertion
 */
public class RedundantAssertTest {

    @Test
    public void testAlwaysTrue() {
        boolean x = true;
        assertEquals(x, true);
    }

    @Test
    public void testNotAlwaysTrue() {
        int x = 2 + 2; // note: this is obviously still constant, but handling cases like this is out of scope
        assertEquals(x, 4);
    }
}
