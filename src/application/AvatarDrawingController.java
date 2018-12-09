package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.DBExample;
import model.User;
import model.Avatar;
import model.Circle;
import model.CustomAvatar;
import model.ParticleTrace;
import model.Person;
import model.SavedAvatar;
import model.StraightLine;

/**
 * Handles events for avatar drawing page.
 *
 * @author James Finlayson
 * @version 1.0
 */
public class AvatarDrawingController implements Initializable {
    //The position that mouseX and Mouse Y are reset to.
    public static final double MOUSE_RESET_POSITION = -1;
    private double mouseX; //The last recorded x position of the mouse.
    private double mouseY; //The last recorded y position of the mouse.
    private Person user; //The user the custom avatar is being created for.
    private CustomAvatar customAvatar;//The customAvatar that is created.
    private ParticleTrace currentParticleTrace;//The current particle trace being drawn.
  //  private Pane rootPane;//The pane the custom avatar page is attached to. 
    private boolean registerPrevController; //true if prev controller was register, else false (from profile.)
    private String customDrawingFileLocation = ""; //location of the custom drawing created.

    //FXML generated attributes.

    @FXML
    private BorderPane root; //The border pane used to layout the page.

    @FXML
    private Canvas canvas; //The canvas the custom avatar is drawn on.

    @FXML
    private VBox options; //The v box used to layout the drawing options.

    @FXML
    private Label optionsTitle; //The title at the top of the options v box.

    //The toggle group that binds the options radio buttons.
    @FXML
    private ToggleGroup optionsToggleGroup;

    //The radio button that allows the user to draw straight lines.
    @FXML
    private RadioButton selectStraightLine;

    //The radio button that allows the user to draw particle traces.
    @FXML
    private RadioButton selectParticleTrace;

    //The title at the top of the page (border pane top box).
    @FXML
    private Label title;

    /* The button which allows a user to save the image as their 
     * new avatar and return to the profile page.
     */
    @FXML
    private Button saveImage;
    
	private String prevScene;

    /**
     * Constructor
     *
     * @param user The user the custom avatar is being created for.
    */
    public AvatarDrawingController(User user) {
        this.user = user;
    } 
	
	public AvatarDrawingController() {
		
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Reset the previous mouse position.
        mouseX = MOUSE_RESET_POSITION;
        mouseY = MOUSE_RESET_POSITION;
        customAvatar = new CustomAvatar(canvas.getHeight(),
                0, 0);
    }

    /**
     * Starts drawing if the mouse is pressed on the canvas.
     *
     * @param event event.
     */
    @FXML
    public void onCanvasMousePressed(MouseEvent event) {
                /* If straight line radio button is selected,
                 * start drawing straight line.
		 */
        if (optionsToggleGroup.getSelectedToggle()
                == selectStraightLine) {
            drawStraightLine(event);
        } else if (optionsToggleGroup.getSelectedToggle()
                == selectParticleTrace) {
                        /* If particle trace radio button is selected,
                         * start drawing particle trace.
			 */
            canvas.getGraphicsContext2D().beginPath();
            canvas.getGraphicsContext2D().moveTo(event.getX(),
                    event.getY());
            Color colour = Color.BLACK;
            currentParticleTrace = new ParticleTrace(mouseX,
                    mouseY, colour);
            drawParticleTrace(event);
        }
    }

    /**
     * Draw when the mouse is dragged over the canvas, if appropriate.
     *
     * @param event event.
     */
    @FXML
    public void onCanvasMouseDragged(MouseEvent event) {
            /* if particle trace radio button is selected,
    	* keep drawing particle trace.
	*/
        if (optionsToggleGroup.getSelectedToggle() == selectParticleTrace) {
            canvas.getGraphicsContext2D().moveTo(event.getX(), event.getY());
            drawParticleTrace(event);
        }
    }

    /**
     * Stops drawing when mouse is released, if appropriate.
     *
     * @param event event.
     */
    @FXML
    public void onCanvasMouseReleased(MouseEvent event) {
        //If straight line radio button is selected, draw a straight line.
        if (optionsToggleGroup.getSelectedToggle() == selectStraightLine) {
            drawStraightLine(event);
        } else if (optionsToggleGroup.getSelectedToggle()
                == selectParticleTrace) {
    		/* Else if particle trace radio button is selected, 
    		 * finish drawing particle trace.
    		 */
            canvas.getGraphicsContext2D().moveTo(event.getX(),
                    event.getY());
            customAvatar.addComponent(currentParticleTrace);
            drawParticleTrace(event);
        }
    }

