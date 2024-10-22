package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

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
				AssertionRouletteInspection.class,
				ConditionalTestLogicInspection.class,
				ConstructorInitializationInspection.class,
				DefaultTestInspection.class,
				DuplicateAssertInspection.class,
				EagerTestInspection.class,
				EmptyMethodInspection.class,
				ExceptionHandlingInspection.class,
				GeneralFixtureInspection.class,
				IgnoredTestInspection.class,
				LazyTestInspection.class,
				MagicNumberInspection.class,
				MysteryGuestInspection.class,
				RedundantAssertionInspection.class,
				RedundantPrintInspection.class,
				ResourceOptimismInspection.class,
				SensitiveEqualityInspection.class,
				SleepyTestInspection.class,
				UnknownTestInspection.class,
				VerboseTestInspection.class
		};
	}
}
