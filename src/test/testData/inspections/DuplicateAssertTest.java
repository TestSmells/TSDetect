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
    public void hasSameAssertSameMessage(){
        fail("message");
        fail("message");
    }

    @Test
    public void hasSameAssertDifferentMessage(){
        fail("message1");
        fail("message2");
    }

    @Test
    public void hasSameMessageDifferentAssert(){
        fail("message");
        assertTrue(false,"message");
    }

    @Test
    public void hasDifferentMessageDifferentAssert(){
        fail("message1");
        assertTrue(false,"message2");
    }

}
