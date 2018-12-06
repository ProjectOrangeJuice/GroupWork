package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
	
	
public class RegisterController {
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
	    private RadioButton librarian;

	    @FXML
	    private ImageView avatar;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private Label lastNameError;

	    @FXML
	    private TextField phoneNumber;

	    @FXML
	    private Button registerButton;

	    @FXML
	    private Button browseButton;

	    @FXML
	    private Button createButton;

	    @FXML
	    private TextField postCode;

	    @FXML
	    private TextField username;
	    
	    @FXML
	    private Pane pane;

	    
	    private String avatarPath = "/SavedAvatars/Avatar1.png";
	    
	    private Pane rootPane;
	    
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
	        Image newAvatar = new Image(avatarPath);
	        avatar.setImage(newAvatar);
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
	     * Creates an account with the details provided if they are valid and opens navigation window with your new account log on.
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

	        if (validateAddess()) {
	            addressError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            addressError.setTextFill(Paint.valueOf("RED"));
	        }

	        if (validatePostCode()) {
	            postCodeError.setTextFill(Paint.valueOf("transparent"));
	        } else {
	            postCodeError.setTextFill(Paint.valueOf("RED"));
	        }

	        //if all text fields are valid create account.
	        if (validateUsername() && validateFirstName() && validateLastName() && validatePhoneNumber()
	                && validateAddess() && validatePostCode()) {
	            createAccountAction(event);
	        }
	        

	    }

		private boolean validatePostCode() {
			String postCodeText = postCode.getText();
	        if (postCodeText.contains(" ")) {
	        	return !(postCodeText.length() > 8 || postCodeText.length() <= 6);
	        }
	        return !(postCodeText.length() > 7 || postCodeText.length() <= 5);
		}

		private boolean validateAddess() {
			String addressText = address.getText();

	        return !(addressText.length() > 20 || addressText.length() <= 0);
		}

		private boolean validatePhoneNumber() {
			String phoneNumberText = phoneNumber.getText();

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

	        return !(usernameText.length() > 15 || usernameText.length() <= 0 || validateExistingUsers());
		}
	    
		/**
	     * Validates if the username already exists in the database.
	     *
	     * @return true of username is valid.
	     */
	    public boolean validateExistingUsers() {
	        String usernameText = username.getText();

	        if (!usernameText.isEmpty()) {
	            for (String username1 : ScreenManager.getAllUsernames()) {//create method
	                if (usernameText.equals(username1)) {
	                    return true;

	                }
	            }
	        }
	        return false;
	    }
	    
	    public boolean isLibrarian() {
	    	
	    }
	    
	    /** Uses all the informatoin from the textfield and  the avatar that is being displayed and build account from it.
	     *  Then logs the user into the system.
	     *
	     *  @param event event.
	     */
	    private void createAccountAction(ActionEvent event) {
	        
	        //TODO code to add user to db

	    }
	    
	    /**
	     * Opens avatar drawing page.
	     *
	     * @param event event.
	     * @throws IOException io exception.
	     */
	    @FXML
	    void customDrawingAction(ActionEvent event) throws IOException {
	        AvatarDrawingController avatarDrawingController = new AvatarDrawingController(pane, true);

	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/drawAvatar.fxml"));
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
	    
	}

