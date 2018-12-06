package model;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author James Finlayson
 * @version 1.0
 */
public class SavedAvatar extends Avatar {
    //The default size of a saved avatar.
    public static final double DEFAULT_SIZE = 400;
    private Image Avatar; //The saved avatar, as a JavaFX image.

    /**
     * Creates a saved avatar of default size.
     *
     * @param fileName The filename of the saved avatar.
     */
    public SavedAvatar(String fileName) {
        super(DEFAULT_SIZE, 0, 0);
        this.Avatar = new Image(fileName);
    }

    /**
     * Creates a saved avatar.
     *
     * @param size     The size of the avatar, which is square.
     * @param posX     The x position of the top left of the avatar.
     * @param posY     The y position of the top left of the avatar.
     * @param fileName The filename of the saved avatar.
     */
    public SavedAvatar(int size, int posX, int posY,
                             String fileName) {
        super(size, posX, posY);
        this.Avatar = new Image(fileName);
    }

    /**
     * Gets the saved avatar, as a JavaFX image.
     *
     * @return The saved avatar, as a JavaFX image.
     */
    public Image getAvatar() {
        return Avatar;
    }

    /**
     * Resets the saved avatar.
     *
     * @param fileName The filename of the saved avatar.
     */
    public void setAvatar(String fileName) {
        Avatar = new Image(fileName);
    }

    /**
     * Converts a saved avatar to a string.
     */
    public String toString() {
        String result = "";
        result += super.toString();
        result += "Image:\t" + this.Avatar.toString();
        return result;
    }

    /**
     * Displays a preset avatar.
     *
     * @param imageView The ImageView scene object the image is being displayed on.
     */
    @Override
    public void displayAvatar(ImageView imageView) {
        imageView.setImage(getAvatar());
        imageView.setTranslateX(getXPosition());
        imageView.setTranslateY(getYPosition());
        imageView.setFitWidth(getSize());
        imageView.setFitHeight(getSize());
        imageView.setPreserveRatio(true);
    }
}
