package org.scanl.plugins.tsdetect.config.application;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;

import javax.swing.*;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent component;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SETTINGS.APPLICATION.NAME");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return component.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        component = new AppSettingsComponent();
        return component.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        for (String key : settings.settings.keySet()) {
            if (settings.settings.get(key) != component.getValue(key)) return true;
        }
        return false;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        for (String key : settings.settings.keySet()) {
            settings.settings.put(key, component.getValue(key));
        }
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        for (String key : settings.settings.keySet()) {
            component.setValue(key, settings.settings.get(key));
        }
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }

}
