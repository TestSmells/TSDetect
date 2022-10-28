package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

public class Change {
    public static void setPrimaryChange(AnalysisSummaryItem item, int value, int valueBefore){
        if(value > valueBefore){
            item.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            item.setPrimaryChangeValue(String.valueOf(value- valueBefore));
        } else if (value < valueBefore){
            item.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            item.setPrimaryChangeValue(String.valueOf(valueBefore - value));
        } else {
            item.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            item.setPrimaryChangeValue("");
        }
    }
    public static void setSecondaryChange(AnalysisSummaryItem item, int value, int valueBefore){
        if( value > valueBefore){
            item.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            item.setSecondaryChangeValue(String.valueOf(value - valueBefore));
        } else if (value < valueBefore){
            item.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            item.setSecondaryChangeValue(String.valueOf(valueBefore -value));
        } else {
            item.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            item.setSecondaryChangeValue("");
        }

    }
}
