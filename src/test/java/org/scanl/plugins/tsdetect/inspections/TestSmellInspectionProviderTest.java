package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestSmellInspectionProviderTest {
	private TestSmellInspectionProvider provider;

	@Before
	public void setup(){
		provider = new TestSmellInspectionProvider();
	}

	@Test
	public void testGetInspectionClasses(){
		Class<? extends LocalInspectionTool> @NotNull [] expectedClasses = new Class[]{
				EmptyMethodInspection.class
		};
		Class<? extends LocalInspectionTool> @NotNull [] actualClasses = provider.getInspectionClasses();
		assertArrayEquals(expectedClasses, actualClasses);
	}
}
