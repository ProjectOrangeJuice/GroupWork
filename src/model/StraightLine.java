package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class which holds information about a straight line
 * @author James Finlayson
 * @version 1.0
 */
public class StraightLine extends AvatarComponent {
    // The point at which the straight line finishes.
    private double[] finishPosition;

    /**
     * Creates a straight line.
     *
     * @param xStartPosition The x start position.
     * @param yStartPosition The y start position.
     * @param xFinishPosition The x end position.
     * @param yFinishPosition The y end position.
     * @param colour The colour of the straight line.
     */
    public StraightLine(double xStartPosition, double yStartPosition,
        double xFinishPosition, double yFinishPosition, Color colour) {
        super(xStartPosition, yStartPosition, colour);
        finishPosition = new double[NO_OF_DIMENSIONS];
        this.finishPosition[X_INDEX] = xFinishPosition;
        this.finishPosition[Y_INDEX] = yFinishPosition;
    }

    /**
     * Gets the x end position.
     *
     * @return The x end position.
     */
    public double getXFinishPosition() {
        return finishPosition[X_INDEX];
    }

    /**
     * Gets the y end position.
     *
     * @return The y end position.
     */
    public double getYFinishPosition() {
        return finishPosition[Y_INDEX];
    }

    /**
     * Resets the x end position.
     *
     * @param xFinishPosition The x end position.
     */
    public void setXFinishPosition(double xFinishPosition) {
        this.finishPosition[X_INDEX] = xFinishPosition;
    }

    /**
     * Resets the y end position.
     *
     * @param yFinishPosition The y end position..
     */
    public void setYFinishPosition(double yFinishPosition) {
        this.finishPosition[Y_INDEX] = yFinishPosition;
    }

    /**
     * Converts a straight line to a string.
     * @return a string representation of the straight line.
     */
    public String toString() {
        String result = "";
        result += super.toString();
        result += "X Finishing Position: " + getXFinishPosition() + "\n";
        result += "Y Finishing Position: " + getYFinishPosition() + "\n";
        return result;
    }

    @Override
    public void displayComponent(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2);
        graphicsContext.setFill(getColour());
        graphicsContext.setStroke(getColour());
        graphicsContext.strokeLine(getXStartPosition(), getYStartPosition(),
            getXFinishPosition(), getYFinishPosition());
    }

}
