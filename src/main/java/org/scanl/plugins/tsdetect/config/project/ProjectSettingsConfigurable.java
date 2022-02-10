package org.scanl.plugins.tsdetect.config.project;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.DefaultSettings;

import javax.swing.*;

public class ProjectSettingsConfigurable implements Configurable {

    private ProjectSettingsComponent component;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SETTINGS.PROJECT.NAME");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return component.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        component = new ProjectSettingsComponent();
        return component.getPanel();
    }

    @Override
    public boolean isModified() {
        ProjectSettingsState settings = ProjectSettingsState.getInstance();
        var defaultSettings = DefaultSettings.getInstance().settings;
        for (String key : component.getValues().keySet()) {
            if (!settings.settings.containsKey(key)) return component.getValue(key) != defaultSettings.get(key);
            if (settings.settings.get(key) != component.getValue(key)) return true;
        }
        return false;
    }

    @Override
    public void apply() {
        ProjectSettingsState settings = ProjectSettingsState.getInstance();
        for (String key : component.getValues().keySet()) {
            settings.settings.put(key, component.getValue(key));
        }
    }

    @Override
    public void reset() {
        ProjectSettingsState settings = ProjectSettingsState.getInstance();
        for (String key : component.getValues().keySet()) {
            component.setValue(key, settings.settings.get(key));
        }
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }

}
