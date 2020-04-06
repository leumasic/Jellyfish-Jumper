package components;

public class Platform extends Entity {
    private double width, height;

    private Boolean bouncy;
    private Boolean accelerating;
    private Boolean solid;

    public Platform(double xPosition, double yPosition, double width, double height) {
        super(xPosition, yPosition, 0, 0, 0, 0);
        
        // Set the width and the height of the platform
        this.width = width;
        this.height = height;

    }
}