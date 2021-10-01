import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has a Constructor Initialization smell, which means a method
 * constructor was used instead of a setUp method to initialize the values for that class.
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#ConstructorInitialization
 */

public class ConstructorInitializationTest {

    //create new random class to use the constructor for
    private static class TestClass{
        private final String val1;
        private final String val2;

        TestClass(String val1, String val2){
            this.val1 = val1;
            this.val2 = val2;
        }

        public String getVal1(){
            return this.val1;
        }

        public String getVal2() {
            return this.val2;
        }
    }

    private TestClass tc;
    private TestClass tc2;

    public ConstructorInitializationTest(){
        //init new objects using a constructor to trigger smell
        tc = new TestClass("test", "test");
        tc2 = new TestClass("test2", "test2");
    }

    //add test method so smeller can sniff.
    @Test
    public void TestConstructorInitializationSmell(){
        assertEquals(tc.getVal1(), tc.getVal2());
        assertNotEquals(tc2.getVal1(), tc.getVal1());
    }
}
