package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Review;

public class WriteReviewController {
	  @FXML
	   private AnchorPane writeReviewBox;
	
	@FXML
	public void submitReview(MouseEvent event) {
		
	}
	
	
	@FXML
	public void initialize() {
		
		if(!Review.hasRead(ScreenManager.getCurrentUser().getUsername(), ScreenManager.getCurrentResource().getUniqueID())) {
			writeReviewBox.setVisible(false);
		}
		
	}
	
}
