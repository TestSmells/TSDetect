import org.junit.jupiter.api.Test;

public class RedundantPrintTest {
    @Test
    public void Print()
    {
        int value1 = 0;
        int value2 = 2;
        assertNotEquals(value1, value2);
        System.out.print("Value 1 and Value 2 are not equal\n");
    }

    @Test
    public void Printf()
    {
        int value1 = 0;
        int value2 = 2;
        assertNotEquals(value1, value2);
        System.out.printf("Value 1 and Value 2 are not equal\n");
    }

    @Test
    public void Println()
    {
        int value1 = 0;
        int value2 = 2;
        assertNotEquals(value1, value2);
        System.out.println("Value 1 and Value 2 are not equal");
    }

    @Test
    public void Write()
    {
        int value1 = 0;
        int value2 = 2;
        assertNotEquals(value1, value2);
        System.out.write(new byte[]{'V', 'a', 'l', 'u', 'e', ' ', '1', ' ', 'a', 'n', 'd', ' ', 'V', 'a', 'l', 'u', 'e', ' ', '2', ' ', 'a', 'r', 'e', ' ', 'n', 'o', 't', ' ', 'e', 'q', 'u', 'a', 'l', '\n'}, 0, 34);
    }
}