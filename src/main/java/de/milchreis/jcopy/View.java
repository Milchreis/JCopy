package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class View extends Application implements Initializable {

	private Stage stage;
	private @FXML Button addElement;
	private @FXML Button removeElement;
	private @FXML Button removeAllElements;
	private @FXML Button selectDestination;
	private @FXML Button startBackup;
	private @FXML ListView<String> elementList;
	private @FXML ProgressBar progressbar;

	private AppModel model;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Language lang = Language.getInstance();

		try {
			model = new AppModel();
			
			if(model.getSources().size() > 0) {
				updateList();
			}
			
			if(model.getDestination() != null) {
				selectDestination.setText(lang.get("destination") + ": " + model.getDestination().getAbsolutePath());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addElement.setText(lang.get("items.add"));
		removeElement.setText(lang.get("items.removeItem"));
		removeAllElements.setText(lang.get("items.removeAll"));
		startBackup.setText(lang.get("start"));
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		URI uri = View.class.getClassLoader().getResource("View.fxml").toURI();
		URL url = uri.toURL();

		Parent root = FXMLLoader.<Parent>load(url);
		Scene scene = new Scene(root);

		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
		stage.setOnCloseRequest(e -> onClose());
		stage.setTitle(Language.getInstance().get("window.title"));
		stage.setScene(scene);
		stage.show();
	}
	
	public void onClose() {
	}
	
	public void onAddElement() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File file = directoryChooser.showDialog(stage);
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
		File file = directoryChooser.showDialog(stage);
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
