package org.scanl.plugins.tsdetect.config.project;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Represents the project-level plugin settings.
 * By default, these settings get saved to an xml file in the .idea/ directory at the project root directory.
 */
@State(
        name = "org.scanl.plugins.tsdetect.config.project.projectSettingsState",
        storages = @Storage("TSDetectPlugin.xml")
)
public class ProjectSettingsState implements PersistentStateComponent<ProjectSettingsState> {

    private static final Logger logger = LogManager.getLogger(ProjectSettingsState.class);

    public Map<String, Boolean> settings = new HashMap<>();
    private static DataContext dataContext;

    static {
        try {
            dataContext = DataManager.getInstance().getDataContextFromFocusAsync().blockingGet(10000);
        } catch (TimeoutException e) {
            logger.error(e);
        } catch (ExecutionException e) {
            logger.error(e);
        }
    }

    private static ProjectSettingsState projectSettingsState = Objects.requireNonNull(Objects.requireNonNull(dataContext).getData(CommonDataKeys.PROJECT)).getService(ProjectSettingsState.class);
    public static ProjectSettingsState getInstance() {
        return projectSettingsState;
    }

    @Nullable
    @Override
    public ProjectSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ProjectSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
