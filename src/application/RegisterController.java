package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DBHelper;
import model.Person;
import model.SavedAvatar;
import model.User;

public class RegisterController implements Initializable {

	@FXML
	private TextField firstName;

	@FXML
	private TextField address;

	@FXML
	private Label firstNameError;

	@FXML
	private Label addressError;

	@FXML
	private Label phoneNumberError;

	@FXML
	private Label usernameError;

	@FXML
	private Label postCodeError;

	@FXML
	private CheckBox librarianCheckBox;

	@FXML
	private ImageView avatar;

	@FXML
	private TextField lastName;

	@FXML
	private Label lastNameError;

	@FXML
	private TextField phoneNumber;

	@FXML
	private Button registerButt;

	@FXML
	private Button browseButton;

	@FXML
	private Button createButton;

	@FXML
	private TextField postCode;

	@FXML
	private TextField username;

	@FXML
	private TextField staffId;

	@FXML
	private Label staffIdError;
	
	@FXML
	private Label employmentDateError;

	@FXML
	private TextField employmentDate;

	@FXML
	private Pane pane;

	private String avatarPath = "/SavedAvatars/Avatar1.png";
	
	private Person user;


	/**
	 * Empty Constructor.
	 */
	public RegisterController() {

	}

	/**
	 * Loads an image for the avatar.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Image newAvatar = new Image(avatarPath);
		//avatar.setImage(newAvatar);
		staffIdError.setVisible(false);
		usernameError.setVisible(false);
		firstNameError.setVisible(false);
		lastNameError.setVisible(false);
		addressError.setVisible(false);
		postCodeError.setVisible(false);
		phoneNumberError.setVisible(false);
		employmentDateError.setVisible(false);
		staffId.visibleProperty().bind(librarianCheckBox.selectedProperty());
		employmentDate.visibleProperty().bind(librarianCheckBox.selectedProperty());
	}

	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * 
	 * @param sceneFXML
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			// create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns user to main login scene.
	 *
	 * @param event
	 *            event.
	 * @throws IOException
	 *             io exception.
	 */
	@FXML
	public void backToLogin(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}

	
	    /**
	     * Creates an account with the details provided if they are valid.
	     * If there is a problem with the details displays error message indicating where the problem is.
	     *
	     * @param event event.
	     * @throws IOException io exception.
	     */
	    @FXML
	    void createAccount(ActionEvent event) {

	        //if any text field is invalid display error message under it.
	    	
	        if (validateUsername()) {
	            usernameError.setVisible(false);
	        } else {
	            usernameError.setVisible(true);
	        }

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
	            postCodeError.setVisible(false);
	        } else {
	            postCodeError.setVisible(true);
	        }
	        
	        if (validateStaffId()) {
	            staffIdError.setVisible(false);
	        } else {
	        	staffIdError.setVisible(true);
	        }
	        
