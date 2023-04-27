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
        try {
            var projectSettings = ProjectSettingsState.getInstance().settings;
            if (projectSettings.containsKey(key)) return projectSettings.get(key);

            var appSettings = AppSettingsState.getInstance().settings;
            if (appSettings.containsKey(key)) return appSettings.get(key);

            return DefaultSettings.getInstance().settings.get(key);
        }catch (NoClassDefFoundError e) {
            //todo: this is a stop-gap to allow headless mode to be run, this should be refactored to allow for more natural collection of settings
            //System.out.println("Project settings not initialised, project is likely being run in Headless mode \n" +
            //        "If that is not the case please ensure project settings are being initialized properly");
            System.out.println(key);
            return true;
        }
    }
    /**
     * Helper method to check if the user has agreed or not to sharing their data
     *
     * Current implementation has only the application settings being checked,
     * This is due to the TSDetectPlugin.xml only loading the Application level settings
     * at the time of this comment being written.
    * */
    public static void popupCheck(){
        var appSettings = AppSettingsState.getInstance().settings;
        if(!appSettings.containsKey("OPT_IN") && appSettings.size() != 0){
            Boolean result = Popup.getPopup();
            if(result != null)
                appSettings.put("OPT_IN", result);
        }
    }


    public static String uuid() {
        return AppSettingsState.getInstance().getUuid();
    }

}
