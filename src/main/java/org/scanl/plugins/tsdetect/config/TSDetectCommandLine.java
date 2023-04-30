package org.scanl.plugins.tsdetect.config;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.application.ApplicationStarter;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.*;
import org.scanl.plugins.tsdetect.service.Analyzer;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.Date;
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

    private void RunAnalysis(String arg) {
       
        String projectName = arg.split("[/\\\\]")[arg.split("[/\\\\]").length-1];

        //prints startup info in blue
        System.out.println("\u001B[34m [INFO] \u001B[0m" + "Running TSDetect on " + projectName + "...");
        try {
            Project project = ProjectManager.getInstance().loadAndOpenProject(arg);
            assert project != null;
            ExecutionResult executionResult = Analyzer.getInstance().DetectTestSmells(project);
            ProjectManager.getInstance().closeAndDispose(project);
            executionResult.writeCSV(project);
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void main(@NotNull List<String> args) {
        //send project(s) to analyzer
        for (String arg : args.get(1).split(" ")) {
            RunAnalysis(arg);
        }
        System.exit(0);
    }
}
