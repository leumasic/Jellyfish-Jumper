package components;

public class Window extends Entity {

    public Window(double xPosition, double yPosition) {
        super(xPosition, yPosition, 0, -50, 0, -2);
    }

    public void update(double timeDelta) {
        ySpeed += timeDelta * yAcceleration;
        yPosition += ySpeed * timeDelta;
    }
}