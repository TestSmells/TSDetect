package org.scanl.plugins.tsdetect.listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Listens if a project is opened or changed
 */
public class ProjectListener implements ProjectManagerListener {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void projectOpened(@NotNull Project project) {
        logger.debug("Project opened: " + project.getName());
    }
}
