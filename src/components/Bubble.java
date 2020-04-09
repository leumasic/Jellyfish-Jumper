package components;
import javafx.scene.paint.Color;

public class Bubble {
    private double radius;
    private Color color;

    private double xPosition, yPosition;
    private double ySpeed;

    public Bubble(double xPosition, double yPosition) {
        
        // Set initial coordinates of the bubble
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        // Randomly set radius of the bubble - betweem 10px and 40px
        radius  = 10 + (Math.random() * 30);

        // Set the velocity of the bubble - between 350 and 450 px/s
        ySpeed = 350 + (Math.random() * 100);
        
        // Set the color of the bubble (blue, 40% opacity)
        color = Color.rgb(0, 0, 255, 0.4);
    }

    public void update(double dt) {
        yPosition += dt * ySpeed;
    }
}