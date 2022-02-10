package org.scanl.plugins.tsdetect.config;

import org.scanl.plugins.tsdetect.config.application.AppSettingsState;
import org.scanl.plugins.tsdetect.config.project.ProjectSettingsState;

public class PluginSettings {

    /**
     * Convenient helper method for getting the correct value for any plugin config option.
     *
     * Project-level settings take priority if they exist. Otherwise, application-level settings are used.
     * If for some reason neither set of configs contains the desired setting, then the default settings provider is used.
     *
     * @param key The name of the config setting to retrieve.
     * @return The strongest-scoped config value.
     */
    public static boolean GetSetting(String key) {
        var projectSettings = ProjectSettingsState.getInstance().settings;
        if (projectSettings.containsKey(key)) return projectSettings.get(key);

        var appSettings = AppSettingsState.getInstance().settings;
        if (appSettings.containsKey(key)) return appSettings.get(key);

        return DefaultSettings.getInstance().settings.get(key);
    }

}
