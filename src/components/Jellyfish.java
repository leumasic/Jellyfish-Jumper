package components;

public class Jellyfish extends Entity {
    private Orientation orientation;

    public Jellyfish(double xPosition, double yPosition) {
        super(xPosition, yPosition);

        // Default the jellyfish's orientation to LEFT
        orientation = Orientation.LEFT;
    }

    public void update(double timeDelta) {
        // Update jellyfish's velocities
        xSpeed += timeDelta * xAcceleration;
        ySpeed += timeDelta * yAcceleration;

        // Update jellyfish's position
        xPosition += xSpeed * timeDelta;
        ySpeed += ySpeed * timeDelta;
    }
    public void jump() {
        ySpeed += 600;
    }
    public void moveLeft() {

    }
    public void moveRight() {

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