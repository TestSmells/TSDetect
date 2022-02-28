import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionHandlingData {

	@Test
	public void testCatch(){
		try {
			File tempFile = File.createTempFile("testtempfile-", ".txt");

			String b = "testy text for the testy file";
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
			bw.write(b);

			assertEquals("test", "test");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testThrow(){
		assertTrue(true);
		throw new ArrayIndexOutOfBoundsException();
	}

	@Test
	public void testNoThrowing()
	{
		int expectedOne = 3; //expected value being tested for
		int actualOne = 3; // actual value

		int expectedTwo = 4;
		int actualTwo = 4;

		assertEquals(actualOne,expectedOne);
		assertEquals(actualTwo,expectedTwo);

	}
}
