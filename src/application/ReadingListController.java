package application;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.ReadingList;

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
		System.out.println(ScreenManager.getCurrentUser().getUsername());
		for(ReadingList r : ReadingList.myLists(ScreenManager.getCurrentUser().getUsername())) {
		
			String name = r.getResources().get(0).getTitle();
			System.out.println("-"+name);
			yourList.getChildren().add(new Text(name));
			
		}
		
		
	 }


}
