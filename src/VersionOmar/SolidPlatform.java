package components;

import javafx.scene.paint.Color;

public class SolidPlatform extends Platform {
    public SolidPlatform(double xPosition, double yPosition, double width, double height) {
        super(xPosition, yPosition, width, height, Color.rgb(184, 15, 36));
    }
}