package org.scanl.plugins.tsdetect.config;

import org.scanl.plugins.tsdetect.config.application.AppSettingsState;
import org.scanl.plugins.tsdetect.config.project.ProjectSettingsState;
import org.scanl.plugins.tsdetect.ui.popup.Popup;

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

        var defaultSettings = DefaultSettings.getInstance().settings;
        return defaultSettings.get(key);
    }
    /**
     * Helper method to check if the user has agreed or not to sharing their data
     *
     * Current implementation has only the application settings being checked,
     * This is due to the TSDetectPlugin.xml only loading the Application level settings
     * at the time of this comment being written.
    * */
    public static void PopupCheck(){
        var projectSettings = ProjectSettingsState.getInstance().settings;
//        if(!projectSettings.containsKey("OPT_IN") && projectSettings.size() != 0){
//            Popup p = new Popup();
//            System.out.println("Project settings popup");
//            Boolean result = p.getPopup();
//            projectSettings.put("OPT_IN", result);
//        }
        var appSettings = AppSettingsState.getInstance().settings;
        if(!appSettings.containsKey("OPT_IN") && appSettings.size() != 0){
            Popup p = new Popup();
            System.out.println("App settings popup");
            Boolean result = p.getPopup();
            appSettings.put("OPT_IN", result);
        }
        var defaultSettings = DefaultSettings.getInstance().settings;
//        if(!defaultSettings.containsKey("OPT_IN")){
//            Popup p = new Popup();
//            System.out.println("Default settings popup");
//            Boolean result = p.getPopup();
//            defaultSettings.put("OPT_IN", result);
//        }
    }

}
