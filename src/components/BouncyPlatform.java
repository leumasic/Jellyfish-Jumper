package components;

import javafx.scene.paint.Color;

public class BouncyPlatform extends Platform {
    public BouncyPlatform(double xPosition, double yPosition, double width, double height) {
        super(xPosition, yPosition, width, height, Color.LIGHTGREEN);
    }
}