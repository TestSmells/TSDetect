import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Assertion Roulette, which means the
 * methods have multiple assertions without having any messages explaining why
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#AssertionRoulette
 */
public class DuplicateAssertTestTest {
    /**
     * a fake assert method to ensure that incorrect methods are not being found
     * @param y
     */
    private void assertMethod(int y){
        int x = 0;
    }

    /**
     * test for false positives
     */
    @Test
    public void hasNoActualJunitAsserts(){
        assertMethod(1);
        assertMethod(2);

        assert(true!=true);
        assert(true!=false);

    }

    @Test
    public void hasFailTwiceWithoutMessages(){
        fail("message");
        fail("message");
    }

    /**
     * message explaining that the same call is actually different
     */
    @Test
    public void testIsActuallyAssertionRoulette() {
        assertEquals("Test", "Test");
        assertEquals("Test", "Test");
    }

    /**
     * no message so all parts are duplicate
     */

    /**
     * unique message, so only duplicate part is name
     */
    @Test
    public void assertTrueWorks(){
        assertTrue(true,"message1");
        assertTrue(true,"message2");
    }

    @Test
    public void assertFalseWorks(){
        assertFalse(true,"message3");
        assertFalse(true,"message4");
    }

    @Test
    public void assertNotNullWorks(){
        assertNotNull("something","message5");
        assertNotNull("something","message6");
    }

    @Test
    public void assertNullWorks(){
        assertNull("something","message7");
        assertNull("something","message8");
    }

    @Test
    public void assertArrayEqualsWorks(){
        int [] one = {0,1};
        int [] two = {0,1};
        assertArrayEquals(one,one,"message1");
        assertArrayEquals(one,two,"message2");

    }
    @Test
    public void assertEqualsWorks(){
        assertEquals("first","first","message3");
        assertEquals("first","first","message4");
    }
    @Test
    public void assertNotEqualsWorks(){
        assertNotEquals("first","first","message5");
        assertNotEquals("first","first","message6");
    }
    @Test
    public void assertNotSameWorks(){
        assertNotSame("first","first","message7");
        assertNotSame("first","first","message8");
    }
    @Test
    public void assertSameWorks(){
        assertSame("first","first","message9");
        assertSame("first","first","message0");
    }

    private Executable temp1 (){
        throw new RuntimeException();
    }
    private Executable temp2 (){
        throw new RuntimeException();
    }
    @Test
    public void assertThrowsWorks(){
        assertThrows(RuntimeException.class, temp1(),"message1");
        assertThrows(RuntimeException.class, temp1(),"message2");
    }
    /**
     * no message, all is duplicate
     */
    @Test
    public void assertTrueWorksNoMessage(){
        assertTrue(true);
        assertTrue(true);

    }
    @Test
    public void assertEqualsWorksNoMessage(){
        assertEquals("first","first");
        assertEquals("first","first");
    }

    @Test
    public void assertThrowsWorksNoMessage(){
        assertThrows(RuntimeException.class, temp1());
        assertThrows(RuntimeException.class, temp1());
    }
}
