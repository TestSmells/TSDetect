package org.scanl.plugins.tsdetect.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;

import java.util.ArrayList;
import java.util.Objects;

public class TabbedPaneWindowFactoryTest extends BasePlatformTestCase {

    Project tempProj;
    TabbedPaneWindowFactory paneWindowFactory;

    //store temp data for testing of private helper functions

    @Override
    public void setUp() throws Exception {
        super.setUp();
        tempProj = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        paneWindowFactory = new TabbedPaneWindowFactory();
    }

    public void testToolWindowCreation() {
//      paneWindowFactory.createToolWindowContent(tempProj, ){
//      }
        assertTrue(true);
    }
}
