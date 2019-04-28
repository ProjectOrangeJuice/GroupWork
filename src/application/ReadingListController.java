package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ReadingList;
import model.Resource;

public class ReadingListController {

    private static final int RES_IMG_WIDTH = 150;
    private static final int RES_IMG_HEIGHT = 200;
	
	@FXML
	private VBox yourList;
	
	@FXML
	private VBox otherList;
	
	@FXML
	public void initialize() {
		setupReadingList();
		setupMyList();
		
		
	 }
	
	
	private void setupReadingList() {
		ArrayList<ReadingList> lists = ReadingList.databaseReader();
		otherList.getChildren().removeAll();
		otherList.getChildren().clear();
		Text listText = new Text("Reading lists");
		listText.setFont(Font.font ("Verdana", 20));
		otherList.setSpacing(2);
		otherList.getChildren().add(listText);
		for( ReadingList l : lists) {
			Text t = new Text(l.getName());
			otherList.getChildren().add(t);
		}
		
		
	}

	private void setupMyList() {
		 yourList.getChildren().removeAll();
	   		yourList.getChildren().clear();
		Text yours = new Text("Your list");
		yours.setFont(Font.font ("Verdana", 20));

		
		yourList.getChildren().add(yours);
		yourList.setSpacing(2);

		System.out.println("The bit that is important..");
		ReadingList myList = ReadingList.myList(ScreenManager.getCurrentUser().getUsername());
		if(myList != null) {
		for(Resource r : myList.getResources()) {
			VBox vbox = new VBox();
			vbox.setSpacing(2);
			String[] borrowed = r.hasBorrowed(ScreenManager.getCurrentUser().getUsername());
			Button remove = new Button("Remove");
			vbox.getChildren().add(remove);
			if(borrowed != null) {
				for (String b : borrowed) {
					vbox.getChildren().add(new Text("Borrowed: "+b));
				}
					
			}else {
				vbox.getChildren().add(new Text("Not borrowed before"));
			}
			
			
			ImageView image = generateImageView(r.getThumbnail());
			image.setOnMouseClicked(new EventHandler<MouseEvent>(){

		          @Override
		          public void handle(MouseEvent arg0) {
		            ScreenManager.setCurrentResource(r);
		            try {
		    			FXMLLoader fxmlLoader =
		    					new FXMLLoader(getClass().getResource("/fxml/copyScene.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Resource Information");
		                stage.setScene(new Scene(root1));
		                stage.show();
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		            
		          }

		      });
		    
			Text text = new Text(r.getTitle());

			
			remove.setOnMouseClicked(new EventHandler<MouseEvent>(){

		          @Override
		          public void handle(MouseEvent arg0) {
		           ReadingList.removeFromMyList(
		        		   ScreenManager.getCurrentUser().getUsername(),
		        		  r.getUniqueID());
		          
		   		setupMyList();
		          }
		      });
			
			
			HBox box = new HBox();
			box.setMinWidth(300);
			box.getChildren().addAll(image,vbox);
			
			yourList.getChildren().addAll(box,text);
			
		}
		}
	}
	
	
	

	private ImageView generateImageView(Image image) {
		ImageView resourceimage = new ImageView();
			resourceimage.setFitWidth(RES_IMG_WIDTH);
	        resourceimage.setFitHeight(RES_IMG_HEIGHT);
	        
	        resourceimage.setImage(image);
	        return resourceimage;
	}
	
}
