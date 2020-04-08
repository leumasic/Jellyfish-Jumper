import java.util.ArrayList;
import java.util.LinkedList;

import components.*;

public class Game {

    private int numPlatforms = 15;
    private double verticalSpaceBetweenPlatforms = 100;
    private int numPlatformsToUpdate = 5;

    protected Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Bubble> bubbles;
    private LinkedList<Platform> platforms;

    private boolean gameOver;

    private double width, height;
    private long score;
    private boolean jellyfishOnPlatform;

    public Game(double width, double height) {

        // Set the bounds of the game
        this.width = width;
        this.height = height;

        // Set whether or not the game has started
        this.gameOver = false;

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(width / 2, height - 50, 50, 50);
        this.jellyfishOnPlatform = true;

        // Instantiate window
        this.window = new Window(0, 0);

        // Instantiate platforms except that the first must be 
        this.platforms = new LinkedList<Platform>();
        this.platforms.add(new SimplePlatform(0, height, width, 10));
        addNPlatforms(numPlatforms, height - verticalSpaceBetweenPlatforms);
    }

    public void startWindow() {
        this.window.setVerticalAcceleration(-2);
        this.window.setVerticalVelocity(-50);
    }

    public void stopWindow() {
        this.window.setVerticalAcceleration(0);
        this.window.setVerticalVelocity(0);
    }

    public void restartGame() {
        // Set playing to false
        this.gameOver = false;

        // Make the jellyfish immobile
        this.jellyfish.setHorizontalAcceleration(0);
        this.jellyfish.setHorizontalVelocity(0);
        this.jellyfish.setVerticalAcceleration(0);
        this.jellyfish.setVerticalVelocity(0);

        // Set the jellyfish to initial position
        this.jellyfish.setX(width / 2);
        this.jellyfish.setY(height - 50);
        this.jellyfishOnPlatform = true;

        // Set the window's position
        this.window.setY(0);
    }

    public void updateGame(double timeDelta) {
        // If jellyfish is below the windows's vertical position, then game over
        if (jellyfish.getY() - window.getY() > height) {
            gameOver = true;
            System.out.println("Game restarted!");
        }
    }

    public void updateScore() {
        this.score = (long) Math.abs(window.getY());
    }

    public void updatePlatforms() {
        if (numPlatformsToUpdate > numPlatforms) {
            throw new IllegalArgumentException("Number of platforms to verify greater than that in memory");
        }

        // Update the platforms after numPlatformsToVerify of them are below the window
        if (platforms.peek().getY() - window.getY() > verticalSpaceBetweenPlatforms * numPlatformsToUpdate) {
            removeFirstPlatforms(numPlatformsToUpdate);
            addNPlatforms(numPlatformsToUpdate, platforms.peekLast().getY());
        }
    }

    public void updateJellyfish(double timeDelta) {

        jellyfish.update(timeDelta, width);

        for (Platform platform : platforms) {

            if (jellyfish.getY() + jellyfish.getHeight() <= platform.getY() + platform.getHeight()
                    && jellyfish.getY() + jellyfish.getHeight() >= platform.getY()
                    && jellyfish.getX() >= platform.getX() && jellyfish.getX() <= platform.getX() + platform.getWidth()
                    && jellyfish.getVerticalVelocity() <= 0) {

                System.out.println("Jelly on platform!");
                jellyfishOnPlatform = true;
                break;
            } else {
                jellyfishOnPlatform = false;
            }
        }

        if (jellyfishOnPlatform) {
            jellyfish.setVerticalAcceleration(0);
            jellyfish.setVerticalVelocity(0);
        } else {
            jellyfish.setVerticalAcceleration(1200);
        }

    }

    public void updateWindow(double timeDelta) {
        window.update(timeDelta);
    }

    public void updateBubbles(double timeDelta) {
        // Show bubbles if % 5 seconds

    }

    public void jellyfishJump() {
        if (jellyfishOnPlatform) {
            jellyfish.jump();
            jellyfishOnPlatform = false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public double getScore() {
        return score;
    }

    /**
     * Méthode qui enlève N plateformes à l'attribut "platforms"
     * @param n
     */
    private void removeFirstPlatforms(int n) {
        if (n > numPlatforms) {
            throw new IllegalArgumentException("Number of platforms to remove greater than that in memory");
        }

        for (int i = 0; i < n; i++) {
            platforms.poll();
        }
    }

    /**
     * Méthode qui ajoute n plateformes à l'attribut "platforms" qui sont verticalement espacés de 
     * la propriété verticalSpaceBetweenPlatforms, commençant à la position startY
     * @param n
     * @param startY
     */
    private void addNPlatforms(int n, double startY) {
        
        // Une variable pour garder en mémoire le type de la dernière plateforme
        boolean lastAddedWasSolid = false;

        for (int i = 0; i < n; i++) {
            Platform platformToAdd;

            // Nombre aléatoire entre 0 et 1
            double rand = Math.random();

            // Largeur de la plateforme aléatoire, entre 80 et 175
            double platformWidth = 80.0 + (95.0 * rand);

            // Position X de la plateforme aléatoire mais dépendante de sa largeur 
            double platformX = rand * (width - platformWidth);

            // Position Y de la plateforme aléatoire, au dessus de la dernière plateforme
            double platformY = startY + (-i * verticalSpaceBetweenPlatforms);
            
            if (!lastAddedWasSolid) {
                // Type de plateforme déterminée aléatoirement (selon les consignes)
                if (rand < 0.65) {
                    platformToAdd = new SimplePlatform(platformX, platformY, platformWidth, 10);
                } else if (rand >= 0.65 && rand < 0.85) {
                    platformToAdd = new BouncyPlatform(platformX, platformY, platformWidth, 10);
                } else if (rand >= 0.85 & rand < 0.95) {
                    platformToAdd = new AcceleratingPlatform(platformX, platformY, platformWidth, 10);
                } else {
                    platformToAdd = new SolidPlatform(platformX, platformY, platformWidth, 10);
                    lastAddedWasSolid = true;
                }
            } else {
                // Type de plateforme ne peut pas être solide puisque la dernière l'était:
                // Nous avons décidé que la prochaine serait une plateforme simple
                platformToAdd = new SimplePlatform(platformX, platformY, platformWidth, 10);
                lastAddedWasSolid = false;
            }

            // Ajouter la plateforme à l'ensemble des plateformes
            platforms.add(platformToAdd);
        }
    }

    public double getverticalSpaceBetweenPlatforms() {
        return verticalSpaceBetweenPlatforms;
    }

    public int getNumPlatforms() {
        return numPlatforms;
    }

    public LinkedList<Platform> getPlatforms() {
        return platforms;
    }
}
