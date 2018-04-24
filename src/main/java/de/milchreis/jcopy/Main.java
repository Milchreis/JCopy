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

		boolean isHeadless = args.length > 0 && args[0].equals("--headless");
		boolean hasFile = (args.length == 1 && !isHeadless) || (args.length == 2 && isHeadless);
		
		if(hasFile) {
			model = new AppModel(new File(args[isHeadless ? 1 : 0]));
		} else {
			model = new AppModel();
		}
		
		if(isHeadless) {
			model.backup(null);
			System.exit(0);
		
		} else {
			Application.launch(Main.class);
		}
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
