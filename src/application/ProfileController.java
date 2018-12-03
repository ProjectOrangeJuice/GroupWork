package application;


import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Resource;


public class ProfileController {

	@FXML
	private HBox resourceImages;

	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private GridPane resourcePane;
	
	@FXML
	private TextField searchTextBox;
	
	@FXML
	private Controller loginController;

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
	 * @param event Passed when mouse event occurs
	 */
	@FXML
	public void loginAction(MouseEvent event) {
		changeScene(event, "/fxml/profileScene.fxml");
	}

	/**
	 * Called when logout link is clicked.
	 * @param event Passed when mouse event occurs
	 */
	@FXML
	public void logoutAction(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}

	@FXML
	 public void initialize() {

		//change resources size on profile page.
		scrollPane.setHvalue(0.5);
		for(Node resource : resourceImages.getChildren()) {
			((ImageView) resource).setFitWidth(300);
			((ImageView) resource).setFitHeight(500);
		}
		
		//load resources
		ArrayList<Resource> resources = Resource.loadDatabaseResources();
		
		for(Resource resource: resources) {
			
			//ImageView image = new ImageView();
			//image.setImage(resource.getThumbnail());
			//resourcePane.add(image, 0, 0);
			
		}
	
	 }

}
