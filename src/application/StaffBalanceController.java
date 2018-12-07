package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import model.User;

public class StaffBalanceController {

	
	@FXML
	private TextField accountUser;
	
	@FXML
	private TextField accountValue;
	
	
	@FXML
	void accountAdd(ActionEvent event) {
		String username = accountUser.getText();
		float balance = Float.valueOf(accountValue.getText());
		System.out.println("Username is " +username+" balance to add is "+balance);
		if(balance > 0.1 && balance < 100) {
			if(User.addBalance(username,balance)) {
				//success
				alertDone("Added balance");
			}else {
				alertDone("Unable to add balance!");
			}
			
		}else {
			alertDone("Values out of range!");
		}
	}
	private void alertDone(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	

	
}
