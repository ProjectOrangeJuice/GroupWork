package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.DBHelper;

public class EventCreationController {

	@FXML
	private TextField eventNameField;
	
	@FXML
	private TextArea eventDetailsField;
	
	@FXML
	private DatePicker datePickerField;
	
	@FXML
	private TextField maxAttendingField;
	
	private static int eventNumber = 0;
	
	@FXML
	 public void initialize() {
		
	}
	
	public int setEventNumber() throws SQLException {
	
		Connection connectionToDB = DBHelper.getConnection();
        
        Statement stmt = connectionToDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM events");

		while(rs.next()) {
			eventNumber += 1;
			System.out.println(rs.getInt(1) +": " + rs.getString(2));
		}
		
		connectionToDB.close();
		
		System.out.println("Event Number: " + eventNumber);
		return eventNumber;
		
	}

	public void createEvent() {
		
		try {

			setEventNumber();
			
            Connection connectionToDB = DBHelper.getConnection();
            PreparedStatement sqlStatement = connectionToDB.prepareStatement("INSERT INTO events VALUES (?,?,?,?,?)");
            
            String eventDate = datePickerField.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String eventName = eventNameField.getText();
            String eventDetails = eventDetailsField.getText();
            int maxAttending = Integer.parseInt(maxAttendingField.getText());
            
            sqlStatement.setInt(1, eventNumber+1);
            sqlStatement.setString(2, eventName);
            sqlStatement.setString(3, eventDetails);
            sqlStatement.setString(4, eventDate);
            sqlStatement.setInt(5, maxAttending);
            
            sqlStatement.execute();
            eventNumber += 1;
    
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

	}

}
