package org.scanl.plugins.tsdetect.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates the Tabbed Pane Window
 */
public class TabbedPaneWindowFactory implements ToolWindowFactory {
	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		SmellTabbedPaneWindow myToolWindow = new SmellTabbedPaneWindow();
		//TabbedPaneWindow myToolWindow = new TabbedPaneWindow();
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindow.getContent(), "", false);
		toolWindow.getContentManager().addContent(content);
	}
}
