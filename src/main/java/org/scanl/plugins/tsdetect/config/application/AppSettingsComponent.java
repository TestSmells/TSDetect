package org.scanl.plugins.tsdetect.config.application;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class AppSettingsComponent {

    private final JPanel panel;
    private final Map<String, JBCheckBox> checkBoxes = new HashMap<>();

    public AppSettingsComponent() {
        FormBuilder formBuilder = FormBuilder.createFormBuilder();

        for (SmellType smell : SmellType.values()) {
            String label = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"inspection.smell." + smell.toString() + ".name.display");
            JBCheckBox checkBox = new JBCheckBox(label);
            checkBoxes.put(smell.toString(), checkBox);
            formBuilder.addComponent(checkBox);
        }

        panel = formBuilder
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JComponent getPreferredFocusedComponent() {
        return checkBoxes.values().stream().findFirst().get();
    }

    public boolean getValue(String key) {
        return checkBoxes.get(key).isSelected();
    }

    public void setValue(String key, boolean value) {
        checkBoxes.get(key).setSelected(value);
    }

}
