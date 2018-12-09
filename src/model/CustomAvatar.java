package model;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;

/**
 * @author James Finlayson
 * @version 1.0
 */
public class CustomAvatar extends Avatar {
    // The list of components that create the custom avatar.
    private ArrayList<AvatarComponent> component;

    /**
     * Creates a blank custom avatar.
     *
     * @param size The size of the avatar.
     * @param posX The x centre position.
     * @param posY The y centre position.
     */
    public CustomAvatar(double size, double posX, double posY) {
        super(size, posX, posY);
        component = new ArrayList<AvatarComponent>();
    }

    /**
     * Adds a component to the custom avatar.
     *
     * @param component the component to be added to the custom avatar.
     */
    public void addComponent(AvatarComponent component) {
        this.component.add(component);
    }

    /**
     * Converts a saved avatar to a string.
     */
    public String toString() {
        String result = "";
        result += super.toString();
        return result;
    }

    @Override
    public void displayAvatar(ImageView imageView) {
        // Create a canvas.
        Canvas canvas = new Canvas();

        // Display every component of the custom avatar.
        for (AvatarComponent elem : component) {
            elem.displayComponent(canvas);
        }

        // Add the canvas to the scene.
        imageView.getParent().getChildrenUnmodifiable().add(canvas);
    }
}
