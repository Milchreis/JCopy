package de.milchreis.jcopy;

import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;

public class BackupTask extends Task {

    private AppModel model;
    private ViewListener viewListener;

    public BackupTask(AppModel model, ViewListener viewListener) {
        this.model = model;
        this.viewListener = viewListener;
    }

    @Override
    protected Object call() throws Exception {

        model.backup(new BackupListener() {

            @Override
            public void onFinish() {
                System.out.println("Finished");
                viewListener.updateDisable(false);
            }

            @Override
            public void onFilesCount(long numbers) {
                model.setNumberOfFiles(numbers);
            }

            @Override
            public void onFileProcessed(File curFile, File destFile) {
                model.setCurrentNumber(model.getCurrentNumber() + 1);
                updateProgress(model.getCurrentNumber(), model.getNumberOfFiles());
            }

            @Override
            public void onFileProcessError(File curFile, IOException e) {
                e.printStackTrace();
                viewListener.updateDisable(false);
            }
        });

        return null;
    }
}
