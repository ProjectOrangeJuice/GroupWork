package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Librarian;
import model.Person;

public class StaffEdit {

    @FXML
    private ImageView profileImageView;//storage for profile image

    @FXML
    private SplitMenuButton changeProfileImageButton;//change profile image button

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;//username text box

    @FXML
    private TextField firstnameText;//firstname text box

    @FXML
    private TextField lastnameText;//lastname text box

    @FXML
    private TextField addressText;//address text box

    @FXML
    private TextField postcodeText;//postcode text box

    @FXML
    private TextField staffIDText;//staffid text box

    @FXML
    private TextField employmentDateText;//employmentdate text box

    @FXML
    private TextField phoneNumberText;//phone number text box

    @FXML
    private TextField lastnameText1;

    @FXML
    private Button saveButton;//save button

    @FXML
    private Button cancelButton;//cancel button
    
    private Person currentUser;//current user instance
    
    private Pane rootPane;
    
    private ScrollPane scrollPane;
    
    public void setRootPane(Pane pane) {
    	this.rootPane = pane;
    }
    
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

	/**
	 * canceledit button
	 * @param event when the button is pressed
	 */
    @FXML
    void cancelEditProfile(MouseEvent event) {
    	changeScene(event, "/fxml/profileScene.fxml");
    }

    /**
     * Saves profile information about the user
     * @param event button is presed
     */
    @FXML
    void saveEditedProfile(MouseEvent event) {
    	String firstName = firstnameText.getText();
    	String lastName = lastnameText.getText();
    	String phoneNumber = phoneNumberText.getText();
    	String address = addressText.getText();
    	String postcode = postcodeText.getText();
    	String staffID = staffIDText.getText();
    	String employmentDate = employmentDateText.getText();
    	String username = usernameLabel.getText();
    	
    	try {
    		//SQL that saves the profile information into the table
    		Connection conn = model.DBHelper.getConnection();
    		PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, telephone = ?, address = ?, postcode = ? WHERE username = ?");
    		pstmt.setString(1, firstName);
   			pstmt.setString(2, lastName);
   			pstmt.setString(3, phoneNumber);
   			pstmt.setString(4, address);
   			pstmt.setString(5, postcode);
   			pstmt.setString(6, username);
    		pstmt.executeUpdate();
    		
    		PreparedStatement pstmt1 = conn.prepareStatement("UPDATE staff SET staffID = ?, employmentDate = ?  WHERE username = ?");
    		pstmt1.setInt(1, Integer.parseInt(staffID));
   			pstmt1.setString(2, employmentDate);
   			pstmt1.setString(3,  username);
    			
   			pstmt1.executeUpdate();
   			
   			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	changeScene(event, "/fxml/profileScene.fxml");
    }
    
    /**
     * Method that loads the staff information and adds them to the text fields
     */
    public void loadStaffInformation() {
    	if (currentUser instanceof Librarian) {
    		Librarian currentStaff = (Librarian) currentUser;
    		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
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
	    	usernameLabel.setText(username);
	    	firstnameText.setText(firstname);
	    	lastnameText.setText(lastname);
	    	phoneNumberText.setText(phoneNumber);
	    	addressText.setText(address);
	    	postcodeText.setText(postcode);
	    	staffIDText.setText(Integer.toString(staffID));
	    	employmentDateText.setText(employmentDate);
    	}
    }
    
    /**
     * Intialize method that is called when the program starts
     */
    @FXML
	 public void initialize() {
		
		currentUser = ScreenManager.getCurrentUser();
				
		loadStaffInformation();
	 }

}
