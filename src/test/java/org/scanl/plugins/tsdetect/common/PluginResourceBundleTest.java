package org.scanl.plugins.tsdetect.common;


import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PluginResourceBundleTest {

	@Test
	public void testInspectionMessage(){
		String expectedMessage = "Every method should have a reasoning behind it, including code, none should be empty";
		String message = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.emptytest.description");
		assertEquals(expectedMessage, message);
	}

	@Test
	public void testUIMessage(){
		String expectedMessage = "Run Analysis";
		String message = PluginResourceBundle.message(PluginResourceBundle.Type.UI, "button.analysis.name");
		assertEquals(expectedMessage, message);
	}
}
