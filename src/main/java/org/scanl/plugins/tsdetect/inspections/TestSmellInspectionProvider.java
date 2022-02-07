package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The provider for the Test Smell Inspection, determines which inspection classes to run
 */
public class TestSmellInspectionProvider implements InspectionToolProvider {
	/**
	 * Query method for inspection tools provided by a plugin.
	 *
	 * @return classes that extend {@link InspectionProfileEntry}
	 */
	@Override
	public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
		return new Class[]{
				EmptyMethodInspection.class,
				GeneralFixtureInspection.class,
				RedundantPrintInspection.class
		};
	}
}
