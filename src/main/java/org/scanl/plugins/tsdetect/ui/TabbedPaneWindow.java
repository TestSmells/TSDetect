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
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;
import java.util.List;

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

	private JPanel detectedSmells;
	private JTree smellTree;
	private JButton detectedSmellsButton;
	private JPanel smellDistributionChart;

	private final List<InspectionMethodModel> allMethods = new ArrayList<>();
	private final List<InspectionClassModel> allClasses = new ArrayList<>();


	/**
	 * Constructor for the tabbed pane window that contains the tables and data for one view of our plugin.
	 * Grabs the project and gets the PSI objects so we're able to search through the project,
	 * and also initializes the table functions and names.
	 */
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
					if (psiFile instanceof PsiJavaFile) {
						setAllDisplays(project);
					}
				}
			}
		});

		//set up button name and actions
		smellDistributionButton.addActionListener(e -> setAllDisplays(project));
		smellDistributionButton.setText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME"));
		smellDistributionButton.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.TOOLTIP"));
		//set the tab name and tooltip
		detailsPanels.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.TABLE.TAB.NAME"));
		detailsPanels.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.TABLE.TAB.TOOLTIP"));
		//sets tooltip for table
		smellTable.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.TABLE.DESCRIPTION"));

		detectedSmellsButton.addActionListener(e -> setAllDisplays(project));
		detectedSmellsButton.setText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME"));
		detectedSmellsButton.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.TOOLTIP"));
		//set the tab name and tooltip
		detectedSmells.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.DETECTED.TREE.TAB.NAME"));
		detectedSmells.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.DETECTED.TREE.TAB.TOOLTIP"));
		//sets tooltip for table
		smellTable.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.DETECTED.TREE.DESCRIPTION"));

	}

	protected void setAllDisplays(Project project){
		visitSmellDetection(project);
		setSmellDistributionTable();
		setSmellTree();
	}

	/**
	 * Creates the smell distribution table
	 */
	protected void setSmellDistributionTable(){
		IdentifierTableModel data = new IdentifierTableModel();

		HashMap<SmellType, List<InspectionMethodModel>> smellyMethods = new HashMap<>(); //hash to store smelly methods by smell
		HashMap<SmellType, List<InspectionClassModel>> smellyClasses = new HashMap<>(); //hash to store smelly classes by smell

		for(SmellType smellType: SmellType.values())
		{
			if (PluginSettings.GetSetting(smellType.toString())) {
				smellyMethods.put(smellType, getMethodBySmell(smellType));
				smellyClasses.put(smellType, getClassesBySmell(smellType));
			}
		}

		data.constructSmellTable(smellyMethods, smellyClasses); //constructs the smell table

		smellTable.setModel(data); //sets the model to be the table and visible
		smellTable.setVisible(true);

		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Map.Entry<SmellType, List<InspectionClassModel>> entry : smellyClasses.entrySet()) {
			int size = entry.getValue().size();
			if (size != 0) {
				dataset.setValue(entry.getKey().toString(), size);
			}
		}

		JFreeChart chart = ChartFactory.createPieChart(
				"",
				dataset,
				true,
				true,
				false
		);

		smellDistributionChart.setLayout(new java.awt.BorderLayout());
		smellDistributionChart.add(new ChartPanel(chart));
		smellDistributionChart.validate();
	}

	/**
	 * Creates the smell distribution tree by smell then tree then method
	 */
	protected void setSmellTree(){
		DefaultTreeModel model = (DefaultTreeModel)smellTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		root.removeAllChildren();

		for(SmellType smellType:SmellType.values()){
			DefaultMutableTreeNode smellTypeNode = new DefaultMutableTreeNode(
					PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,
							"INSPECTION.SMELL." + smellType.toString() + ".NAME.DISPLAY"));
			for(InspectionClassModel smellyClass:getClassesBySmell(smellType)){
				DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(smellyClass.getName());
				for(InspectionMethodModel method:getMethodBySmell(smellType)){
					if(method.getClassName().getName().equals(smellyClass.getName())){
						DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(method.getName());
						classNode.add(methodNode);
					}
				}
				smellTypeNode.add(classNode);
			}
			root.add(smellTypeNode);
		}

		model.reload(root);
	}

	/**
	 * Visits all of the smell detection stuff
	 * @param project the project that is open
	 */
	protected void visitSmellDetection(Project project){
		Collection<VirtualFile> vFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)); //gets the files in the project

		allMethods.clear();
		allClasses.clear();
		for(VirtualFile vf : vFiles)
		{
			PsiFile psiFile = PsiManager.getInstance(project).findFile(vf); //converts into a PsiFile
			if(psiFile instanceof  PsiJavaFile) //determines if the PsiFile is a PsiJavaFile
			{
				SmellVisitor sv = new SmellVisitor(); //creates the smell visitor
				psiFile.accept(sv); //visits the methods
				allMethods.addAll(sv.getSmellyMethods());
				allClasses.addAll(sv.getSmellyClasses());

			}
		}
	}

	/**
	 * Gets the methods for a matching smell
	 * @param smell The smell that is being searched for
	 * @return a list of methods with a specific smell
	 */
	protected List<InspectionMethodModel> getMethodBySmell(SmellType smell){
		List<InspectionMethodModel> smellyMethods = new ArrayList<>();
		for(InspectionMethodModel m:allMethods){
			if(m.getSmellTypeList().contains(smell))
				smellyMethods.add(m);
		}
		return smellyMethods;
	}


	/**
	 * Gets class that contains a matching smell
	 * @param smell The smell thats being searched for
	 * @return	a list of classes with the specific smell
	 */
	protected List<InspectionClassModel> getClassesBySmell(SmellType smell){
		List<InspectionClassModel> classes = new ArrayList<>();
		for(InspectionClassModel smellyClass: allClasses){
			if(smellyClass.getSmellTypeList().contains(smell))
				classes.add(smellyClass);
		}
		return classes;
	}

	/**
	 * Simple getter for the content within the panel
	 * @return returns the panel object
	 */
	public JPanel getContent() {
		return inspectionPanel;
	}

	private void createUIComponents() {
		DefaultMutableTreeNode smellNode = new DefaultMutableTreeNode("SmellTypes");
		smellTree = new Tree(smellNode);
	}
}
