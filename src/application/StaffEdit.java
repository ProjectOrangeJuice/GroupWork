package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBHelper;
import model.Librarian;
import model.Person;

/**
 * This class allows staff/librarians to view and edit their details when they register to the system.
 * Also, they can edit some information adn update it back to the database with their latest information.
 * @author leezhinghang
 */
public class StaffEdit {

    @FXML
    private Label usernameLabel; // username label

    @FXML
    private TextField usernameText;// username text box

    @FXML
    private TextField firstnameText;//first name text box
    
    @FXML
    private Label firstNameError; // first name error label
    
    @FXML
    private Label lastNameError; // last name error label
    
    @FXML
    private Label phoneNumberError; // phone number error label
    
    @FXML
    private Label addressError; // address error label
    
    @FXML
    private Label postcodeError; // postcode error label

    @FXML
    private TextField lastnameText;// lastname text box

    @FXML
    private TextField addressText;// address text box

    @FXML
    private TextField postcodeText;// postcode text box

    @FXML
    private Label staffIDLabel;//staffid text box

    @FXML
    private Label employmentDateLabel;//employmentdate text box

    @FXML
    private TextField phoneNumberText;// phone number text box

    @FXML
    private Button saveButton;// save button

    @FXML
    private Button cancelButton;// cancel button

    private Person currentUser;// current user instance

    private Pane rootPane;

    private ScrollPane scrollPane;

    public void setRootPane(Pane pane) {
        this.rootPane = pane;
    }

    /**
     * Sets new scene on stage within program using fxml file provided.
     * 
     * @param sceneFXML scene that will be changed into.
     * @param event Mouse event.
     */
    public void changeScene(MouseEvent event, String sceneFXML) {
        try {
            // create new scene object
            Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene()
                .getWindow();
            stage.getScene().setRoot(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel edit button.
     * 
     * @param event when the button is pressed.
     */
    @FXML
    void cancelEditProfile(MouseEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
    	stage.close();
    	//changeScene(event, "/fxml/profileScene.fxml");
    }

    /**
     * Saves profile information about the user.
     * 
     * @param event button is pressed.
     */
    @FXML
    void saveEditedProfile(MouseEvent event) {
    	String firstName = firstnameText.getText();
    	String lastName = lastnameText.getText();
    	String phoneNumber = phoneNumberText.getText();
    	String address = addressText.getText();
    	String postcode = postcodeText.getText();
    	String staffID = staffIDLabel.getText();
    	String employmentDate = employmentDateLabel.getText();
    	String username = usernameLabel.getText();
    	
    	if (validateFirstName() && validateLastName() && validatePhoneNumber() 
    			&& validateAddress() && validatePostCode()) {
    		try {
        		//SQL that saves the profile information into the table
        		Connection conn = model.DBHelper.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(
        				"UPDATE users SET firstName = ?, lastName = ?, "
        				+ "telephone = ?, address = ?, postcode = ? WHERE "
        				+ "username = ?");
        		pstmt.setString(1, firstName);
       			pstmt.setString(2, lastName);
       			pstmt.setString(3, phoneNumber);
       			pstmt.setString(4, address);
       			pstmt.setString(5, postcode);
       			pstmt.setString(6, username);
        		pstmt.executeUpdate();
        		//conn.commit();
        		
        		PreparedStatement pstmt1 = conn.prepareStatement("UPDATE "
        				+ "staff SET staffID = ?, employmentDate = ?  WHERE "
        				+ "staff.username = ?");
        		pstmt1.setInt(1, Integer.parseInt(staffID));
       			pstmt1.setString(2, employmentDate);
       			pstmt.setString(3,  username);
        			
       			pstmt1.executeUpdate();
       			//conn.commit();
       			
       			conn.close();
       			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	alertDone("Details have been saved");
        	//Stage stage = (Stage) saveButton.getScene().getWindow();
        	//stage.close();
        	try {
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginScene.fxml"));
    			Parent root1 = (Parent) fxmlLoader.load();
    			Stage stage = new Stage();
    			stage.initModality(Modality.APPLICATION_MODAL);
    			stage.setScene(new Scene(root1));
    			stage.show();

    		} catch (IOException e2) {
    			e2.printStackTrace();
    		}
    	}
    }

    /**
     * Method that loads the staff information and adds them to the text fields.
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
	    	staffIDLabel.setText(Integer.toString(staffID));
	    	employmentDateLabel.setText(employmentDate);
	    	
	    	if (validateFirstName()) {
	            firstNameError.setVisible(false);
	        } else {
	            firstNameError.setVisible(true);
	        }
	    	
	    	if (validateLastName()) {
	            lastNameError.setVisible(false);
	        } else {
	            lastNameError.setVisible(true);
	        }
	    	
	    	if (validatePhoneNumber()) {
	            phoneNumberError.setVisible(false);
	        } else {
	            phoneNumberError.setVisible(true);
	        }
	    	
	    	if (validateAddress()) {
	            addressError.setVisible(false);
	        } else {
	            addressError.setVisible(true);
	        }
	    	
	    	if (validatePostCode()) {
	            postcodeError.setVisible(false);
	        } else {
	            postcodeError.setVisible(true);
	        }
    	}
    }

    /**
     * Intialize method that is called when the program starts.
     */
    @FXML
    public void initialize() {
        currentUser = ScreenManager.getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        loadStaffInformation();
    }
	
    /**
     * Validation to ensure address is valid.
     * @return true if it's a valid address, false if the address is not valid.
     */
	private boolean validateAddress() {
		String address = addressText.getText();
		return !(address.length() > 1000 || address.length() <= 0);
	}
	
	/**
	 * Validation to ensure phone number is in correct form.
	 * @return true if number is exactly 10 digits and consists of all numbers, false otherwise.
	 */
	private boolean validatePhoneNumber() {
		String phoneNumber = phoneNumberText.getText();
		return !(phoneNumber.length() > 11 || phoneNumber.length() < 9 
				|| !isNumeric(phoneNumber));
	}
	
	/**
	 * Validate to ensure last name is valid.
	 * @return true if the name is within given length, false otherwise.
	 */
	private boolean validateLastName() {
		 String lastName = lastnameText.getText();
		 
		return !(lastName.length() > 15 || lastName.length() <= 0);
	}

    /**
     * Generate a popup.
     * 
     * @param text The text to be displayed.
     */
    private void alertDone(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    /**
     * Validate to ensure first name is valid.
     * @return true if name is within given length, false otherwise.
     */
	private boolean validateFirstName() {
		String firstName = firstnameText.getText();
		 
		return !(firstName.length() > 15 || firstName.length() <= 0);
	}

	
	/**
	 * Checks if a string consists of numbers.
	 *
	 * @param str
	 *            string to check.
	 * @return true if String = numbers.
	 */
	private boolean isNumeric(String str) {
		return str.matches(".*\\d+.*");
	}
	
	/**
	 * Validate to ensure post code is valid.
	 * @return true is post code is entered with given format, false otherwise.
	 */
	private boolean validatePostCode() {
		String postCode = postcodeText.getText();
		if (postCode.contains(" ")) {
			
			return !(postCode.length() > 8 || postCode.length() <= 6);
		}
		return !(postCode.length() > 7 || postCode.length() <= 5);
	}
}
