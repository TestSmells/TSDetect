import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Assertion Roulette, which means the
 * methods multiple assertions without having any messages explaining why
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#AssertionRoulette
 */
public class AssertionRouletteTest {

    @Test
    public void FailureAssertionRoulette()
    {
        assertEquals("Test", "Test");
        assertNotEquals("This is A Fail", "Test");
        assertEquals("Test", "Test");
    }
}
