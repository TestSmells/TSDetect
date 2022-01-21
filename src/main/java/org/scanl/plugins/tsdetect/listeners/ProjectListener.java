package org.scanl.plugins.tsdetect.listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listens if a project is opened or changed
 */
public class ProjectListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        System.out.println("Project opened: " + project.getName());
    }
}