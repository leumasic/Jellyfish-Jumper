package components;

import javafx.scene.paint.Color;

public abstract class Platform extends Entity {
    private double width, height;
    private Color color;
    Color memoColor;

    public Platform(double xPosition, double yPosition, double width, double height, Color color) {
        super(xPosition, yPosition, 0, 0, 0, 0);

        // Set the width and the height of the platform
        this.width = width;
        this.height = height;
        
        this.color = color;
        this.memoColor=color;
    }

    public double getWidth() {return width;}
    public double getHeight() {return height;}
    public Color getColor() {return color;}
    public void setYellow() {
        this.color= Color.YELLOW;

    }
    public void resetYellow() {
        this.color= memoColor;
    }

}