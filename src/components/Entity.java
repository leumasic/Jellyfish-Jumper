package components;

public abstract class Entity {
    protected double xPosition, yPosition;
    protected double xSpeed, ySpeed;
    protected double xAcceleration, yAcceleration;

    public Entity(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public double getX() {return xPosition;}
    public double getY() {return yPosition;}

    public abstract double getWidth();
    public abstract double getHeight();

    // public void update(double timeDelta) {
    //     xSpeed += timeDelta * xAcceleration;
    //     ySpeed += timeDelta * yAcceleration;

    //     xPosition += xSpeed * timeDelta;
    //     ySpeed += ySpeed * timeDelta;

    //     if (xPosition + getWidth() / 2 > 300 || xPosition - getWidth() / 2 < 0) {
    //         xSpeed *= -0.9;
    //     }

    //     if (yPosition + getHeight() / 2 > 300 || yPosition - getHeight() / 2 < 0) {
    //         ySpeed *= -0.9;
    //     }

    //     xPosition = Math.min(xPosition, 300 - getWidth() / 2);
    //     xPosition = Math.max(xPosition, getWidth() / 2);
    //     yPosition = Math.min(yPosition, 300 - getHeight() / 2);
    //     yPosition = Math.max(yPosition, getHeight() / 2);
    // }
}