package org.scanl.plugins.tsdetect.common;


import javax.swing.*;
import java.net.URL;

public class Util {

    public static Icon GetLoadingIcon(){
        URL url = Util.class.getResource("/images/loading.gif");
        return new ImageIcon(url);
    }

}
