package org.scanl.plugins.tsdetect.config;

import org.scanl.plugins.tsdetect.config.application.AppSettingsState;
import org.scanl.plugins.tsdetect.config.project.ProjectSettingsState;

public class PluginSettings {

    public static boolean GetSetting(String key) {
        var projectSettings = ProjectSettingsState.getInstance().settings;
        if (projectSettings.containsKey(key)) return projectSettings.get(key);

        var appSettings = AppSettingsState.getInstance().settings;
        if (appSettings.containsKey(key)) return appSettings.get(key);

        return DefaultSettings.getInstance().settings.get(key);
    }

}
