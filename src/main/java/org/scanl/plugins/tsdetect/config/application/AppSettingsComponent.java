package org.scanl.plugins.tsdetect.config.application;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the UI for the plugin's application-level settings menu.
 */
public class AppSettingsComponent {

    private final JPanel panel;
    private final Map<String, JBCheckBox> checkBoxes = new HashMap<>();

    public AppSettingsComponent() {
        FormBuilder formBuilder = FormBuilder.createFormBuilder();

        // create a checkbox for each smell type, to enable/disable that inspection
        for (SmellType smell : SmellType.values()) {
            String label = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"INSPECTION.SMELL." + smell.toString() + ".NAME.DISPLAY");
            JBCheckBox checkBox = new JBCheckBox(label);
            checkBoxes.put(smell.toString(), checkBox);
            formBuilder.addComponent(checkBox);
        }
        JBCheckBox optIn = new JBCheckBox("Opt in to data collection");
        checkBoxes.put("OPT_IN", optIn);
        formBuilder.addComponent(optIn);

        panel = formBuilder
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JComponent getPreferredFocusedComponent() {
        if(checkBoxes.values().stream().findFirst().isPresent())
            return checkBoxes.values().stream().findFirst().get();
        return null;
    }

    public boolean getValue(String key) {
        return checkBoxes.get(key).isSelected();
    }

    public void setValue(String key, boolean value) {
        checkBoxes.get(key).setSelected(value);
    }

}
