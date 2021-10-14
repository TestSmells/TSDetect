import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
* This test has a General Fixture smell, which means 
* a test case fixture is too general and the test methods
* only access part of it.
*
* link: https://testsmells.org/pages/testsmellexamples.html#GeneralFixture
*/

public class GeneralFixtureTest {
    private int a;
    private int b;
    private int c;

    @BeforeEach
    protected void setUp() {
        a = 1;
        b = 2;
        c = 3;
    }

    @Test
    public void GeneralFixture()
    {
        assertNotEquals(a, b);
    }
}
