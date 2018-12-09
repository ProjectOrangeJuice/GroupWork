package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Laptop;
import model.Person;
import model.Resource;
import model.User;


public class RecommendedController {
	
	@FXML
	VBox recommended;
	private final int RES_IMG_WIDTH = 150;
	private final int RES_IMG_HEIGHT = 250;
	/**
	 * Makes the image and resource text for the resource
	 * @param copyResource the copy of the resource 
	 * @param width width of image
	 * @param height height of image
	 * @return the image pane
	 */
	private StackPane createImage(Resource copyResource, int width, int height) {
		
		//create stackpane to add image layers to.
		StackPane imagePane = new StackPane();
		
		//create white backround just in case image is small.
		Rectangle background = new Rectangle();
		background.setWidth(width);
		background.setHeight(height);
		background.setFill(Color.WHITE);
		
		//create text containing resource information
		//this text only shows when mouse enters image.
		Text resourceText = new Text();
		resourceText.setFont(Font.font("Arial", 20));
		resourceText.setStyle("-fx-font-weight: bold");
		resourceText.setFill(Color.WHITE);
		resourceText.setText("ID: " + copyResource.getUniqueID() + "\n" +
		copyResource.getTitle() + "\n" + copyResource.getYear());
		resourceText.setVisible(false);
		resourceText.setTextAlignment(TextAlignment.CENTER);
		resourceText.setWrappingWidth(width);
		
		//create imageview containg resource image.
		ImageView image = new ImageView();
		image.setFitWidth(width);
		image.setFitHeight(height);
		image.setImage(copyResource.getThumbnail());
		
		//if image is of a laptop, keep aspect ratio.
		if(copyResource instanceof Laptop) {
			image.setPreserveRatio(true);
		}
		
		//make image as smooth as possible.
		image.setCache(true);
		image.setCacheHint(CacheHint.SCALE);
		image.setSmooth(true);
		
		//add black colour overlay
		//only shows when mouse is in image.
		Rectangle rect = new Rectangle();
		rect.setWidth(width);
		rect.setHeight(height);
		rect.setFill(Color.BLACK);
		rect.setOpacity(0.7);
		rect.setVisible(false);
		
		//add all elements to stack pane.
		imagePane.getChildren().add(background);
		imagePane.getChildren().add(image);
		imagePane.getChildren().add(new ImageView());
		imagePane.getChildren().add(rect);
		imagePane.getChildren().add(resourceText);
		
		//set id of imagePane to it's index so it can be accessed
		//within the event handler.
		imagePane.setId(String.valueOf(copyResource.getUniqueID()));
		
		return imagePane;
	}
	
	@FXML
	 public void initialize() {
		Person person = ScreenManager.getCurrentUser();
		if (person instanceof User) {
			User user = ((User) ScreenManager.getCurrentUser());
						
			try {
				ArrayList<Resource> recommendations = user.getRecommendations();
				for(Resource recommendedR : recommendations) {
					StackPane imagePane = createImage(recommendedR, RES_IMG_WIDTH, RES_IMG_HEIGHT);
						
					recommended.getChildren().add(imagePane);
					recommended.setSpacing(15);
					
				}
			} catch (SQLException e) {
				System.out.println("hello00000");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
	}
	
	
	}
