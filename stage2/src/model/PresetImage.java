package model;

import javafx.scene.image.Image;

/**
 * Preset image for user profile.
 * @author James Finlayson
 * @version 1.0
 */
public enum PresetImage {
    // Avatars taken from "https://www.flaticon.com/free-icons/avatar".
    IMAGE_1("Avatar1.png"), IMAGE_2("Avatar2.png"), IMAGE_3("Avatar3.png"),
    IMAGE_4("Avatar4.png");

    // The file path to saved avatars.
    private static final String FILE_PATH = "src/SavedAvatars";
    private final Image presetAvatar; // The preset JavaFX image.

    /**
     * Creates a preset avatar.
     *
     * @param fileName The filename of the avatar.
     */
    PresetImage(String fileName) {
        this.presetAvatar = new Image(FILE_PATH + fileName);
    }

    /**
     * Gets the preset avatar as a JavaFX image.
     *
     * @return The preset avatar, as a JavaFX image.
     */
    public Image getAvatar() {
        return presetAvatar;
    }

    /**
     * Converts a preset avatar to a string.
     * @return a string representation of the preset avatar of this image.
     */
    public String toString() {
        return presetAvatar.toString();
    }
}
