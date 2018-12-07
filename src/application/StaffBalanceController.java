package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import model.User;

/**
 * Controller for StaffBalance view.
 * @author Oliver Harris.
 *
 */
public class StaffBalanceController {

	private final double MIN_VALUE = 0.1;
	private final double MAX_VALUE = 500;
	
	@FXML
	private TextField accountUser;
	
	@FXML
	private TextField accountValue;
	
	
	/**
	 * Add money to an account action.
	 * @param event Button that was clicked.
	 */
	@FXML
	void accountAdd(ActionEvent event) {
		String username = accountUser.getText();
		float balance = Float.valueOf(accountValue.getText());
		if(balance > MIN_VALUE && balance < MAX_VALUE) {
			if(User.addBalance(username,balance)) {
				alertDone("Added balance");
			}else {
				alertDone("Unable to add balance!");
			}
			
		}else {
			alertDone("Values out of range!");
		}
		
	}
	/**
	 * Generate a popup.
	 * @param text The text to be displayed.
	 */
	private void alertDone(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	

	
}
