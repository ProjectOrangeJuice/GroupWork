package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.DBHelper;
import model.Event;

public class EventCreationController {

	@FXML
	private TextField eventNameField;
	
	@FXML
	private TextArea eventDetailsField;
	
	@FXML
	private DatePicker datePickerField;
	
	@FXML
	private TextField maxAttendingField;
	
	@FXML
	 public void initialize() {
		
	}

	public void createEvent() {
		
		try {

            Connection connectionToDB = DBHelper.getConnection();
            PreparedStatement sqlStatement = connectionToDB.prepareStatement("INSERT INTO events VALUES (?,?,?,?,?)");
            
            String eventDate = datePickerField.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String eventName = eventNameField.getText();
            String eventDetails = eventDetailsField.getText();
            int maxAttending = Integer.parseInt(maxAttendingField.getText());
            
            sqlStatement.setInt(1, Event.getAllEvents().size()+1);
            sqlStatement.setString(2, eventName);
            sqlStatement.setString(3, eventDetails);
            sqlStatement.setString(4, eventDate);
            sqlStatement.setInt(5, maxAttending);
            
            sqlStatement.execute();
            
            Event.addEvent(eventName, eventDetails, eventDate, maxAttending);
            
            System.out.println("NewEventAdded!");
            for(Event event : Event.getAllEvents()) {
            	System.out.println(event.getTitle());
            	System.out.println(Event.getAllEvents().size());
            }
    
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

	}

}