	        if(!librarianCheckBox.isSelected()) {
	        	 staffIdError.setVisible(false);
	        }
	        
	  
	        //if all text fields are valid create account.
	        if (validateUsername() && validateFirstName() && validateLastName() && validatePhoneNumber()
	                && validateAddress() && validatePostCode()) {
	   
	        	if(isLibrarian(event)) {
	
	        		if(validateStaffId()) {
	        
	        		insertIntoUser(event);
	        		insertIntoLibrarian(event);
	        	}
	        	}
	        	else {
	            insertIntoUser(event);
	            
	        }
	        }

	    }


	private boolean validatePostCode() {
		String postCodeText = postCode.getText();
		if (postCodeText.contains(" ")) {
			
			return !(postCodeText.length() > 8 || postCodeText.length() <= 6);
		}
		return !(postCodeText.length() > 7 || postCodeText.length() <= 5);
	}

	private boolean validateAddress() {
		String addressText = address.getText();
		return !(addressText.length() > 1000 || addressText.length() <= 0);
	}

	private boolean validatePhoneNumber() {
		String phoneNumberText = phoneNumber.getText();
		return !(phoneNumberText.length() > 11 || phoneNumberText.length() < 11 || !isNumeric(phoneNumberText));
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

	private boolean validateLastName() {
		 String lastNameText = lastName.getText();
		 
		return !(lastNameText.length() > 15 || lastNameText.length() <= 0);
	}

	private boolean validateFirstName() {
		String firstNameText = firstName.getText();
		 
		return !(firstNameText.length() > 15 || firstNameText.length() <= 0);
	}

	private boolean validateUsername() {
		String usernameText = username.getText();
		
		return !(usernameText.length() > 15 || usernameText.length() <= 0 || validateExistingUser(usernameText));
	}

	/**
	 * Validates if the username already exists in the database.
	 *
	 * @return true if username is valid.
	 */
	private boolean validateExistingUser(String username) {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean validateStaffId() {
		String staffIdText = staffId.getText();
		 
		return !(staffIdText.length() > 2 || staffIdText.length() < 2 || validateExistingStaffId(staffIdText));
	}

	/**
	 * Validates if the staffId already exists in the database.
	 *
	 * @return true if staffId is valid.
	 */
	private boolean validateExistingStaffId(String staffId) {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM staff WHERE staffId=?");
			pstmt.setString(1, staffId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
	}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	    
		/**
		 * Checks to see if the user is an librarian
		 * @param event checkbox being checked
		 * @return boolean of whehter checkbox was selected
		 */
	    public boolean isLibrarian(ActionEvent event) {//show staffId box
	    	boolean selected = librarianCheckBox.isSelected();
	    	/**if(librarianCheckBox.isSelected()) {
	    		staffId.setVisible(true);
		        employmentDate.setVisible(true);
	    		}*/
	    	return selected;
	    }
	    
	    /** Builds account in database using inputted info and chosen avatar.
	     *  Then return user to login page.
	     *
	     *  @param event
	     */
	    private void insertIntoUser(ActionEvent event) {
	        
	    	try {
				Connection conn = DBHelper.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,0)");
				pstmt.setString(1, username.getText());
				pstmt.setString(2, firstName.getText());
				pstmt.setString(3, lastName.getText());
				pstmt.setString(4, phoneNumber.getText());
				pstmt.setString(5, address.getText());
				pstmt.setString(6, postCode.getText());
				pstmt.setString(7, avatarPath);
				pstmt.executeUpdate();//This can return a value to tell you if it was successful.

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
	    /**
	     * Inserts the librarin attributes into the staff table
	     * @param event button being pressed
	     */
	    private void insertIntoLibrarian(ActionEvent event) {
	        
	    	try {
				Connection conn = DBHelper.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO staff VALUES (?, ?, ?)");//make current date
				pstmt.setString(1, username.getText());
				pstmt.setString(2, staffId.getText());
				pstmt.setString(3, employmentDate.getText());
				pstmt.executeUpdate();//This can return a value to tell you if it was successful.

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * Opens custom drawing window. Lets the user draw an icon.
	 *
	 * @param event
	 *            event.
	 * @throws IOException
	 *             io exception.
	 */
	@FXML
	public void customAvatarAction(MouseEvent event) {
		AvatarDrawingController avatarDrawingController = new AvatarDrawingController();
		avatarDrawingController.setPrevScene("profile");
		changeScene(event, "/fxml/drawAvatar.fxml");
	}
	
	/**
     * Convert custom profile image to file.
     *
     * @param path     location of directory.
     * @param fileName file name.
     */
    public void convertToFile(String fileName, String path) {

        //Create path for File class as absolute path to project

        path = path.substring(0, path.length() - 2)
                + "/SavedAvatars/"
                + fileName;

        //Create the file that will be saved.
        File file = new File(path);

        //If the file isn't null, try to save it.
        if (!(file == null)) {
            try {
                /* Convert a snapshot of the canvas to a buffered image
                 * and the write it to file.
                 */
                BufferedImage bImage = SwingFXUtils.fromFXImage(
                        avatar.snapshot(null, null), null);
                ImageIO.write(bImage, "png", file);
            } catch (Exception e) {
                /* If an exception is cause, print the error message
                 * on the console.
                 */
                System.out.println(e.getMessage());
            }
        }
    }

	/**
	 * Sets avatar image.
	 *
	 * @param image
	 */
	public void setAvatar(Image image) {
		this.avatar.setImage(image);
	}

	/**
	 * Sets path to avatar image.
	 *
	 * @param path
	 */
	public void setAvatarPath(String path) {
		this.avatarPath = path;
	}
	
	/**
	 * select a preset avatar
	 * @param event
	 */
	@FXML protected void browseAvatarAction(ActionEvent event) {
	    FileChooser chooser = new FileChooser();
	    FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		chooser.getExtensionFilters().add(extentionFilter);
	    chooser.setTitle("Open File");    
	    File file = chooser.showOpenDialog(new Stage());
	    System.out.println(file.getAbsolutePath());
	    setAvatarPath(file.getAbsolutePath());
	    Image selectedAvatar = new Image(new File(this.avatarPath).toURI().toString());
	   // Image selectedAvatar = new Image(this.avatarPath);
	    avatar.setImage(selectedAvatar);
	}

	// TODO update selected avatar
}
