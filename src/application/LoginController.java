package application;


import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Librarian;
import model.Person;
import model.User;

public class LoginController {
	
	@FXML
	private TextField usernameTextBox;
	
	@FXML
	private Label errorLabel;
	
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
		
		Person User1 = Person.loadPerson(usernameTextBox.getText());
		
		if(User1 != null) {
			ScreenManager.setCurrentUser((Person) User1);
			if(User1 instanceof User) {
				changeScene(event, "/fxml/profileScene.fxml");
			} else {
				changeScene(event, "/fxml/profileScene.fxml");
			}
		} else {
			errorLabel.setText("User cannot be found.");
		}
		
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
		
	 }    

}
