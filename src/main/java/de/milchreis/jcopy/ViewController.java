package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;

public class ViewController {

	private @FXML Button addElement;
	private @FXML Button removeElement;
	private @FXML Button removeAllElements;
	private @FXML Button selectDestination;
	private @FXML Button startBackup;
	private @FXML ListView<String> elementList;
	private @FXML ProgressBar progressbar;

	private AppModel model = Main.model;
	
	@FXML
	public void initialize() {
		Language lang = Language.getInstance();

		if(model.getSources().size() > 0) {
			updateList();
		}
		
		if(model.getDestination() != null) {
			selectDestination.setText(lang.get("destination") + ": " + model.getDestination().getAbsolutePath());
		}
	}
	
	public void onAddElement() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File file = directoryChooser.showDialog(addElement.getScene().getWindow());
		if (file != null) {
			model.addSource(file);
			elementList.getItems().add(file.getAbsolutePath());
		}
	}
	
	public void onRemoveElement() {
		int index = elementList.getSelectionModel().getSelectedIndex();
		model.getSources().remove(index);
		elementList.getItems().remove(index);
	}
	
	public void onRemoveAllElements() {
		model.getSources().clear();
		elementList.getItems().clear();
	}
	
	public void onSelectDestination() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File file = directoryChooser.showDialog(addElement.getScene().getWindow());
		if (file != null) {
			String path = file.getAbsolutePath();
			model.setDestination(file);
			selectDestination.setText(Language.getInstance().get("destination") + ": " + path);
		}
	}
	
	public void onStartBackup() throws IOException {
		
		updateDisable(true);
		model.setCurrentNumber(0);
		model.setNumberOfFiles(0);
		
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws IOException {
		        
		    	model.backup(new BackupListener() {
					
					@Override
					public void onFinish() {
						System.out.println("Finished");
						updateDisable(false);
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
						updateDisable(false);
					}
				});
		    	return null;
		    }
		};
		
		progressbar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}
	
	
	private void updateList() {
		List<String> sources = model.getSources()
				.stream()
				.map(f -> f.getAbsolutePath())
				.collect(Collectors.toList());
		
		elementList.setItems(FXCollections.observableArrayList(sources));
	}
	
	private void updateDisable(boolean disable) {
		addElement.setDisable(disable);
		removeElement.setDisable(disable);
		removeAllElements.setDisable(disable);
		selectDestination.setDisable(disable);
		elementList.setDisable(disable);
		startBackup.setDisable(disable);
	}

}
