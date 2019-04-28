package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.ReadingList;
import model.Resource;

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
		System.out.println("The bit that is important..");
		ReadingList myList = ReadingList.myList(ScreenManager.getCurrentUser().getUsername());
		for(Resource r : myList.getResources()) {
		
			String name = r.getResources().get(0).getTitle();
			System.out.println("-"+name);
			//yourList.getChildren().add(new Text(name));
			
		}
		
		
	 }


}
