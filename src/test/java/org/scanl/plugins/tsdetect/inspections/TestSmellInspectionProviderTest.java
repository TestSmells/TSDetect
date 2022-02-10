package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import static org.junit.Assert.*;

public class TestSmellInspectionProviderTest extends LightJavaCodeInsightFixtureTestCase {
	private TestSmellInspectionProvider provider;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		provider = new TestSmellInspectionProvider();
	}

	public void testGetInspectionClasses(){
		Class[] expectedClasses = new Class[]{
				EmptyMethodInspection.class,
				GeneralFixtureInspection.class,
				LazyTestInspection.class
		};
		Class<? extends LocalInspectionTool> @NotNull [] actualClasses = provider.getInspectionClasses();
		assertArrayEquals(expectedClasses, actualClasses);
	}
}
