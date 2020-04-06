package components;

public class Window extends Entity {

    public Window(double xPosition, double yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    public double getHeight() {
        return 300;
    }
    @Override
    public double getWidth() {
        return 300;
    }
}