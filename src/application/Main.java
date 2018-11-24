package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * @author Kane Miles
 * @version 1
 */
public class Main extends Application {
	
	//stores current scene (updated during each scene change).
	private static Scene currentScene;
	
	//stores minimum width and height of stage.
	private final int MIN_WIDTH = 600;
	private final int MIN_HEIGHT = 400;
	
	/**
	 * Initialises stage object and shows login scene on start-up.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//create initial scene object.
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginScene.fxml"));
			currentScene = new Scene(root);
			currentScene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			//set stage attributes.
			primaryStage.setScene(currentScene); //set scene within stage
			primaryStage.setMinWidth(MIN_WIDTH); //set minimum width of stage
			primaryStage.setMinHeight(MIN_HEIGHT); //set minimum height of stage
			primaryStage.setMaximized(true); //make stage full screen
			primaryStage.show(); //show stage to user
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML 
	 */
	@FXML
	public void changeScene(String sceneFXML) {
		try {
			//create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			currentScene.setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Called when login button is clicked.
	 */
	@FXML
	public void loginAction() {
		changeScene("/fxml/profileScene.fxml");
	}
	
	/**
	 * Called when log out link is clicked.
	 */
	@FXML
	public void logoutAction() {
		changeScene("/fxml/loginScene.fxml");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
