package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.DBHelper;

public class EventCreationController {

	@FXML
	private TextField eventNameField;
	
	@FXML
	private TextField eventDetailsField;
	
	@FXML
	private DatePicker datePickerField;
	
	@FXML
	private TextField maxAttendingField;

	public void createEvent() {
		
		try {
			
            Connection connectionToDB = DBHelper.getConnection();
            PreparedStatement sqlStatement = connectionToDB.prepareStatement("INSERT INTO events VALUES (?,?,?,?)");
            
            String eventDate = datePickerField.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String eventName = eventNameField.getText();
            String eventDetails = eventDetailsField.getText();
            int maxAttending = Integer.parseInt(maxAttendingField.getText());
            
            sqlStatement.setString(1, eventName);
            sqlStatement.setString(2, eventDetails);
            sqlStatement.setString(3, eventDate);
            sqlStatement.setInt(4, maxAttending);
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		
		
		
	}

}
