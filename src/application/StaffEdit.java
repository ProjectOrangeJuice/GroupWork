package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Librarian;
import model.Person;

public class StaffEdit {

    @FXML
    private ImageView profileImageView;

    @FXML
    private SplitMenuButton changeProfileImageButton;

    @FXML
    private TextField usernameText;

    @FXML
    private TextField firstnameText;

    @FXML
    private TextField lastnameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postcodeText;

    @FXML
    private TextField staffIDText;

    @FXML
    private TextField employmentDateText;

    @FXML
    private Label phoneNumberText;

    @FXML
    private TextField lastnameText1;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    private Librarian currentLibrarian;
    
    /**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			//create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    @FXML
    void cancelEditProfile(MouseEvent event) {
    	changeScene(event, "/fxml/profileScene.fxml");
    }

    @FXML
    void saveEditedProfile(MouseEvent event) {
    	try {
			Connection conn = model.DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET firstName = ?, "
					+ "+ lastName = ?, telephone = ?, address = ?, postcode = ?, avatarPath = ?,"
					+ "staffID = ?, employmentDate = ?  WHERE username = ?");
			pstmt.setString(1, currentLibrarian.getFirstName());
			pstmt.setString(2, currentLibrarian.getLastName());
			pstmt.setString(3, currentLibrarian.getPhoneNumber());
			pstmt.setString(4, currentLibrarian.getAddress());
			pstmt.setString(5, currentLibrarian.getPostcode());
			pstmt.setString(6, currentLibrarian.getAvatar());
			pstmt.setInt(7, currentLibrarian.getStaffID());
			
			SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
			pstmt.setString(8, sdfr.format(currentLibrarian.getEmploymentDate()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void loadStaffInformation() {
    	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
    	//get all information in about user from ScreenManager class.
    	String username = currentLibrarian.getUsername();
    	String firstname = currentLibrarian.getFirstName();
    	String lastname = currentLibrarian.getLastName();
    	String address = currentLibrarian.getAddress();
    	String postcode = currentLibrarian.getPostcode();
    	String phoneNumber = currentLibrarian.getPhoneNumber();
    	int staffID = currentLibrarian.getStaffID();
    	String employmentDate = sdfr.format(currentLibrarian.getEmploymentDate());
    	
    	//change text to text field for staff to edit
    	usernameText.setText(username);
    	firstnameText.setText(firstnameText.getText() + " " + firstname);
    	lastnameText.setText(lastnameText.getText() + " " + lastname);
    	phoneNumberText.setText(phoneNumberText.getText() + " " + phoneNumber);
    	addressText.setText(addressText.getText() + " " + address);
    	postcodeText.setText(postcodeText.getText() + " " + postcode);
    	staffIDText.setText(staffIDText.getText() + " " + staffID);
    	employmentDateText.setText(employmentDateText.getText() + " " + employmentDate);
    }

}
