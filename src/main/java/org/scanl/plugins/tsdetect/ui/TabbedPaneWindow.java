package org.scanl.plugins.tsdetect.ui;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.util.*;

/**
 * The Tabbed Pane Window
 */
public class TabbedPaneWindow {
	private JPanel inspectionPanel; //the main popout panel
	private JTabbedPane detailsPanels; //the way to nicely get the tabbed pain to switch between

	//Smell Distrubtion UI Elements
	private JPanel smellDistribution;
	private JTable smellTable;
	private JButton smellDistributionButton;
	private IdentifierTableModel data;

	public TabbedPaneWindow() {
		Project project = ProjectManager.getInstance().getOpenProjects()[0];

		EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener() {
			@Override
			public void documentChanged(@NotNull DocumentEvent event) {
				Document document = event.getDocument();
				Project project = ProjectManager.getInstance().getOpenProjects()[0];
				VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
				if (virtualFile != null) {
					PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
					if (psiFile instanceof PsiJavaFile)
						setSmellDistributionTable(project);
				}
			}
		});

		smellDistributionButton.addActionListener(e -> setSmellDistributionTable(project));
	}

	/**
	 * Creates the smell distribution table
	 * @param project The Project that is currently opened
	 */
	private void setSmellDistributionTable(Project project){
		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
				JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)); //gets the files in the project

		data = new IdentifierTableModel();
		ArrayList<InspectionMethodModel> allMethods = new ArrayList<>();
		ArrayList<InspectionClassModel> allClasses = new ArrayList<>();
		for(VirtualFile vf : vFiles)
		{
			PsiFile psiFile = PsiManager.getInstance(project).findFile(vf); //converts into a PsiFile
			if(psiFile instanceof  PsiJavaFile) //determines if the PsiFile is a PsiJavaFile
			{
				PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
				PsiClass @NotNull [] classes = psiJavaFile.getClasses(); //gets the classes
				for(PsiClass psiClass: classes) {
					SmellVisitor sv = new SmellVisitor(); //creates the smell visitor
					psiFile.accept(sv); //visits the methods

					List<InspectionMethodModel> methods = sv.getSmellyMethods(); //gets all the smelly methods
					for(InspectionMethodModel method:methods){
						System.out.println(method.getName());
					}
					List<InspectionClassModel> smellyClasses = sv.getSmellyClasses();
					allMethods.addAll(methods);
					allClasses.addAll(smellyClasses);
				}
			}
		}
		HashMap<SmellType, List<InspectionMethodModel>> smellyMethods = new HashMap<>(); //hash to store smelly methods by smell
		HashMap<SmellType, List<InspectionClassModel>> smellyClasses = new HashMap<>(); //hash to store smelly classes by smell

		for(SmellType smellType: SmellType.values())
		{
			smellyMethods.put(smellType, getMethodBySmell(smellType, allMethods));
			smellyClasses.put(smellType, getClassesBySmell(smellType, allClasses));
		}

		data.constructSmellTable(smellyMethods, smellyClasses); //constructs the smell table

		smellTable.setModel(data); //sets the model to be the table and visible
		smellTable.setVisible(true);
	}

	/**
	 * Gets the methods for a matching smell
	 * @param smell The smell that is being searched for
	 * @param methods the list of all smelly methods
	 * @return a list of methods with a specific smell
	 */
	private List<InspectionMethodModel> getMethodBySmell(SmellType smell, List<InspectionMethodModel> methods){
		List<InspectionMethodModel> smellyMethods = new ArrayList<>();
		for(InspectionMethodModel m:methods){
			if(m.getSmellTypeList().contains(smell))
				smellyMethods.add(m);
		}
		return smellyMethods;
	}


	private List<InspectionClassModel> getClassesBySmell(SmellType smell, List<InspectionClassModel> smellyClasses){
		List<InspectionClassModel> classes = new ArrayList<>();
		for(InspectionClassModel smellyClass: smellyClasses){
			if(smellyClass.getSmellTypeList().contains(smell))
				classes.add(smellyClass);
		}
		return classes;
	}

	public JPanel getContent() {
		return inspectionPanel;
	}
}
