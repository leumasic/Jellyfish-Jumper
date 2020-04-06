package components;

public class Jellyfish extends Entity {

    // Direction which the jellyfish is facing (LEFT || RIGHT)
    private Orientation orientation;

    // Hardcoded dimensions
    private double width, height;

    public Jellyfish(double xPosition, double yPosition, double width, double height) {
        super(xPosition, yPosition);

        // Default the jellyfish's orientation to LEFT
        orientation = Orientation.LEFT;

        xAcceleration = 0.0;
        yAcceleration = 0.0;
        xSpeed = 0.0;
        ySpeed = 0.0;
        
        this.width = width;
        this.height = height;
    }

    public void update(double timeDelta, double xBound) {
        // Update jellyfish's velocities
        xSpeed += timeDelta * xAcceleration;
        ySpeed += timeDelta * yAcceleration;

        // Update jellyfish's position
        xPosition += xSpeed * timeDelta;
        yPosition += ySpeed * timeDelta;

        if (xPosition + getWidth() / 2 > xBound || xPosition - getWidth() / 2 < 0) {
            xSpeed *= -0.9;
        }

        xPosition = Math.min(xPosition, xBound - getWidth() / 2);
        xPosition = Math.max(xPosition, getWidth() / 2);
    }

    public void jump() {
        ySpeed += 600;
    }
    public void moveLeft() {
        this.xAcceleration = -1200.0;
    }
    public void moveRight() {
        this.xAcceleration = 1200.0;
    }

    @Override
    public double getWidth() {
        return 50;
    }
    @Override
    public double getHeight() {
        return 50;
    }

    public enum Orientation {
        LEFT,
        RIGHT
    }
    public Orientation getOrientation() {return orientation;}
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}