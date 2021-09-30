import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

public class IgnoredTest {

    @Disabled("Disabled due to flakiness")
    public void IgnoredTest()
    {
        boolean x = 2+3 == 5;

        assertTrue(x);
    }

}
