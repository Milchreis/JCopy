package de.milchreis.jcopy;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class FileBrowserOpener {

    public static void open(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } else {
                System.out.println("desktop is not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
