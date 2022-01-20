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
import org.scanl.plugins.tsdetect.SampleVisitor;
import org.scanl.plugins.tsdetect.model.ClassModel;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.Method;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class TabbedPaneWindow {
	private JPanel panel1;
	private JTabbedPane tabbedPane1;
	private JPanel tabbedAPane;
	private JPanel tabbedBPane;
	private JPanel listPane;
	private JTree treeMethod;
	private JButton treeMethodButton;
	private JTree treeSmell;
	private JButton treeSmellButton;
	private JTable table1;
	private JButton listButton;
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
						testList(project);
				}
			}
		});

		treeMethodButton.addActionListener(e -> testMethodTree(project));
		treeSmellButton.addActionListener(e -> testSmellTree(project));
		listButton.addActionListener(e -> testList(project));
	}

	private void testMethodTree(Project project){

		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));

		DefaultTreeModel model = (DefaultTreeModel)treeMethod.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		root.removeAllChildren();

		for (VirtualFile vf: vFiles) {
			parseMethodTree(root, vf, project);
		}

		model.reload(root);
	}

	private void testSmellTree(Project project){

		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));

		DefaultTreeModel model = (DefaultTreeModel)treeSmell.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		root.removeAllChildren();
		List<Method> methods = new ArrayList<>();
		for (VirtualFile vf: vFiles) {
			methods.addAll(getDetails(vf, project));
		}

		for(SmellType smellType:SmellType.values()){
			parseTree(root, methods, smellType);
		}

		model.reload(root);

	}


	private List<Method> getDetails(VirtualFile vf, Project project){
		PsiFile psiFile = PsiManager.getInstance(project).findFile(vf);
		List<Method> methods = new ArrayList<>();
		if(psiFile instanceof PsiJavaFile)
		{
			PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
			PsiClass @NotNull [] classes = psiJavaFile.getClasses();
			for(PsiClass psiClass: classes) {
				System.out.println(psiClass.getQualifiedName());
				SampleVisitor sv = new SampleVisitor();
				psiFile.accept(sv);
				methods = sv.getPsiMethods();
			}
		}
		return methods;
	}

	private void parseTree(DefaultMutableTreeNode tn, List<Method> methods, SmellType smellType){
		DefaultMutableTreeNode smellTypeNode = new DefaultMutableTreeNode(smellType);
		for(Method method:methods){
			if(method.getSmellTypeList().contains(smellType)){
				System.out.println(method.getName());
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(method.getName());
				smellTypeNode.add(methodNode);
			}
		}
		tn.add(smellTypeNode);
	}

	private void parseMethodTree(DefaultMutableTreeNode tn, VirtualFile vf, Project project) {
		DefaultMutableTreeNode vftn = new DefaultMutableTreeNode(vf.getName());

		if (vf.isDirectory()) {
			for (VirtualFile child : vf.getChildren()) {
				parseMethodTree(vftn, child, project);
			}
		}
		PsiFile psiFile = PsiManager.getInstance(project).findFile(vf);
		if(psiFile instanceof PsiJavaFile)
		{
			PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
			PsiClass @NotNull [] classes = psiJavaFile.getClasses();
			for(PsiClass psiClass: classes) {
				SampleVisitor sv = new SampleVisitor();
				psiFile.accept(sv);

				List<Method> methods = sv.getPsiMethods();
				for (Method m : methods) {
					DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(m.getName());
					for(SmellType smellType:m.getSmellTypeList())
					{
						DefaultMutableTreeNode smellNode = new DefaultMutableTreeNode(smellType);
						methodNode.add(smellNode);
					}
					vftn.add(methodNode);
				}
			}
		}

		tn.add(vftn);
	}

	private void testList(Project project){
		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));

		data = new IdentifierTableModel();
		ArrayList<Method> allMethods = new ArrayList<>();
		for(VirtualFile vf : vFiles)
		{
			PsiFile psiFile = PsiManager.getInstance(project).findFile(vf);
			if(psiFile instanceof  PsiJavaFile)
			{
				PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
				PsiClass @NotNull [] classes = psiJavaFile.getClasses();
				for(PsiClass psiClass: classes) {
					SampleVisitor sv = new SampleVisitor();
					psiFile.accept(sv);

					List<Method> methods = sv.getPsiMethods();
					allMethods.addAll(methods);
				}
			}
		}
		HashMap<SmellType, List<Method>> smellyMethods = new HashMap<>();
		HashMap<SmellType, List<ClassModel>> smellyClasses = new HashMap<>();

		for(SmellType smellType:Arrays.asList(SmellType.values()))
		{
			List<Method> methods = getMethodBySmell(smellType, allMethods);
			List<ClassModel> classes = getClassesBySmell(smellType, allMethods);
			smellyMethods.put(smellType, methods);
			smellyClasses.put(smellType, classes);
		}
		data.constructSmellTable(smellyMethods, smellyClasses);

		table1.setModel(data);
		table1.setVisible(true);
	}

	public List<Method> getMethodBySmell(SmellType smell, List<Method> methods){
		List<Method> smellyMethods = new ArrayList<>();
		for(Method m:methods){
			if(m.getSmellTypeList().contains(smell))
				smellyMethods.add(m);
		}
		return smellyMethods;
	}

	public List<ClassModel> getClassesBySmell(SmellType smell, List<Method> methods){
		List<Method> smellyMethods = getMethodBySmell(smell, methods);
		List<String> classList = new ArrayList<>();
		List<ClassModel> classes = new ArrayList<>();
		for(Method smellyMethod: smellyMethods){
			if(!classList.contains(smellyMethod.getClassName().getName())) {
				classes.add(smellyMethod.getClassName());
				classList.add(smellyMethod.getClassName().getName());
			}
		}
		return classes;
	}

	public JPanel getContent() {
		return panel1;
	}

	private void createUIComponents() {
		DefaultMutableTreeNode smellNode = new DefaultMutableTreeNode("SmellTypes");
		DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("Method");
		treeSmell = new JTree(smellNode);
		treeMethod = new JTree(methodNode);
	}
}

