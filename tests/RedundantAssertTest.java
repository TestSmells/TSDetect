import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedundantAssertTest {

	@Test
	public void testTrue()
	{
		assertEquals(true, true);
	}

}
