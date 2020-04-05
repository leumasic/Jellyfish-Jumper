package components;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bubble {
    private double radius;
    private Color color;

    private double xPosition, yPosition;
    private double ySpeed;

    public Bubble(double xPosition, double yPosition) {
        
        // Set initial coordinates of the bubble
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        // Randomly set radius of the bubble - betweem 10px and 40px
        radius  = 10 + (Math.random() * 30);

        // Set the velocity of the bubble - between 350 and 450 px/s
        ySpeed = 350 + (Math.random() * 100);
        
        // Set the color of the bubble (blue, 40% opacity)
        color = Color.rgb(0, 0, 255, 0.4);
    }

    public void update(double dt) {
        yPosition += dt * ySpeed;
    }

    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        /* 
          Les coordonnées (x, y) sont les coordonnées dans le niveau
          et ne sont *pas* utilisées pour dessiner directement le rectangle.
        
          On doit plutôt calculer les nouvelles coordonnées (xAffiche, yAffiche)
          qui correspondent à des coordonnées dans le canvas affiché à l'écran,
          en se basant sur l'endroit où la fenêtre du jeu est positionnée.
         */
        double xAffiche = xPosition - fenetreX;
        double yAffiche = yPosition - fenetreY;

        context.setFill(color);
        context.fillRect(xAffiche, yAffiche, 30, 30);
    }
}