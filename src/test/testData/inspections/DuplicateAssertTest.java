import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateAssertTestTest {

    @Test
    public void DuplicateAssertTest()
    {
        int expectedOne = 3; //expected value being tested for
        int actualOne = 3; // actual value
        int expectedTwo = 4;
        int actualTwo = 4;

        assertEquals(actualOne,expectedOne);
        assertEquals(actualTwo,expectedTwo);

        assertEquals(actualTwo,expectedTwo);
        assertEquals(actualOne,expectedOne);

        assertEquals(actualOne,expectedOne);
        assertEquals(actualTwo,expectedTwo);

    }

    @Test
    public void NotDuplicateAssertTest()
    {
        int expectedOne = 3; //expected value being tested for
        int actualOne = 3; // actual value

        int expectedTwo = 4;
        int actualTwo = 4;

        assertEquals(actualOne,expectedOne);
        assertEquals(actualTwo,expectedTwo);

    }
}
