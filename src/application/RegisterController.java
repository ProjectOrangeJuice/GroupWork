package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBHelper;
import model.Person;
import model.User;
	
	
public class RegisterController {
	    @FXML
	    private TextField firstName;//textbox that holds the users firstname

	    @FXML
	    private TextField address;//textbox that holds the users address

	    @FXML
	    private Label firstNameError;//first name error message

	    @FXML
	    private Label addressError;//address error message

	    @FXML
	    private Label phoneNumberError;//phone number error message

	    @FXML
	    private Label usernameError;//user name error message

	    @FXML
	    private Label postCodeError;//postcode error message

	    @FXML
	    private CheckBox librarianCheckBox;//allows a librarian to register a librarian

	    @FXML
	    private ImageView avatar;//profile image

	    @FXML
	    private TextField lastName;//texbox for last name

	    @FXML
	    private Label lastNameError;//last name error message

	    @FXML
	    private TextField phoneNumber;//textbox for phone number

	    @FXML
	    private Button registerButton;//button that calls a register method

	    @FXML
	    private Button browseButton;

	    @FXML
	    private Button createButton;//button that creates the person

	    @FXML
	    private TextField postCode;//textbox that holds postcode

	    @FXML
	    private TextField username;//textbox that holds username
	    
	    @FXML
	    private TextField staffId;//textbox that holds teh staff id
	    
	    @FXML
	    private Label staffIdError;//staff id error message
	    
	    @FXML
	    private TextField employmentDate;//text box that holds employment date
	    
	    @FXML
	    private Pane pane;
	    
	    private Pane rootPane;
	    
	    //sets all the textboxs to the variables
	    private String avatarPath = "/SavedAvatars/Avatar1.png";
	    private String postCodeText = postCode.getText();
	    private String usernameText = username.getText();
	    private String addressText = address.getText();
	    private String phoneNumberText = phoneNumber.getText();
	    private String lastNameText = lastName.getText();
	    private String firstNameText = firstName.getText();
	    private String staffIdText = staffId.getText();
	    private String employmentDateText = employmentDate.getText();
	    
	    /**
	     * Empty Constructor.
	     */
	    public RegisterController() {

	    }
	    
	    /**
	     * Loads an image for the avatar.
	     */
	    //@Override
	    public void initialize(URL location, ResourceBundle resources) {
	        Image newAvatar = new Image(avatarPath);
	        avatar.setImage(newAvatar);
	        staffId.setVisible(false);
	        employmentDate.setVisible(false);
	    }
	    
	    /**
	     * Returns user to main login scene.
	     *
	     * @param event event.
	     * @throws IOException io exception.
	     */
	    @FXML
	    void backToLogIn(ActionEvent event) throws IOException {
	        //builds a new stage
	        Stage primaryStage = new Stage();

	        Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginScene.fxml"));

	        Scene scene = new Scene(root);

	        primaryStage.setTitle("Login");
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        // Hides the old window
	        ((Node) (event.getSource())).getScene().getWindow().hide();

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
	            usernameError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            usernameError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validateFirstName()) {
	            firstNameError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            firstNameError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validateLastName()) {
	            lastNameError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            lastNameError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validatePhoneNumber()) {
	            phoneNumberError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            phoneNumberError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validateAddress()) {
	            addressError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            addressError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validatePostCode()) {
	            postCodeError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            postCodeError.setTextFill(Paint.valueOf("RED"));
	        }
	        
