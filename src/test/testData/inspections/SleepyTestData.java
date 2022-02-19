import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SleepyTest {
    @Test
    public void SleepyTest() throws Exception{
        assertEquals("test", "test");
        Thread.sleep(1000);
        assertEquals(5, 5);
    }

    @Test
    public void NotSleepyTest() throws Exception{
        assertEquals("test", "test");
        assertEquals(5, 5);
    }
}