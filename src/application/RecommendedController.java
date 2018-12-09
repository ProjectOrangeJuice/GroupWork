package application;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Person;
import model.Resource;
import model.User;


public class RecommendedController {
	@FXML
	HBox recommended;
	
	
	
	@FXML
	 public void initialize() {
		Person person = ScreenManager.getCurrentUser();
		if (person instanceof User) {
			User user = (User) person;
			
			
			try {
				for(Resource recommendedR : user.getRecommendations()) {
					
					VBox vbox = new VBox();
					Text title = new Text("Title: "+ recommendedR.getTitle());
					Text year = new Text("Year: "+ recommendedR.getYear());		
					vbox.getChildren().addAll(title,year);
					
					recommended.getChildren().add(vbox);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
	}
	
	
	}
