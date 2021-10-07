import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test has Redunant Print Statements, which means the
 * method has printing statements
 *
 * link: https://testsmells.org/pages/testsmellexamples.html#RedundantPrint
 */
public class PrintStatementTest {

	@Test
	public void PrintStatementTest()
	{
		int value1 = 0;
		int value2 = 2;
		assertNotEquals(value1, value2);
		System.out.println("Value 1 and Value 2 are not equal");
	}
}
