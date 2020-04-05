package components;

public class Window {
    private double yPosition;
    private double ySpeed;

    public Window(double yPosition) {
        // Set initial position
        this.yPosition = yPosition;

        // Initial speed defaults to 50px/s
        this.ySpeed = 50;
    }
    public void updatePosition(double timeDelta) {
        yPosition += ySpeed * timeDelta + Math.pow(timeDelta, 2);
    }
    public void updateSpeed(double timeDelta) {
        ySpeed += timeDelta * 2;
    }
}