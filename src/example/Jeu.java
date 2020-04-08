package example;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public class Jeu {

    // Largeur, hauteur du niveau
    private double width = 300, height = 300;

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    // Entités dans le jeu
    private ArrayList<Rectangle> rectangles;

    public Jeu() {
        rectangles = new ArrayList<>();

        // Crée 10 rectangles à des positions aléatoires dans les dimensions du niveau
        for (int i = 0; i < 1; i++) {
            rectangles.add(new Rectangle(Math.random() * width, Math.random() * height));
        }
    }

    // Les flèches déplacent l'origine de la fenêtre
    public void gauche() {
        fenetreX -= 10;
    }

    public void droite() {
        fenetreX += 10;
    }

    public void haut() {
        fenetreY -= 10;
    }

    public void bas() {
        fenetreY += 10;
    }

    public void update(double dt) {
        for (Rectangle r : rectangles) {
            r.update(dt);
        }
    }

    public void draw(GraphicsContext context) {
        for (Rectangle r : rectangles) {
            /* Chaque entité doit être dessinée en considérant
               l'origine de la fenêtre qui est affichée */
            r.draw(context, fenetreX, fenetreY);
        }

        context.setFill(Color.BLACK);
        context.fillText("Utilisez les flèches pour déplacer la fenêtre", 5, 15);
        context.fillText("Origine de la fenêtre: (" + fenetreX + ", " + fenetreY + ")", 5, 30);
    }
}
