import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensitiveEqualityTest {
    public class ExampleClass {

        @Override
        public String toString() {
            return "This is an example toString that just might be change in the future";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            // add relevant checks here
            return true;
        }
    }

    @Test
    public void SensitiveEqualityTest()
    {
        ExampleClass examp = new ExampleClass();
        assertEquals(examp.toString(),
                "This is an example toString that just might be change in the future");

    }

    @Test
    public void NotSensitiveEqualityTest()
    {
        ExampleClass examp = new ExampleClass();
        assertEquals(examp,
                "This is an example toString that just might be change in the future");

    }
}
