package application;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ReadingListController {
	
	@FXML
	private VBox yourList;
	
	@FXML
	private VBox otherList;
	
	@FXML
	public void initialize() {
		Text yours = new Text("Your list");
		Text theirs = new Text("Reading lists");
		
		yourList.getChildren().add(yours);
		otherList.getChildren().add(theirs);
	 }


}
