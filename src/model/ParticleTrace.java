package model;

import java.util.LinkedList;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * @author James Finlayson
 * @version 1.0
 */
public class ParticleTrace extends AvatarComponent {
	private LinkedList<Circle> particleTrace;
	
	/**
	 * Creates a blank particle trace.
	 * @param xStartPosition The x start coordinate.
	 * @param yStartPosition The y start coordinate.
	 * @param colour The colour of the component.
	 */
	public ParticleTrace(double xStartPosition, double yStartPosition, Color colour) {
		super(xStartPosition, yStartPosition, colour);
		particleTrace = new LinkedList<Circle>();
	}

	/**
	 * Extends the particle trace.
	 * @param c The circle that extends the particle trace.
	 */
	public void addToTrace(Circle c) {
		particleTrace.add(c);
	}
	
	@Override
	public void displayComponent(Canvas canvas) {
	}
}