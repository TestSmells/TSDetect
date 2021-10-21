import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensitiveEqualityTest {
    public class ExampleClass {

        @Override
        public String toString() {
            return "This is an example toString that just might be change in the future";
        }
    }

    @Test
    public void EmptyTest()
    {
        ExampleClass examp = new ExampleClass();
        assertEquals(examp.toString(),
                "This is an example toString that just might be change in the future");

    }
}
