package org.scanl.plugins.tsdetect.config.project;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectSettingsComponent {

    private final JPanel panel;
    private final Map<String, JBCheckBox> checkBoxes = new HashMap<>();

    public ProjectSettingsComponent() {
        FormBuilder formBuilder = FormBuilder.createFormBuilder();

        for (SmellType smell : SmellType.values()) {
            String label = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"INSPECTION.SMELL." + smell.toString() + ".NAME.DISPLAY");
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

    public Map<String, Boolean> getValues() {
        return checkBoxes.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().isSelected()
        ));
    }

    public void setValue(String key, boolean value) {
        checkBoxes.get(key).setSelected(value);
    }

}
