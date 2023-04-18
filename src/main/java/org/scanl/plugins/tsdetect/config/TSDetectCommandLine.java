package org.scanl.plugins.tsdetect.config;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.application.ApplicationStarter;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.*;
import org.scanl.plugins.tsdetect.service.Analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        //Regex is super fun
        System.out.println("Arggggg! " + arg);
        String projectName = arg.split("[/\\\\]")[arg.split("[/\\\\]").length-1];

        System.out.println("Running TSDetect on " + projectName + "...");
        Project project = ProjectUtil.openOrImport(Path.of(arg), null , false);
        ExecutionResult executionResult = Analyzer.getInstance().DetectTestSmells(project);
        ProjectManager.getInstance().closeAndDispose(project);
        executionResult.writeCSV(project);
    }

    @Override
    public void main(@NotNull List<String> args) {
        //send project(s) to analyzer
        if (args.size() > 1) {
            String[] temp = args.get(1).split(" ");
            for (int i = 0; i < temp.length; i++) {
                RunAnalysis(temp[i]);
            }
        }
        System.exit(0);
    }
}
