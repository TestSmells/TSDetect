package org.scanl.plugins.tsdetect.config;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.application.ApplicationStarter;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.*;
import org.scanl.plugins.tsdetect.service.Analyzer;

import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;
import com.intellij.openapi.project.Project;


public class TSDetectCommandLine implements ApplicationStarter {
    public final String TSDETECT_CMD = "tsdetect";

    @Override
    public String getCommandName() {
        return TSDETECT_CMD;
    }

    @Override
    public boolean isHeadless() {
        return true;
    }

    protected List<InspectionMethodModel> getMethodBySmell(SmellType smell, List<InspectionMethodModel> allMethods) {
        List<InspectionMethodModel> smellyMethods = new ArrayList<>();
        for (InspectionMethodModel m : allMethods) {
            if (m.getSmellTypeList().contains(smell))
                smellyMethods.add(m);
        }
        return smellyMethods;
    }

    protected List<InspectionClassModel> getClassesBySmell(SmellType smell, List<InspectionClassModel> allClasses) {
        List<InspectionClassModel> classes = new ArrayList<>();
        for (InspectionClassModel smellyClass : allClasses) {
            if (smellyClass.getSmellTypeList().contains(smell))
                classes.add(smellyClass);
        }
        return classes;
    }

    public void printSmellyData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods, String projectName) {
        int infectedClasses;
        int infectedMethods;
        //print the results
        System.out.println(projectName + " results:");
        System.out.println("--------------------------------------------------");
        System.out.println(" Smell Type | Infected Classes | Infected Methods ");
        System.out.println("--------------------------------------------------");
        //get smell type
        for (SmellType smellType : SmellType.values()) {
            String smellName = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL." + smellType.toString() + ".NAME.DISPLAY");
            infectedClasses = 0;
            infectedMethods = 0;
            //get infected classes
            for (InspectionClassModel smellyClass : getClassesBySmell(smellType, allClasses)) {
                infectedClasses++;
                //get infected methods
                for (InspectionMethodModel method : getMethodBySmell(smellType, allMethods)) {
                    if (method.getClassName().getName().equals(smellyClass.getName())) {
                        infectedMethods++;
                    }
                }
            }
            System.out.format("%2s | %d | %d %n", smellName, infectedClasses, infectedMethods);
        }
        System.out.println("--------------------------------------------------\n");
    }

    private void RunAnalysis(String arg) {
        String projectName = arg.split("/")[arg.split("/").length-1];

        System.out.println("Running TSDetect on " + projectName + "...");
        Project project = ProjectUtil.openOrImport(Path.of(arg), null , false);
        ExecutionResult executionResult = Analyzer.getInstance().DetectTestSmells(project);
        ProjectManager.getInstance().closeAndDispose(project);

        printSmellyData(executionResult.getAllClasses(), executionResult.getAllMethods(), projectName);
    }

    @Override
    public void main(@NotNull List<String> args) {
        //send project(s) to analyzer
        if (args.size() == 2) {
            RunAnalysis(args.get(1));
        } else {
            for (int i = 1; i < args.size(); i++) {
                RunAnalysis(args.get(i));
            }
        }
        System.exit(0);
    }
}
