package application;



import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Review;

public class WriteReviewController {
	  @FXML
	   private AnchorPane writeReviewBox;
	  
	  @FXML
	  private TextArea reviewBox;
	  
	  @FXML
	  private ToggleGroup star;
	 
	
	@FXML
	public void submitReview(MouseEvent event) {
		System.out.println(star.getSelectedToggle());
	}
	
	
	@FXML
	public void initialize() {
		
		if(!Review.hasRead(ScreenManager.getCurrentUser().getUsername(), ScreenManager.getCurrentResource().getUniqueID())) {
			writeReviewBox.setVisible(false);
		}
		
	}
	
}
