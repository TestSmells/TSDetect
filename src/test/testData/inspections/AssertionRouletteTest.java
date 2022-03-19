import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Assertion Roulette, which means the
 * methods multiple assertions without having any messages explaining why
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#AssertionRoulette
 */
public class AssertionRouletteTest {

	/**
	 * No message explaining what went wrong
	 */
	@Test
	public void FailureAssertionRoulette01() {
		assertNotEquals("Test", "DifferentTest");
		assertNotEquals("This is A Fail", "Test");
	}
	private void assertMethod(int y){
		int x = 0;
	}
	@Test
	public void hasAssertTrueTwice(){
		assertTrue(false);
		assertTrue(true);

		this.assertMethod(1);
		this.assertMethod(2);

		assert(true!=true);
		assert(true!=false);

	}
	@Test
	public void hasFailTwiceWithoutMessages(){
		fail();
		fail();
	}
	/**
	 * message explaining what the same call is actually different
	 */
	@Test
	public void testFailureAssertionRoulette02() {
		assertEquals("Test", "Test", "This is a message");
		assertNotEquals("This is A Fail", "Test");
		assertEquals("Test", "Test");
	}
}
