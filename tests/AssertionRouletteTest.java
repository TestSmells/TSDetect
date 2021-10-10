import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionRouletteTest {

    @Test
    public void FailureAssertionRoulette01() {
        assertEquals("Test", "Test");
        assertNotEquals("This is A Fail", "Test");
        assertEquals("Test", "Test");
    }

    public void testFailureAssertionRoulette02() {
        assertEquals("Test", "Test", "This is a message");
        assertNotEquals("This is A Fail", "Test");
        assertEquals("Test", "Test");
    }
}
