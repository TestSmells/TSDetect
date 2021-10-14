import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagicNumberTest {

    @Test
    public void FailureMagicNumber01() {
        int x = 2;
        int y = 3;
        int z = x + y;

        assertEquals(5, z);
    }

    public void test_magicNumber02() {
        assertEquals(5, 3 + 2);
    }

    public void testMagicNumber03() {
        int x = 2;
        int y = 3;
        int z = x + y;

        assertEquals(z, 5);
    }

    public void testMagicNumber04() {
        int x = 2;
        int y = 3;
        int z = x + y;

        assertEquals(z, 5);
    }

    @Test
    public void FailureMagicNumber05() {
        int x = 2;
        int y = 3;
        int z = x + y;

        assertEquals(returnInt(z), returnInt(5));
    }

    private int returnInt(int x) {
        return x;
    }

}
