package org.scanl.plugins.tsdetect.listeners;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

//
//  THIS CLASS LISTENS TO FILE SELECTION CHANGES IN THE EDITOR
//
public class EditorListener implements FileEditorManagerListener {
	private static final String TOOL_WINDOW_ID = "SCANL Sample Plugin";
	private final Project project;

	public EditorListener() { this.project = null; }
	public EditorListener(Project project) {
		this.project = project;
	}


//    @Override
//    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
//        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
//        if (psiFile instanceof PsiJavaFile) {
//            IdentifierListingToolWindow.refreshList(project, psiFile.getName());
//
////            ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
////            if (toolWindowManager.getToolWindow(TOOL_WINDOW_ID) == null) {
////                FileEditorManagerListener.super.fileOpened(source, file);
////                return;
////            }
////
////            final ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID); // this will give the toolWindow
////            final Content content = toolWindow.getContentManager().getContent(0); // this will give the first tab
////            final @NotNull JComponent myToolWindowPanel = content.getComponent(); // this will give the MyToolWindowPanel reference
////            myToolWindowPanel.invalidate();
//        }
//
//        FileEditorManagerListener.super.fileOpened(source, file);
//    }

//    @Override
//    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
//        IdentifierListingToolWindow.refreshList(project, "");
//        VirtualFile @NotNull [] selectedFiles = source.getSelectedFiles();
//        if (selectedFiles.length > 0) {
//            PsiFile psiSelectedFile = PsiManager.getInstance(project).findFile(selectedFiles[0]);
//            if (psiSelectedFile instanceof PsiJavaFile) {
//                IdentifierListingToolWindow.refreshList(project, selectedFiles[0].getName());
//            }
//
//        }
//        FileEditorManagerListener.super.fileClosed(source, file);
//        VirtualFile @NotNull [] s = source.getOpenFiles();
//    }


}
