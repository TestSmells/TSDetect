package org.scanl.plugins.tsdetect.config.application;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the application-level plugin settings.
 * By default, these settings get saved to an xml file in the IDE options directory:
 * https://www.jetbrains.com/help/idea/directories-used-by-the-ide-to-store-settings-caches-plugins-and-logs.html#config-directory
 */
@State(
        name = "org.scanl.plugins.tsdetect.config.application.appSettingsState",
        storages = @Storage("TSDetectPlugin.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public Map<String, Boolean> settings = new HashMap<>();
    public String uuid = UUID.randomUUID().toString();

    public AppSettingsState() {
        for (SmellType smell : SmellType.values()) {
            settings.put(smell.toString(), true);
        }
    }

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
