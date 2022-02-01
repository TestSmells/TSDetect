package org.scanl.plugins.tsdetect.model;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class InspectionMethodModelTest extends BasePlatformTestCase {

    private InspectionClassModel inspectionClassModel;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        inspectionClassModel = new InspectionClassModel("testModel", null);
    }
}
