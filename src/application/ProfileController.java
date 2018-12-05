package application;


import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private VBox vResourceBox;
	
	@FXML
	private TextField searchTextBox;
	
	@FXML
	private Controller loginController;
	
	@FXML
	private Label userLabel;
	
	@FXML
	private Label fullnameLabel;
	
	@FXML
	private Label ageLabel;
	
	@FXML
	private Label address1Label;
	
	@FXML
	private Label address2Label;
	
	@FXML
	private Label cityLabel;
	
	private int RES_IMG_WIDTH = 150;
	private int RES_IMG_HEIGHT = 250;
	
	private int COPY_IMG_WIDTH = 300;
	private int COPY_IMG_HEIGHT = 500;

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
	 * Called when logout link is clicked.
	 * @param event Passed when mouse event occurs
	 */
	@FXML
	public void logoutAction(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}
	
	/**
	 * Loads user information from Screen Manager class, so that
	 * it can be displayed within the UI.
	 */
	private void loadUserInformation() {
		
		//get all information in about user from ScreenManager class.
		String username = ScreenManager.currentUser.getUsername();
		String fullname = ScreenManager.currentUser.getFirstName() + " "
		+ ScreenManager.currentUser.getLastName();
		String address = ScreenManager.currentUser.getAddress();
		
		//change text in labels to appropriate user information.
		userLabel.setText(username);
		fullnameLabel.setText(fullnameLabel.getText() + " " + fullname);
		address1Label.setText(address1Label.getText() + " " + address);
	}
	
	/**
	 * Loads resource images from Resource class, so that they can
	 * be displayed within the UI.
	 */
	private void loadResourceImages() {
		
		//load resources
		ArrayList<Resource> resources = Resource.loadDatabaseResources();
		
		//for each resource in resources array
		for(int i = 0; i < resources.size(); i++) {
			
			//create new resource image to be added.
			ImageView image = new ImageView();
			image.setFitWidth(RES_IMG_WIDTH);
			image.setFitHeight(RES_IMG_HEIGHT);
			image.setImage(resources.get(i).getThumbnail());
			
			//get last image in last resource HBox.
			HBox latestHBox = (HBox) vResourceBox.getChildren().get(vResourceBox.getChildren().size() - 1);
			
			//if there is at least one image in last resource HBox
			if(!latestHBox.getChildren().isEmpty()) {
				//if the number of resources in resource HBox is more than
				//the width of the resource VBox / the width of a resource image
				if(latestHBox.getChildren().size() > (vResourceBox.getPrefWidth()
				- RES_IMG_WIDTH) / RES_IMG_WIDTH) {
					//create new HBox below last HBox
					HBox hResourceBox = new HBox();
					hResourceBox.setAlignment(Pos.TOP_CENTER);
					hResourceBox.setSpacing(5);
					//add image to new HBox
					hResourceBox.getChildren().add(image);
					vResourceBox.getChildren().add(hResourceBox);
				} else {
					latestHBox.getChildren().add(image); //add new image to last HBox
				}
			} else {
				latestHBox.getChildren().add(image); //add new image to last HBox
			}
			
		}
		
	}

	@FXML
	 public void initialize() {
		
		//change resources size on profile page.
		scrollPane.setHvalue(0.5);
		for(Node resource : resourceImages.getChildren()) {
			((ImageView) resource).setFitWidth(COPY_IMG_WIDTH);
			((ImageView) resource).setFitHeight(COPY_IMG_HEIGHT);
		}
				
		loadResourceImages();
		loadUserInformation();
	
	 }

}