    /**
     * Saves the custom avatar when the "Save Image" button is pressed.
     * Returns to either profile or registration depending on where the page was accessed from.
     *
     * @param event event.
     * @throws IOException Thrown if Profile.fxml can't be loaded.
     */
    @FXML
    public void onSaveImageAction(ActionEvent event) throws IOException {

        //Get the absolute path of the project.
        File directory = new File("./");
        String path = directory.getAbsolutePath();
      
        String fileName;

        //Checks whether it came from profile or register pages.
        if (prevScene=="profile") {

    	/* The filename of the image will be the users username followed by the
    	 * time it was created in milliseconds as a png file.
    	 */
            fileName = user.getUsername() + System.currentTimeMillis()
                    + ".png";

            convertToFile(fileName, path);

            //Update system and database with new avatar.
<<<<<<< HEAD
            SavedAvatar newAvatar = createSavedAvatar(fileName);
            user.setAvatar("/Group6/SavedAvatars/" + fileName);
=======
            //SavedAvatar newAvatar = createSavedAvatar(path);
            user.setAvatar("SavedAvatars/" + fileName);
            changeScene(event, "/fxml/profileScene.fxml");
>>>>>>> 84c0c4308c6470c8d9d32d387f529bb922d4d710
            
            
        } else {
            //Create file name.
            fileName = System.currentTimeMillis() + ".png";

            convertToFile(fileName, path);
   
            String total = path.substring(0, path.length()-1)+"src/SaveAvatar/"+fileName;
            createSavedAvatar(total);

            //Set the relative path.
<<<<<<< HEAD
            customDrawingFileLocation = "/Group6/SavedAvatars/"
=======
            customDrawingFileLocation = "SavedAvatars/"
>>>>>>> 84c0c4308c6470c8d9d32d387f529bb922d4d710
                    + fileName;
        }
    }

    /**
     * Convert custom avatar to file.
     *
     * @param path     location of directory.
     * @param fileName file name.
     */
    public void convertToFile(String fileName, String path) {

        //Create path for File class as absolute path to project

        path = path.substring(0, path.length() - 2)
<<<<<<< HEAD
                + "/Group6/SavedAvatars/"
=======
                + "/src/SavedAvatars/"
>>>>>>> 84c0c4308c6470c8d9d32d387f529bb922d4d710
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
                        canvas.snapshot(null, null), null);
                ImageIO.write(bImage, "png", file);
            } catch (Exception e) {
                /* If an exception is caused, print the error message
                 * on the console.
                 */
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * Creates file relative to the project.
     *
     * @param fileName name of file.
     * @return the avatar.
     */
    public SavedAvatar createSavedAvatar(String fileName) {
        //Create a new Saved Avatar.
<<<<<<< HEAD
        return new SavedAvatar(
                "/Group6/SavedAvatars/"
                        + fileName);
=======
        return new SavedAvatar(fileName);
>>>>>>> 84c0c4308c6470c8d9d32d387f529bb922d4d710
    }

    /**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML 
	 */
	public void changeScene(ActionEvent event, String sceneFXML) {
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
     * Draws a straight line.
     *
     * @param event event.
     */
    public void drawStraightLine(MouseEvent event) {
		/* If the mouses previous position was the reset position, set 
		 * the previous position to the current position.
		 */
        if ((mouseX == MOUSE_RESET_POSITION)
                && (mouseY == MOUSE_RESET_POSITION)) {
            mouseX = event.getX();
            mouseY = event.getY();
        } else {
			/* Else draw a straight line between the 
			 * previous position and current position of
			 * the mouse.
			 */
            Color lineColour = Color.BLACK;
            StraightLine s = new StraightLine(mouseX, mouseY,
                    event.getX(), event.getY(), lineColour);
            s.displayComponent(canvas);
            customAvatar.addComponent(s);

            //Reset the previous mouse position.
            mouseX = MOUSE_RESET_POSITION;
            mouseY = MOUSE_RESET_POSITION;
        }
    }

    /**
     * Draws a new circle on the particle trace.
     *
     * @param event event.
     */
    public void drawParticleTrace(MouseEvent event) {
        //Creates a black circle at the current position of the mouse.
        Color lineColour = Color.BLACK;
        Circle circle = new Circle(event.getX(), event.getY(),
                lineColour, 5);
		
		/* Adds the circle to the particle trace and draws 
		 * it on the canvas.
		 */
        currentParticleTrace.addToTrace(circle);
        circle.displayComponent(canvas);
    }

	public void setUser(Person user) {
		this.user = user;
	}

	public void setPrevScene(String prevScene) {
		prevScene = this.prevScene;
		
	}
}

