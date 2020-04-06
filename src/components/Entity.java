package components;

public abstract class Entity {
    protected double xPosition, yPosition;
    protected double xSpeed, ySpeed;
    protected double xAcceleration, yAcceleration;

    public Entity(double xPosition, double yPosition, double xSpeed, double ySpeed, double xAcceleration,
            double yAcceleration) {

        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }

    public double getX() {
        return xPosition;
    }

    public double getY() {
        return yPosition;
    }

    public double getHorizontalAcceleration() {
        return xAcceleration;
    }

    public void setHorizontalAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public double getVerticalAcceleration() {
        return yAcceleration;
    }

    public void setVerticalAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public void setHorizontalVelocity(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setVerticalVelocity(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getHorizontalVelocity() {
        return xSpeed;
    }

    public double getVerticalVelocity() {
        return ySpeed;
    }

    public void setX(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setY(double yPosition) {
        this.yPosition = yPosition;
    }
}