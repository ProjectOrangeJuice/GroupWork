package application;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileController {
	
	@FXML
	private VBox resourcesVbox;
	
	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML 
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			//create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Called when login button is clicked.
	 */
	@FXML
	public void loginAction(MouseEvent event) {
		changeScene(event, "/fxml/profileScene.fxml");
	}
	
	/**
	 * Called when log out link is clicked.
	 */
	@FXML
	public void logoutAction(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}
	
	@FXML
	 public void initialize() {
		
		for(Node resource : resourcesVbox.getChildren()) {
			((ImageView) resource).setFitWidth(300);
			((ImageView) resource).setFitHeight(500);
		}
		
		
		
	 }    

}
