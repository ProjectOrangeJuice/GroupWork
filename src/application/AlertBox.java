package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * AlertBox method
* @author Oliver Harris.
*
*/
public class AlertBox {
    
	/**
	 * Generate an information popup with the given message.
	 * @param text The text to be displayed.
	 */
	public static void showInfoAlert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	/**
	 * Generates an error message popup with the given message.
	 * @param text The message to be displayed by the alert box.
	 */
	public static void showErrorAlert(String text) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
