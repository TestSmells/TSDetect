import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Assertion Roulette, which means the
 * methods have multiple assertions without having any messages explaining why
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#AssertionRoulette
 */
public class AssertionRouletteTest {
	/**
	 * a fake assert method to ensure that incorrect methods are not being found
	 * @param y
	 */
	private void assertMethod(int y){
		int x = 0;
	}

	/**
	 *
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
		fail();
		fail();
	}

	/**
	 * message explaining that the same call is actually different
	 */
	@Test
	public void testFailureDifferentAssertMethods() {
		assertEquals("Test", "Test", "This is a message");
		assertEquals("Test", "Test", "This is a different message");
	}

	/**
	 * No message explaining what went wrong for rest of tests
	 */
	@Test
	public void assertTrueWorks(){
		assertTrue(false);
		assertTrue(true);
	}

	@Test
	public void assertFalseWorks(){
		assertFalse(false);
		assertFalse(true);
	}

	@Test
	public void assertNotNullWorks(){
		assertNotNull("something");
		assertNotNull("somethingElse");
	}

	@Test
	public void assertNullWorks(){
		assertNull("something");
		assertNull("somethingElse");
	}

	@Test
	public void assertArrayEqualsWorks(){
		int [] one = {0,1};
		int [] two = {0,1};
		int [] three = {1,1};
		assertArrayEquals(one,two);
		assertArrayEquals(one,three);

	}
	@Test
	public void assertEqualsWorks(){
		assertEquals("first","second");
		assertEquals("first","first");
	}
	@Test
	public void assertNotEqualsWorks(){
		assertNotEquals("first","second");
		assertNotEquals("first","first");
	}
	@Test
	public void assertNotSameWorks(){
		assertNotSame("first","second");
		assertNotSame("first","first");
	}
	@Test
	public void assertSameWorks(){
		assertSame("first","second");
		assertSame("first","first");
	}

	private Executable temp1 (){
		throw new RuntimeException();
	}
	private Executable temp2 (){
		throw new RuntimeException();
	}
	@Test
	public void assertThrowsWorks(){
		assertThrows(RuntimeException.class, temp1());
		assertThrows(RuntimeException.class, temp2());
	}
}