	        if (validateStaffId()) {
	            staffIdError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	        	staffIdError.setTextFill(Paint.valueOf("RED"));
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

	    /**
	     * Validates postcode by checking the length
	     * @return false if the postode is invalid
	     */
		private boolean validatePostCode() {
			
	        if (postCodeText.contains(" ")) {
	        	return !(postCodeText.length() > 8 || postCodeText.length() <= 6);
	        }
	        return !(postCodeText.length() > 7 || postCodeText.length() <= 5);
		}

		/**
		 * Validates address by checking length
		 * @return false if the address is invalid
		 */
		private boolean validateAddress() {
			
	        return !(addressText.length() > 1000 || addressText.length() <= 0);
		}

		/**
		 * Validates phone number by checking length
		 * @return false if the number is invalid
		 */
		private boolean validatePhoneNumber() {
			
	        return !(phoneNumberText.length() > 11 || phoneNumberText.length() < 11 || !isNumeric(phoneNumberText));
		}

		/**
	     * Checks if a string consists of numbers.
	     *
	     * @param str string to check.
	     * @return true if String = numbers.
	     */
	    private boolean isNumeric(String str) {
	        return str.matches(".*\\d+.*");
	    }
		
	    /**
	     * validates the last name by checking the length
	     * @return false if the lastname is invalid
	     */
		private boolean validateLastName() {
			
	        return !(lastNameText.length() > 15 || lastNameText.length() <= 0);
		}

		 /**
	     * validates the first name by checking the length
	     * @return false if the firstname is invalid
	     */
		private boolean validateFirstName() {
			
	        return !(firstNameText.length() > 15 || firstNameText.length() <= 0);
		}

		 /**
	     * validates the last name by checking the length and if it already exists
	     * @return false if the username is invalid
	     */
		private boolean validateUsername() {
			
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
		            if(rs.next()) {
		            	return true;
		            }else {
		            	return false;
		            }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		/**
		 * Validates the staff id by checking length and if it already exists
		 * @return false if the staff id is invalid
		 */
		private boolean validateStaffId() {
			
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
		            if(rs.next()) {
		            	return true;
		            }else {
		            	return false;
		            }
				
			} catch (SQLException e) {
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
	    	if(librarianCheckBox.isSelected()) {
	    		staffId.setVisible(true);
		        employmentDate.setVisible(true);
	    	}
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
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,0");
				pstmt.setString(1, usernameText);
				pstmt.setString(2, firstNameText);
				pstmt.setString(3, lastNameText);
				pstmt.setString(4, phoneNumberText);
				pstmt.setString(5, addressText);
				pstmt.setString(6, postCodeText);
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
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO staff VALUES (?, staffId, employmentDate)");//make current date
				pstmt.setString(1, usernameText);
				pstmt.setString(2, staffIdText);
				pstmt.setString(3, employmentDateText);
				pstmt.executeUpdate();//This can return a value to tell you if it was successful.

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
	   /** final EventHandler<MouseEvent> clickHandler = event -> {
	    	
	    	
	    try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/drawAvatar.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Resource Information");
            stage.setScene(new Scene(root1));  
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    };*/
	    
	    
		/**
	     * Opens custom drawing window. Lets the user draw an icon.
	     *
	     * @param event event.
	     * @throws IOException io exception.
	     */
	    @FXML
	    void customDrawingAction(ActionEvent event) throws IOException {
	        AvatarDrawingController avatarDrawingController = new AvatarDrawingController(pane, true);

	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("co/uk/artatawe/gui/CustomProfileImagePage.fxml"));
	        fxmlLoader.setController(avatarDrawingController); //sets controller manually.

	        try {
	            pane.getChildren().add(fxmlLoader.load()); //changes the centre of the page.
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }

	    }
	    
	    /**
	     * Sets avatar image.
	     *
	     * @param image
	     */
	    public void setIcon(Image image) {
	        this.avatar.setImage(image);
	    }

	    /**
	     * Sets path to avatar image.
	     *
	     * @param path
	     */
	    public void setAvatarImagePath(String path) {
	        this.avatarPath = path;
	    }

	    /**
	     * Sets centre pane.
	     *
	     * @param pane
	     */
	    public void setRootPane(Pane pane) {
	        this.rootPane = pane;
	    }
	    
	    
	    //TODO open system explorer to select avatar
	}

