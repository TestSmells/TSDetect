import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionRouletteTest {

    @Test
    public void FailureAssertionRoulette()
    {
        assertEquals("Test", "Test");
        assertNotEquals("This is A Fail", "Test");
        assertEquals("Test", "Test");
    }
}
