package components;
import javafx.scene.paint.Color;

public class Bubble {
    private double width,heigth;
    private double radius;
    private Color color;

    private double xPosition, yPosition;
    private double ySpeed;

    public Bubble(double xPosition, double yPosition) {

        this.width= xPosition;
        this.heigth= yPosition;
        // Set X coordinate of the bubble
        this.xPosition = xPosition * Math.random();
        if ( (xPosition + 40) > width) {this.xPosition= xPosition -40;} 
        if ( (xPosition - 40) < 0)     {this.xPosition= xPosition +40;}

        this.yPosition = yPosition;

        // Randomly set radius of the bubble - betweem 10px and 40px
        this.radius  = 10 + (Math.random() * 30);

        // Set Y coordinate of the bubble
        this.yPosition = yPosition + radius;
        


        // Set the velocity of the bubble - between 350 and 450 px/s
        ySpeed = 350 + (Math.random() * 100);
        
        // Set the color of the bubble (blue, 40% opacity)
        color = Color.rgb(0, 0, 255, 0.4);
    }

    public void update(double dt) {
        yPosition += dt * ySpeed;
    }

    public double getX() {return xPosition;}
    public double getY() {return yPosition;}

    public void  setX() {
        this.xPosition = width * Math.random();
        if ( (xPosition + 40) > width) {this.xPosition= xPosition -40;} 
        if ( (xPosition - 40) < 0)     {this.xPosition= xPosition +40;}
    }

    public void setR()  {
        this.radius  = 10 + (Math.random() * 30);
    }    

    public void  setY() {
        this.yPosition = heigth + radius;

    }

    public double getWidth() {return radius;}
    public double getHeight() {return radius;}
    public Color getColor() {return color;}


}