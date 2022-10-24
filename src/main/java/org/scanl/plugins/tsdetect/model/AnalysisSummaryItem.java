package org.scanl.plugins.tsdetect.model;

public class AnalysisSummaryItem {
    private String primaryHeader;
    private String primaryValue;
    private String primaryChangeValue;
    private AnalysisSummaryChangeType primaryChangeType;

    private String secondaryHeader;
    private String secondaryValue;
    private String secondaryChangeValue;
    private AnalysisSummaryChangeType secondaryChangeType;
    public AnalysisSummaryItem(){
        this.primaryValue = String.valueOf(0);
        this.primaryChangeValue = String.valueOf(0);
        this.secondaryValue = String.valueOf(0);
        this.secondaryChangeValue = String.valueOf(0);
    }

    public String getPrimaryHeader() {
        return primaryHeader;
    }

    public void setPrimaryHeader(String primaryHeader) {
        this.primaryHeader = primaryHeader;
    }

    public String getPrimaryValue() {
        return primaryValue;
    }

    public void setPrimaryValue(String primaryValue) {
        this.primaryValue = primaryValue;
    }

    public String getPrimaryChangeValue() {
        return primaryChangeValue;
    }

    public void setPrimaryChangeValue(String primaryChangeValue) {
        this.primaryChangeValue = primaryChangeValue;
    }

    public AnalysisSummaryChangeType getPrimaryChangeType() {
        return primaryChangeType;
    }

    public void setPrimaryChangeType(AnalysisSummaryChangeType primaryChangeType) {
        this.primaryChangeType = primaryChangeType;
    }

    public String getSecondaryHeader() {
        return secondaryHeader;
    }

    public void setSecondaryHeader(String secondaryHeader) {
        this.secondaryHeader = secondaryHeader;
    }

    public String getSecondaryValue() {
        return secondaryValue;
    }

    public void setSecondaryValue(String secondaryValue) {
        this.secondaryValue = secondaryValue;
    }

    public String getSecondaryChangeValue() {
        return secondaryChangeValue;
    }

    public void setSecondaryChangeValue(String secondaryChangeValue) {
        this.secondaryChangeValue = secondaryChangeValue;
    }

    public AnalysisSummaryChangeType getSecondaryChangeType() {
        return secondaryChangeType;
    }

    public void setSecondaryChangeType(AnalysisSummaryChangeType secondaryChangeType) {
        this.secondaryChangeType = secondaryChangeType;
    }


    public enum AnalysisSummaryChangeType {
        None,
        Increase,
        Decrease
    }
}


