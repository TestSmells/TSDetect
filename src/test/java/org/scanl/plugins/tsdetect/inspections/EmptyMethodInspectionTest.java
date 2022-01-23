package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiType;
import org.junit.Before;
import org.junit.Test;
import org.scanl.plugins.tsdetect.model.SmellType;

import static org.junit.Assert.assertEquals;

public class EmptyMethodInspectionTest {

	EmptyMethodInspection inspection;

	@Before
	public void setup(){
		inspection = new EmptyMethodInspection();
	}

	@Test
	public void testDisplayName(){
		String expectedDisplayName = "Empty Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testShortName(){
		String expectedDisplayName = "ET";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testSmellType(){
		SmellType expectedSmellType = SmellType.EMPTY_METHOD;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}
}
