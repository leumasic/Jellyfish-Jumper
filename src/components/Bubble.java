package components;

import javafx.scene.paint.Color;

public class Bubble extends Entity {
    private double radius;
    private Color color;

    public Bubble(double xPosition, double yPosition) {
        super(xPosition, yPosition, 0, -(350 + (Math.random() * 100)), 0, 0);

        // Randomly set radius of the bubble - betweem 10px and 40px
        radius = 10 + (Math.random() * 30);

        // Set the color of the bubble (blue, 40% opacity)
        color = Color.rgb(0, 0, 255, 0.4);
    }

    public void update(double dt) {
        yPosition += dt * ySpeed;
    }

    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }
}