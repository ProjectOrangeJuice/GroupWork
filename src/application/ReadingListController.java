package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import model.User;

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
	
	
	
	private void displayReadingList(ReadingList list) {
		otherList.getChildren().removeAll();
		otherList.getChildren().clear();
		
		Text listText = new Text(list.getName());
		listText.setFont(Font.font ("Verdana", 20));
		
		Button close = new Button("Close");
		close.setOnMouseClicked(new EventHandler<MouseEvent>(){

	          @Override
	          public void handle(MouseEvent arg0) {
	           setupReadingList();
	          }

	      });
		
		Button follow = new Button("Follow");
		
		follow.setOnMouseClicked(new EventHandler<MouseEvent>(){

	          @Override
	          public void handle(MouseEvent arg0) {
	           ReadingList.changeFollow(ScreenManager.getCurrentUser().getUsername(),
	        		   list.getName());
	           if(ReadingList.follows(ScreenManager.getCurrentUser().getUsername(),
	        		   list.getName())) {
	        	   follow.setText("Unfollow");
	           }else {
	        	   follow.setText("Follow");
	           }
	          }

	      });
		
		HBox h = new HBox();
		h.setSpacing(10);
		h.getChildren().addAll(close,follow);
		
		
		otherList.setSpacing(5);
		otherList.getChildren().addAll(listText,h);
		
		ArrayList<Resource> resources = list.getResources();
		HBox hbox = new HBox();
		for(int i = 0; i<resources.size();i++) {
			Resource r = resources.get(i);
			VBox item = new VBox();
			ImageView img = generateImageView(r.getThumbnail());
			img.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
			Text name = new Text(r.getTitle());
			item.getChildren().addAll(img,name);
			
			if(i != 0 && i % 3 == 0) {
				System.out.println("Adding to main");
				otherList.getChildren().add(hbox);
				hbox = new HBox();
				hbox.getChildren().add(item);
			}else {
				System.out.println("Adding to inner");
				hbox.getChildren().add(item);
			}
			
		}
		otherList.getChildren().add(hbox);
		
		
	}
	
	
	private void setupReadingList() {
		ArrayList<ReadingList> lists = ReadingList.databaseReader();
		otherList.getChildren().removeAll();
		otherList.getChildren().clear();
		Text listText = new Text("Reading lists");
		listText.setFont(Font.font ("Verdana", 20));
		otherList.setSpacing(2);
		otherList.getChildren().add(listText);
		ArrayList<VBox> vboxs = new ArrayList<VBox>();
		for( ReadingList l : lists) {
			VBox vbox = new VBox();
			vbox.setMinWidth(200);
			vbox.setSpacing(5);

			Text t = new Text(l.getName());
			t.setFont(Font.font(15));
			
			
			TextArea desc = new TextArea(l.getDescription());
			if(ScreenManager.getCurrentUser() instanceof User) {
				desc.setEditable(false);
				desc.setDisable(true);
			}else {
				desc.setEditable(true);
			}
			vbox.getChildren().addAll(t,desc);
			vbox.setOnMouseClicked(new EventHandler<MouseEvent>(){

		          @Override
		          public void handle(MouseEvent arg0) {
		           displayReadingList(l);
		            
		          }

		      });
			
			
			vboxs.add(vbox);
			System.out.println("Value created");
		}
		HBox hbox = new HBox();
		for(int i = 0; i<vboxs.size();i++) {
			if(i != 0 && i % 3 == 0) {
				System.out.println("Adding to main");
				otherList.getChildren().add(hbox);
				hbox = new HBox();
				hbox.getChildren().add(vboxs.get(i));
			}else {
				System.out.println("Adding to inner");
				hbox.getChildren().add(vboxs.get(i));
			}
			
		}
		otherList.getChildren().add(hbox);
		
		
		
		
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
