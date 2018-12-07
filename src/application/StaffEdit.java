package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    
    private Person currentUser;
    
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
    		if (currentUser instanceof Librarian) {
    			Librarian currentStaff = (Librarian) currentUser;
    			Connection conn = model.DBHelper.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET firstName = ?, "
    					+ "+ lastName = ?, telephone = ?, address = ?, postcode = ?, avatarPath = ?,"
    					+ "staffID = ?, employmentDate = ?  WHERE username = ?");
    			pstmt.setString(1, currentStaff.getFirstName());
    			pstmt.setString(2, currentStaff.getLastName());
    			pstmt.setString(3, currentStaff.getPhoneNumber());
    			pstmt.setString(4, currentStaff.getAddress());
    			pstmt.setString(5, currentStaff.getPostcode());
    			pstmt.setString(6, currentStaff.getAvatar());
    			pstmt.setInt(7, currentStaff.getStaffID());
    			
    			SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
    			pstmt.setString(8, sdfr.format(currentStaff.getEmploymentDate()));
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void loadStaffInformation() {
    	if (currentUser instanceof Librarian) {
    		Librarian currentStaff = (Librarian) currentUser;
    		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
	    	//get all information in about user from ScreenManager class.
	    	String username = currentStaff.getUsername();
	    	String firstname = currentStaff.getFirstName();
	    	String lastname = currentStaff.getLastName();
	    	String address = currentStaff.getAddress();
	    	String postcode = currentStaff.getPostcode();
	    	String phoneNumber = currentStaff.getPhoneNumber();
	    	int staffID = currentStaff.getStaffID();
	    	String employmentDate = sdfr.format(currentStaff.getEmploymentDate());
	    	
	    	//change text to text field for staff to edit
	    	usernameText.setText(username);
	    	firstnameText.setText(firstname);
	    	lastnameText.setText(lastname);
	    	phoneNumberText.setText(phoneNumber);
	    	addressText.setText(address);
	    	postcodeText.setText(postcode);
	    	staffIDText.setText(Integer.toString(staffID));
	    	employmentDateText.setText(employmentDate);
    	}
    }

}
