package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * @author James Finlayson
 * @version 1.0
 */
public abstract class AvatarComponent {
    public static final int NO_OF_DIMENSIONS = 2;
    public static final int X_INDEX = 0; // The x index in an array.
    public static final int Y_INDEX = 1; // The y index in an array.
    private double[] startPosition; // The start point of the component.
    private Color colour; // The colour of the component.

    /**
     * Creates a avatar component.
     *
     * @param xStartPosition The x start position.
     * @param yStartPosition The y start position.
     * @param colour The colour of the component.
     */
    public AvatarComponent(double xStartPosition, double yStartPosition, Color colour) {
        startPosition = new double[NO_OF_DIMENSIONS];
        this.startPosition[X_INDEX] = xStartPosition;
        this.startPosition[Y_INDEX] = yStartPosition;
        this.colour = colour;
    }

    /**
     * Gets the x start position.
     *
     * @return The x start position.
     */
    public double getXStartPosition() {
        return startPosition[X_INDEX];
    }

    /**
     * Gets the y start position.
     *
     * @return The y start position.
     */
    public double getYStartPosition() {
        return startPosition[Y_INDEX];
    }

    /**
     * Gets the colour of the component.
     *
     * @return The colour of the component.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Resets the x start position.
     *
     * @param xStartPosition x start position.
     */
    public void setXStartPosition(double xStartPosition) {
        this.startPosition[X_INDEX] = xStartPosition;
    }

    /**
     * Resets the y start position.
     *
     * @param yStartPosition y start position.
     */
    public void setYStartPosition(double yStartPosition) {
        this.startPosition[Y_INDEX] = yStartPosition;
    }

    /**
     * Resets the colour of the component.
     *
     * @param colour The colour of the component.
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Converts an avatar component to a string.
     */
    public String toString() {
        String result = "";
        result += "X Starting Position: " + getXStartPosition() + "\n";
        result += "Y Starting Position: " + getYStartPosition() + "\n";
        result += "Colour: " + getColour().toString() + "\n";
        return result;
    }

    /**
     * Displays the component on the canvas provided.
     * 
     * @param canvas The canvas the component will be displayed on.
     */
    public abstract void displayComponent(Canvas canvas);
}