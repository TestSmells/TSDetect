package org.scanl.plugins.tsdetect.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

}
