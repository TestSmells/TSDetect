package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.scanl.plugins.tsdetect.model.SmellType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmptyMethodInspectionTest {

	EmptyMethodInspection inspection;

	@Mock
	PsiMethod emptyMethodTest;

	@Mock
	PsiCodeBlock methodBody;

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

	@Test
	public void testHasSmell(){
		when(emptyMethodTest.getBody()).thenReturn(methodBody);
		when(methodBody.isEmpty()).thenReturn(true);
		assertTrue(inspection.hasSmell(emptyMethodTest));
	}

	@Test
	public void testHasNoSmell(){
		when(emptyMethodTest.getBody()).thenReturn(methodBody);
		when(methodBody.isEmpty()).thenReturn(false);
		assertFalse(inspection.hasSmell(emptyMethodTest));
	}
}
