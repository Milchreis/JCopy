package de.milchreis.jcopy.ui;

import de.milchreis.jcopy.AppModel;
import de.milchreis.jcopy.BackupTask;
import de.milchreis.jcopy.ViewListener;
import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import de.milchreis.uibooster.model.ListElement;
import de.milchreis.uibooster.model.formelements.ButtonFormElement;
import de.milchreis.uibooster.model.formelements.ListFormElement;
import de.milchreis.uibooster.model.formelements.ProgressElement;

import java.io.File;
import java.util.ResourceBundle;

public class ViewController implements ViewListener {

    private final AppModel model;
    private ListFormElement list;
    private ProgressElement progress;
    private ButtonFormElement destinationButton;

    public ViewController(AppModel model) {
        this.model = model;
    }

    public void setList(ListFormElement list) {
        this.list = list;
    }

    public void setProgress(ProgressElement progress) {
        this.progress = progress;
    }

    public void setDestinationButton(ButtonFormElement destinationButton) {
        this.destinationButton = destinationButton;
    }

    public void onSelectDestination() {
        final File destination = new UiBooster().showDirectorySelection();
        model.setDestination(destination);
    }

    public void onAddDirectory() {
        final File directory = new UiBooster().showDirectorySelection();
        model.addSource(directory);
        list.addElement(new ListElement(directory.getAbsolutePath(), null));
    }


    public void onClearList() {
        model.getSources().clear();
        list.clearAll();
    }

    public void onRemoveDirectory() {
        final ListElement selectedElement = list.getValue();
        if (selectedElement == null) return;

        model.removeSource(new File(selectedElement.getTitle()));
        list.removeElement(selectedElement);
    }

    public void onStartBackup() {
        progress.setEnabled(true);
        model.setCurrentNumber(0);
        model.setNumberOfFiles(0);
        new Thread(new BackupTask(model, this)).start();
    }

    public String calcDestinationLabel(ResourceBundle bundle) {
        return model.getDestination() == null
                ? bundle.getString("destination.select")
                : bundle.getString("destination") + ": " + model.getDestination().getAbsolutePath();
    }


    @Override
    public void updateDisable(boolean isDisable) {
//        addElement.setDisable(disable);
//        removeElement.setDisable(disable);
//        removeAllElements.setDisable(disable);
//        selectDestination.setDisable(disable);
//        elementList.setDisable(disable);
//        startBackup.setDisable(disable);
    }

    @Override
    public void updateProgress(long currentNumber, long numberOfFiles) {
        final float progress = (float) currentNumber / numberOfFiles;
        final int roundedPercent = (int) (progress * 100);
        this.progress.setValue(roundedPercent);
    }

}
