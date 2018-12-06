package model;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author James Finlayson
 * @version 1.0
 */
public class PresetAvatar extends Avatar {
    private PresetAvatar presetAvatar; //The selected preset avatar.

    /**
     * Creates a preset avatar.
     *
     * @param size        The size of the avatar, which is square.
     * @param posX        The x position of the centre of the avatar.
     * @param posY        The y position of the centre of the avatar.
     * @param presetImage The selected preset avatar.
     */
    public PresetAvatar(double size, double posX, double posY,
                              PresetAvatar presetAvatar) {
        super(size, posX, posY);
        this.presetAvatar = presetAvatar;
    }

    /**
     * Resets the selected preset avatar.
     *
     * @param image The selected preset avatar.
     */
    public void setPresetAvatar(PresetAvatar image) {
        this.presetAvatar = image;
    }

    /**
     * Gets the selected preset avatar.
     *
     * @return The selected preset avatar.
     */
    public PresetAvatar getPresetAvatar() {
        return presetAvatar;
    }

    /**
     * Gets the selected preset avatar, as a JavaFX image.
     *
     * @return The selected preset avatar, as a JavaFX image.
     */
    public Image getAvatar() {
        return presetAvatar.getAvatar();
    }

    /**
     * Converts a preset avatar to a string.
     */
    public String toString() {
        String result = "";
        result += super.toString();
        result += "Image:\t" + this.presetAvatar.toString();
        return result;
    }

    /**
     * This should display a preset avatar.
     *
     * @param imageView the image view.
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