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

import java.util.HashMap;
import java.util.Map;

@State(
        name = "org.scanl.plugins.tsdetect.config.project.projectSettingsState",
        storages = @Storage("TSDetectPlugin.xml")
)
public class ProjectSettingsState implements PersistentStateComponent<ProjectSettingsState> {

    public Map<String, Boolean> settings = new HashMap<>();

    public static ProjectSettingsState getInstance() {
        DataContext dataContext = null;
        try {
            dataContext = DataManager.getInstance().getDataContextFromFocusAsync().blockingGet(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataContext.getData(CommonDataKeys.PROJECT).getService(ProjectSettingsState.class);
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
