package model;
import javafx.scene.image.ImageView;

/**
 * @author James Finlayson
 */
public abstract class Avatar {
    //Number of dimensions an avatar has
    public static final int NO_OF_DIMENSIONS = 2;
    public static final int X_INDEX = 0; //x index
    public static final int Y_INDEX = 1; //y index
    private double size; //size of the avatar
    private double[] position; //centre x,y coordinates 

    /**
     * Creates an avatar.
     *
     * @param size 
     * @param posX 
     * @param posY 
     */
    public Avatar(double size, double posX, double posY) {
        this.size = size;
        position = new double[NO_OF_DIMENSIONS];
        position[X_INDEX] = posX;
        position[Y_INDEX] = posY;
    }

    /**
     * @return The size of the avatar.
     */
    public double getSize() {
        return size;
    }

    /**
     * Gets x-axis centre position.
     *
     * @return The x position of the centre of the avatar.
     */
    public double getXPosition() {
        return position[X_INDEX];
    }

    /**
 	 * Gets y-axis centre position
 	 * 
     * @return The y position of the centre of the avatar.
     */
    public double getYPosition() {
        return position[Y_INDEX];
    }

    /**
     * Sets size variable
     *
     * @param size The size of the avatar.
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Sets x-axis centre position
     *
     * @param x The x position at the centre of the avatar.
     */
    public void setXPosition(double x) {
        this.position[X_INDEX] = x;
    }

    /**
     * Sets y-axis centre position     
     * 
     * @param y The y position at the centre of the avatar.
     */
    public void setYPosition(double y) {
        this.position[Y_INDEX] = y;
    }

    /**
     * Converts an avatar to a String.
     */
    public String toString() {
        String result = "";
        result += "Size:\t" + getSize() + "\n";
        result += "X Position:\t" + getXPosition() + "\n";
        result += "Y Position:\t" + getYPosition() + "\n";
        return result;
    }

    /**
     * Displays the avatar on an ImageView.
     * @param imageView The ImageView that the avatar is being displayed on.
     */
    public abstract void displayAvatar(ImageView imageView);
}