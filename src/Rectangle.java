import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public class Rectangle {

    private double x, y, vx, vy;
    private Color color;

    public Rectangle(double x, double y) {
        this.x = x;
        this.y = y;

        // Couleur aléatoire
        color = Color.hsb(Math.random() * 360, 0.8, 0.8);

        // Vitesse aléatoire
        vx = Math.random() * 20 - 10;
        vy = Math.random() * 20 - 10;
    }

    public void update(double dt) {
        x += dt * vx;
        y += dt * vy;
    }

    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        /* 
          Les coordonnées (x, y) sont les coordonnées dans le niveau
          et ne sont *pas* utilisées pour dessiner directement le rectangle.
        
          On doit plutôt calculer les nouvelles coordonnées (xAffiche, yAffiche)
          qui correspondent à des coordonnées dans le canvas affiché à l'écran,
          en se basant sur l'endroit où la fenêtre du jeu est positionnée.
         */
        double xAffiche = x - fenetreX;
        double yAffiche = y - fenetreY;

        context.setFill(color);
        context.fillRect(xAffiche, yAffiche, 30, 30);
    }
}
