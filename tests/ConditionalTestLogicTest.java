import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Conditional Test Logic, which means the
 * methods have if statements, switch statements, while loops, for loops, or for each loops
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#ConditionalTestLogic
 */
public class ConditionalTestLogicTest {

	@Test
	public void testConditionalIf()
	{
		if(3 == 3)
		{
			assertEquals(3, 3);
		}
	}

	@Test
	public void testConditionalSwitch()
	{
		String animal = "cat";
		switch(animal){
			case "cat":
				assertEquals("cat", animal);
			case "dog":
			case "pig":
			case "platypus":
			default:
				assertNotEquals("cat", animal);
		}
	}

	@Test
	public void testConditionalWhile()
	{
		int x = 0;
		int y = 0;
		while (x<5)
		{
			assertEquals(x, y);
			x++;
			y++;
		}
	}

	@Test
	public void testConditionalFor()
	{
		int x = 0;
		for(int y = 0; y<5; y++)
		{
			assertEquals(x, y);
			x++;
		}
	}

	@Test
	public void testConditionalForEach()
	{
		int[] x = {0,1,2,3,4};
		int y = 0;
		for (int i: x) {
			assertEquals(i, y);
			y++;
		}
	}
}
