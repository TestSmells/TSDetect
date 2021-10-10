import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
* This test has a Lazy smell, which means
* multiple test methods invoke the same method
* of the production object.
*
* link: https://testsmells.org/pages/testsmellexamples.html#LazyTest
*/

public class LazyTest {
    static class TestClass {
        public static String getVal() {
          return "hello";
        }
    }

    @Test
    public void Lazy()
    {
        String s = TestClass.getVal();
        assertEquals("hello", s);
    }

    @Test
    public void Lazy2()
    {
        assertEquals("hello", TestClass.getVal());
    }
}
