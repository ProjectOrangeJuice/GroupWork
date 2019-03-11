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
	
	public void testReturnEvent() throws SQLException {
		
		Connection conn = DBHelper.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM events");

		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
		
	}

	public void createEvent() {
		
		try {
			
			System.out.println("hello");
			
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
            
            testReturnEvent();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

	}

}
