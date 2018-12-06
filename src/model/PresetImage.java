package model;

import javafx.scene.image.Image;

/**
 * @author James Finlayson
 * @version 1.0
 */
public enum PresetImage {
    //Avatars taken from "https://www.flaticon.com/free-icons/avatar".
    IMAGE_1("Avatar1.png"),
    IMAGE_2("Avatar2.png"),
    IMAGE_3("Avatar3.png"),
    IMAGE_4("Avatar4.png");


    private static final String FILE_PATH = "Group6/src/SavedAvatars"; //The file path to saved avatars.
    private final Image presetAvatar; //The preset JavaFX image.

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
     */
    public String toString() {
        return presetAvatar.toString();
    }
}
