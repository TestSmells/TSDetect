import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has a unknown test smell, which means a method
 * does not have any assert method inside of it.
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#UnknownTest
 */

public class UnknownTest {


    //test that doesnt actually have an assert in it.
    @Test
    public void UnknownTestSmell(){
        int a = 2;
        int b = 3;

        int c = a + b;
    }

    @Test
    public void NoUnknownTestSmell(){
        int a = 2;
        int b = 2;
        assertEquals(a, b);
        System.out.println("Hi");
    }

}