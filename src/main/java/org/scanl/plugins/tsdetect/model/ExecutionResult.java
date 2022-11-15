package org.scanl.plugins.tsdetect.model;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectUtil;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExecutionResult {
    private LocalDateTime executionStartTimestamp;
    private LocalDateTime executionEndTimestamp;
    private List<InspectionMethodModel> allMethods;
    private List<InspectionClassModel> allClasses;

    public ExecutionResult(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods, LocalDateTime executionStartTimestamp, LocalDateTime executionEndTimestamp) {
        this.allClasses = allClasses;
        this.allMethods = allMethods;
        this.executionStartTimestamp = executionStartTimestamp;
        this.executionEndTimestamp = executionEndTimestamp;
    }

    private LocalDateTime convertToUtc(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public List<InspectionMethodModel> getAllMethods() {
        return allMethods;
    }

    public List<InspectionClassModel> getAllClasses() {
        return allClasses;
    }

    public LocalDateTime getExecutionTimestamp() {
        return executionEndTimestamp;
    }
    public String getExecutionTimestampString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return executionEndTimestamp.format(formatter);
    }

    public LocalDateTime getExecutionTimestampUTC() {
        return executionEndTimestamp.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public long getExecutionDurationInSeconds(){
        return ChronoUnit.SECONDS.between(executionStartTimestamp, executionEndTimestamp);
    }

    public void GenerateReport(ReportType reportType){
        switch (reportType){
            case CSV_RAW: ReportCSV(); break;
            case PIE_SMELL_DISTRIBUTION:ReportPieChart();break;
        }
    }

    private void ReportCSV(){
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        writeCSV(project);
    }
    private void ReportPieChart(){}

    public void writeCSV(Project project) {
        int infectedClasses;
        int infectedMethods;
        //print the results
        try {
            FileWriter csv = new FileWriter(new File(project.getBasePath(), project.getName() + "-" + new Date() + ".csv"));
            csv.write(project.getName() + "\n");
            csv.write("Smell Type,Infected Classes,Infected Methods\n");

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
                csv.write(smellName + "," + infectedClasses + "," + infectedMethods + "\n");
            }
            csv.close();
            //notify headless via print
            System.out.println("\nCSV of results generated at " + project.getBasePath());
            //notify "Run Plugin" by intellij notification
            NotificationGroupManager.getInstance()
                    .getNotificationGroup("TSDetect")
                    .createNotification("CSV of results generated at " + project.getBasePath(), NotificationType.INFORMATION)
                    .notify(project);
        } catch (IOException e) {
            System.out.println("Unable to generate CSV due to: " + e.getMessage());
            NotificationGroupManager.getInstance()
                    .getNotificationGroup("TSDetect")
                    .createNotification("Unable to generate CSV due to: " + e.getMessage(), NotificationType.ERROR)
                    .notify(project);
        }
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

}
