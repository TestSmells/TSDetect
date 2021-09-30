import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagicNumberTest {

    @Test
    public void FailureMagicNumber()
    {
        int x = 2;
        int y = 3;
        int z = x+y;

        assertEquals(5, z);
    }

}
