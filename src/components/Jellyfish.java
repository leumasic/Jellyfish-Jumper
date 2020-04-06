package components;

public class Jellyfish extends Entity {
    
    public Jellyfish(double xPosition, double yPosition) {
        super(xPosition, yPosition);
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
}