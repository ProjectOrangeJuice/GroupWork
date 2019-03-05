package application;



import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
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
	  private RadioButton r1;
	  @FXML
	  private RadioButton r2;
	  @FXML
	  private RadioButton r3;
	  @FXML
	  private RadioButton r4;
	 
	
	@FXML
	public void submitReview(MouseEvent event) {
		int starValue = 0;
		
		System.out.println(star.getSelectedToggle());
		Toggle selected = star.getSelectedToggle();
		if(selected.equals(r1)) {
			System.out.println("r1..");
			starValue = 1;
		}else if(selected.equals(r2)) {
			starValue = 2;
		}else if(selected.equals(r3)) {
			starValue = 3;
		}else if(selected.equals(r4)) {
			starValue = 4;
		}else{
			starValue = 5;
		}
		
		String text = reviewBox.getText();
		
		Review.addReview(ScreenManager.getCurrentUser().getUsername(), ScreenManager.getCurrentResource().getUniqueID(),
				starValue, text);
		
		
		reviewBox.setText("Your review has been added!");
		reviewBox.setEditable(false);
		
	}
	
	
	@FXML
	public void initialize() {
		
		if(!Review.hasRead(ScreenManager.getCurrentUser().getUsername(), ScreenManager.getCurrentResource().getUniqueID()) ||
				Review.hasReviewed(ScreenManager.getCurrentUser().getUsername(), ScreenManager.getCurrentResource().getUniqueID())) {
			writeReviewBox.setVisible(false);
		}
		
	}
	
}
