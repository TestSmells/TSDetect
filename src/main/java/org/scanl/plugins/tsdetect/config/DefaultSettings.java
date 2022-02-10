package org.scanl.plugins.tsdetect.config;

import com.intellij.openapi.application.ApplicationManager;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the default plugin settings, to be used if there is a problem with both the app-level and project-level configs.
 */
public class DefaultSettings {
    public Map<String, Boolean> settings = new HashMap<>();

    public DefaultSettings() {
        for (SmellType smell : SmellType.values()) {
            settings.put(smell.toString(), true);
        }
    }

    public static DefaultSettings getInstance() {
        return ApplicationManager.getApplication().getService(DefaultSettings.class);
    }
}
