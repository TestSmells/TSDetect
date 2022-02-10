package org.scanl.plugins.tsdetect.common;


import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

public class PluginResourceBundleTest extends LightJavaCodeInsightFixtureTestCase {

	public void testInspectionMessage(){
		String expectedMessage = "Every method should have a reasoning behind it, including code, none should be empty";
		String message = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL.EMPTY_TEST.DESCRIPTION");
		assertEquals(expectedMessage, message);
	}

	public void testUIMessage(){
		String expectedMessage = "Run Analysis";
		String message = PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME");
		assertEquals(expectedMessage, message);
	}
}
