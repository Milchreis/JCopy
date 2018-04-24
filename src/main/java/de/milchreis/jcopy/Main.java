package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static AppModel model;
	
	public static void main(String[] args) throws IOException {
		if(args.length > 0) {
			model = new AppModel(new File(args[0]));
		} else {
			model = new AppModel();
		}
		
		Application.launch(Main.class);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		URI uri = ViewController.class.getClassLoader().getResource("View.fxml").toURI();
		Parent root = FXMLLoader.<Parent>load(uri.toURL());
		Scene scene = new Scene(root);
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
		stage.setOnCloseRequest(e -> onClose());
		stage.setTitle(Language.getInstance().get("window.title"));
		stage.setScene(scene);
		stage.show();
	}
	
	public void onClose() {
	}
}
