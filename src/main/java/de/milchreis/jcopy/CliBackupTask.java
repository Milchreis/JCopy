package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;

public class CliBackupTask implements BackupListener {

    private CliProgressbar progressBar;

    @Override
    public void onFilesCount(long numbers) {
        progressBar = new CliProgressbar(50, 0, 0, numbers);
    }

    @Override
    public void onFinish() {
        System.out.println("\r" + progressBar.print() + " done!");
    }

    @Override
    public void onFileProcessed(File curFile, File destFile) {
        if (curFile.isFile()) {
            progressBar.step();
            System.out.print("\r" + progressBar.print() + " " + curFile.getName());
        }
    }

    @Override
    public void onFileProcessError(File curFile, IOException e) {
        e.printStackTrace();
    }

}
