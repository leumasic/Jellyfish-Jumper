package components;

public class Platform {
    private double width, height;
    private double xPosition, yPosition;

    public Platform(double width, double height) {

        // Set the width and the height of the platform
        this.width = width;
        this.height = height;

        /**
         * 
         */
        double rand = Math.random();
    }
}

enum State {
    PASSTHROUGH,
    SOLID,
}
