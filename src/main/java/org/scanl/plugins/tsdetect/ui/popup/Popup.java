package org.scanl.plugins.tsdetect.ui.popup;

import javax.swing.*;

public class Popup{
    public static Boolean getPopup(){
        int choice = JOptionPane.showConfirmDialog(null, "Do you consent to sending anonymous test smell data for research purposes? (You can change this decision later in the TSDetect settings)");
        switch(choice){
            case JOptionPane.YES_OPTION:
                return true;
            case JOptionPane.NO_OPTION:
                return false;
            case JOptionPane.CLOSED_OPTION:
                break;
        }
        return false;
    }
    public static void main(String args[]){
        getPopup();
    }
}
