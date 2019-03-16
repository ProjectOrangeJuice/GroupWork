package application;

import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextFlow;
import model.Person;
import model.Resource;

public class LibrarianStatisticsContolller {

	@FXML
	private Tab weekTab;

	@FXML
	private RadioButton bookRB1;

	@FXML
	private Tab overallTab;

	@FXML
	private RadioButton bookRB11;

	@FXML
	private RadioButton gameRB11;

	@FXML
	private Tab dayTab;

	@FXML
	private RadioButton laptopRB1;

	@FXML
	private TextFlow descBox1;

	@FXML
	private RadioButton gameRB;

	@FXML
	private RadioButton dvdRB11;

	@FXML
	private RadioButton laptopRB11;

	@FXML
	private TextFlow descBox;

	@FXML
	private RadioButton bookRB;

	@FXML
	private RadioButton gameRB1;

	@FXML
	private RadioButton dvdRB;

	@FXML
	private RadioButton laptopRB;

	@FXML
	private RadioButton dvdRB1;

	@FXML
	private TextFlow descBox11;

	@FXML
	private Button requestButton;

	@FXML
	private ToggleGroup group;

	public void initialize() {
		Person person = ScreenManager.getCurrentUser();
		Resource resource = ScreenManager.getCurrentResource();
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		String toogleGroupValue = selectedRadioButton.getText();
		

		requestButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (toogleGroupValue) {
				case "bookRB":
					ScreenManager.setCurrentResource(getMostPopularBook(dayTab));
					;
					break;
				case "bookRB1":
					ScreenManager.setCurrentResource(getMostPopularBook(weekTab));
					;
					break;
				case "bookRB11":
					ScreenManager.setCurrentResource(getMostPopularBook(overallTab));
					;
					break;
				case "dvdRB":
					ScreenManager.setCurrentResource(getMostPopularDVD(dayTab));
					;
					break;
				case "dvdRB1":
					ScreenManager.setCurrentResource(getMostPopularDVD(weekTab));
					;
					break;
				case "dvdRB11":
					ScreenManager.setCurrentResource(getMostPopularDVD(overallTab));
					;
					break;
				case "laptopRB":
					ScreenManager.setCurrentResource(getMostPopularLaptop(dayTab));
					;
					break;
				case "laptopRB1":
					ScreenManager.setCurrentResource(getMostPopularLaptop(weekTab));
					;
					break;
				case "laptopRB11":
					ScreenManager.setCurrentResource(getMostPopularLaptop(overallTab));
					;
					break;
				case "gameRB":
					ScreenManager.setCurrentResource(getMostPopularGame(dayTab));
					;
					break;
				case "gameRB1":
					ScreenManager.setCurrentResource(getMostPopularGame(weekTab));
					;
					break;
				case "gameRB11":
					ScreenManager.setCurrentResource(getMostPopularGame(overallTab));
					;
					break;
				}

			}
		});

	}

	public Resource getMostPopularBook(Tab tab) {
		Resource popBook;
		if(tab != null) {
    	if(tab.equals(dayTab)) {
			popBook = ;
		}
    	else if(tab.equals(weekTab)) {
			popBook = ;
		}
    	else {
			popBook = ;
		}
		return popBook;
    	
    }
    }

	public Resource getMostPopularDVD(Tab tab) {
    	Resource popDVD;
		if(tab != null) {
    	if(tab.equals(dayTab)) {
    		popDVD = ;
		}
    	else if(tab.equals(weekTab)) {
    		popDVD = ;
		}
    	else {
    		popDVD = ;
		}
		return popDVD;
    	
    }
    	
    }

	public Resource getMostPopularLaptop(Tab tab) {
    	Resource popLaptop;
		if(tab != null) {
    	if(tab.equals(dayTab)) {
    		popLaptop = ;
		}
    	else if(tab.equals(weekTab)) {
    		popLaptop = ;
		}
    	else {
    		popLaptop = ;
		}
		return popLaptop;
    	
    }
    	
    }

	public Resource getMostPopularGame(Tab tab) {
    	Resource popGame;
		if(tab != null) {
    	if(tab.equals(dayTab)) {
    		popGame = ;
		}
    	else if(tab.equals(weekTab)) {
    		popGame = ;
		}
    	else {
    		popGame = ;
		}
		return popGame;
    	
    }
    	
    }

}
