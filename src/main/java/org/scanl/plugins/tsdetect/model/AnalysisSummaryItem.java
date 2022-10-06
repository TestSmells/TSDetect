package org.scanl.plugins.tsdetect.model;

public class AnalysisSummaryItem {
    private String primaryHeader;
    private String primaryValue;
    private String primaryChangeValue;
    private AnalysisSummaryItem primaryChangeType;

    private String secondaryHeader;
    private String secondaryValue;
    private String secondaryChangeValue;
    private AnalysisSummaryItem secondaryChangeType;

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

    public AnalysisSummaryItem getPrimaryChangeType() {
        return primaryChangeType;
    }

    public void setPrimaryChangeType(AnalysisSummaryItem primaryChangeType) {
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

    public AnalysisSummaryItem getSecondaryChangeType() {
        return secondaryChangeType;
    }

    public void setSecondaryChangeType(AnalysisSummaryItem secondaryChangeType) {
        this.secondaryChangeType = secondaryChangeType;
    }


    public enum AnalysisSummaryChangeType {
        None,
        Increase,
        Decrease
    }
}


