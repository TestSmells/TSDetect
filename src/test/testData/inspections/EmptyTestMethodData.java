import org.junit.jupiter.api.Test;

/**
 * This test has Empty Tests, which means the
 * methods have nothing in them
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#EmptyTest
 */
public class EmptyTestMethodData {

    @Test
    public void EmptyTest() {
        //this is an empty test
    }

    public void NotEmptyTest() {
        assertTrue(true);
    }
}