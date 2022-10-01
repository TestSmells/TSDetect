package org.scanl.plugins.tsdetect.model;

import org.scanl.plugins.tsdetect.common.PluginResourceBundle;

public class ReportItem {
    private String displayText;
    private ReportType reportType;

    public ReportItem(ReportType reportType){
        this.reportType = reportType;
        this.displayText =  PluginResourceBundle.message(PluginResourceBundle.Type.UI, "REPORT.TYPE." + reportType.toString() + ".NAME.DISPLAY");
    }

    public String getDisplayText() {
        return displayText;
    }

    public ReportType getReportType() {
        return reportType;
    }

    @Override
    public String toString() {
        return displayText;
    }
}

