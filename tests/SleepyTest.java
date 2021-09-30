import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SleepyTest {
    @Test
    public void SleepyTest() throws Exception{
        assertEquals("test", "test");
        Thread.sleep(1000);
        assertEquals(5, 5);
    }


}
