package components;

public class Jellyfish extends Entity {

    // Direction which the jellyfish is facing (LEFT || RIGHT)
    private Orientation orientation;

    // Hardcoded dimensions
    private double width, height;

    public Jellyfish(double xPosition, double yPosition, double width, double height) {
        super(xPosition, yPosition, 0, 0, 0, 1200);

        // Default the jellyfish's orientation to LEFT
        orientation = Orientation.LEFT;

        this.width = width;
        this.height = height;
    }

    public void update(double timeDelta, double bound) {
        
        // Update jellyfish's velocities
        xSpeed += timeDelta * xAcceleration;
        ySpeed += timeDelta * yAcceleration;

        // Update jellyfish's position
        xPosition += xSpeed * timeDelta;
        yPosition += ySpeed * timeDelta;

        // Force à rester dans les bornes de l'écran
        if (xPosition + width > bound || xPosition < 0) {
            xSpeed *= -1;
        }
        
        xPosition = Math.min(xPosition, bound - width);
        xPosition = Math.max(xPosition, 0);
    }

    public void jump() {
        ySpeed += -600.0;
    }
    public void moveLeft() {
        this.xAcceleration = -1200.0;
    }
    public void moveRight() {
        this.xAcceleration = 1200.0;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public enum Orientation {
        LEFT,
        RIGHT
    }
    public Orientation getOrientation() {return orientation;}
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}