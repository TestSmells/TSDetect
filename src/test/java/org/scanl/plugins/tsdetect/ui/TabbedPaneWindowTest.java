package org.scanl.plugins.tsdetect.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.openapi.project.Project;

import java.util.Objects;

public class TabbedPaneWindowTest extends BasePlatformTestCase {
    TabbedPaneWindow testPane;
    Project tempProj;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        tempProj = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        testPane = new TabbedPaneWindow();
    }

    public void testCreation(){
        assertNotNull(testPane);
    }

    public void testSetDistTable(){
        
    }
}
