package de.milchreis.jcopy;

import de.milchreis.jcopy.ui.MainForm;

import java.io.File;
import java.io.IOException;

public class Main {

    public static AppModel model;

    public static void main(String[] args) throws IOException {

        boolean isHeadless = args.length > 0 && args[0].equals("--headless");
        boolean hasFile = (args.length == 1 && !isHeadless) || (args.length == 2 && isHeadless);

        if (hasFile) {
            model = new AppModel(new File(args[isHeadless ? 1 : 0]));
        } else {
            model = new AppModel();
        }

        if (isHeadless) {
            model.backup(new CliBackupTask());
            System.exit(0);

        } else {
            new MainForm(model).show();
        }
    }

}
