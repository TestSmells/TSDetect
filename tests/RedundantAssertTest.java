import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Redundant Assertion, which means the
 * assert is comparing 2 same booleans
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#RedundantAssertion
 */
public class RedundantAssertTest {

	@Test
	public void testTrue()
	{
		assertEquals(true, true);
	}

}
