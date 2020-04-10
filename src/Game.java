import java.util.ArrayList;
import java.util.LinkedList;

import components.*;

public class Game {

    // Nombre de plateformes gardées en mémoire (choisie comme bon vous semble)
    private int numPlatforms = 15;
    
    // Nombre de plateformes à mettre updater
    private int numPlatformsToUpdate = 2;

    private double verticalSpaceBetweenPlatforms = 100;

    protected Window window;
    protected Jellyfish jellyfish;
    private LinkedList<Platform> platforms;

    private boolean gameStarted;

    private double width, height;
    private long score;
    private boolean jellyfishOnPlatform;

    public Game(double width, double height) {

        // Verifies whether or not the number of platforms to update is greater than the number of platforms
        if (numPlatformsToUpdate > numPlatforms) {
            throw new IllegalArgumentException("Number of platforms to verify greater than that in memory");
        }

        // Set the bounds of the game
        this.width = width;
        this.height = height;

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(width / 2, height - 50, 50, 50);
        this.jellyfishOnPlatform = true;

        // Instantiate window
        this.window = new Window(0, 0);

        // Instantiate platforms except that the first must be right below
        // the jellyfish's initial position
        this.platforms = new LinkedList<Platform>();
        this.platforms.add(new SimplePlatform(0, height, width, 10));
        addNPlatforms(numPlatforms, height - verticalSpaceBetweenPlatforms);

        // Set the status of the game
        this.gameStarted = false;
    }

    /**
     * Starts moving the window upward at:
     * -> A speed of 50px/s
     * -> An acceleration of 2px/s squared
     */
    public void startWindow() {
        this.window.setVerticalAcceleration(-2);
        this.window.setVerticalVelocity(-50);
    }

    /**
     * Stops the window from moving
     */
    public void stopWindow() {
        this.window.setVerticalAcceleration(0);
        this.window.setVerticalVelocity(0);
    }

    /**
     * Method that restarts the game
     */
    private void restartGame() {

        // Make the jellyfish immobile
        this.jellyfish.setHorizontalAcceleration(0);
        this.jellyfish.setHorizontalVelocity(0);
        this.jellyfish.setVerticalVelocity(0);

        // Stop the window from moving
        this.stopWindow();

        // Set the window's position back to 0
        this.window.setY(0);

        // Set the jellyfish to initial position
        this.jellyfish.setX(width / 2);
        this.jellyfish.setY(height - jellyfish.getHeight());

        // Renew the platforms
        this.platforms = new LinkedList<Platform>();
        this.platforms.add(new SimplePlatform(0, height, width, 10));
        addNPlatforms(numPlatforms, height - verticalSpaceBetweenPlatforms);

        // Set the status of the game
        this.gameStarted = false;
    }

    /**
     * Vérifie si la méduse est en dessous de la fenêtre; auquel cas le jeu
     * recommence
     * 
     * @param timeDelta
     */
    public void updateGame(double timeDelta) {
        // If jellyfish is below the windows's vertical position, then game over
        if (jellyfish.getY() - window.getY() > height) {
            restartGame();
        }
    }

    /**
     * Updates the score by casting the window's height (double) into a long
     */
    public void updateScore() {
        this.score = (long) Math.abs(window.getY());
    }

    /**
     * Méthode qui enlève les plateformes en-dessous de la fenêtre s'il y en a
     * numPlatformsToUpdate en dessous d'elle.
     * 
     * Elle ajoute "numPlatformsToUpdate" plateformes s'il y a des plateformes
     * enlevées
     */
    public void updatePlatforms() {
        // Update the platforms after numPlatformsToVerify of them are below the window
        if (platforms.peek().getY() - (window.getY() + height) > verticalSpaceBetweenPlatforms * numPlatformsToUpdate) {
            removeFirstPlatforms(numPlatformsToUpdate);
            addNPlatforms(numPlatformsToUpdate, platforms.peekLast().getY() - verticalSpaceBetweenPlatforms);
        }
    }

    /**
     * Updates the position of the jellyfish
     * @param timeDelta
     */
    public void updateJellyfish(double timeDelta) {

        jellyfishOnPlatform = false;

        for (Platform platform : platforms) {
            testCollision(platform);
        }

        jellyfish.update(timeDelta, width);
    }

    /**
     * Updates the position of the window.
     * 
     * @param timeDelta
     */
    public void updateWindow(double timeDelta) {

        // If the jellyfish is above 75% of the window, then move the window up by a bit
        if (jellyfish.getY() < window.getY() + height / 4) {
            window.setY(window.getY() - 2);
        }

        // Update the window's position
        window.update(timeDelta);
    }

    /**
     * TODO -> Updates the bubbles
     * @param timeDelta
     */
    public void updateBubbles(double timeDelta) {
        // Show bubbles if % 5 seconds

    }

    /**
     * Makes the jellyfish jump if it is on a platform
     */
    public void jellyfishJump() {
        if (jellyfishOnPlatform) {
            jellyfish.jump();
        }
    }

    /**
     * Méthode qui enlève N plateformes à la tête de l'attribut "platforms"
     * 
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
     * Méthode qui ajoute n plateformes à l'attribut "platforms" qui sont
     * verticalement espacés de la propriété verticalSpaceBetweenPlatforms,
     * commençant à la position startY
     * 
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

    /**
     * Method that tests if the jellyfish collides against a platform
     * @param other
     */
    public void testCollision(Platform other) {
        /**
         * La collision avec une plateforme a lieu seulement si :
         *
         * - Il y a une intersection entre la plateforme et le personnage
         *
         * - La collision a lieu entre la plateforme et le *bas du personnage* seulement
         *
         * - La vitesse va vers le bas (le personnage est en train de tomber, pas en
         * train de sauter)
         */
        if (intersects(other) && Math.abs(jellyfish.getY() + jellyfish.getHeight() - other.getY()) < 10
                && jellyfish.getVerticalVelocity() > 0) {
            
            /**
             * 
             */
            if (other instanceof AcceleratingPlatform) {
                window.setVerticalVelocity(window.getVerticalVelocity() * 1.05);
                pushOut(other);
                jellyfish.setVerticalVelocity(0);
                jellyfishOnPlatform = true;
            } else if (other instanceof BouncyPlatform) {
                jellyfish.setVerticalVelocity(Math.min(-100,jellyfish.getVerticalVelocity() * - 1.5));
            } else {
                pushOut(other);
                jellyfish.setVerticalVelocity(0);
                jellyfishOnPlatform = true;
            }
            
        }

        /**
         * Ce bloc "if" vérifie si la méduse essaie de sauter à travers une plateforme solide. Pour ce faire, quatres
         * conditions doivent évaluer à vrai:
         * 
         * - La plateforme est solide (SolidPlatform)
         * 
         * - La méduse intersecte la plateforme
         * 
         * - La tête de la méduse est entre le bas et le haut de la plateforme 
         * 
         * - La méduse a une vitesse vers le haut
         */
        if (intersects(other) && Math.abs(jellyfish.getY() - other.getY()) < 10 && jellyfish.getVerticalVelocity() < 0) {
            if (other instanceof SolidPlatform) {
                jellyfish.setVerticalVelocity(0);
            }
        }
    }

    /**
     * Method that verifies whether or not the jellyfish intersects a platform
     * @param other
     * @return
     */
    public boolean intersects(Platform other) {
        return !( // Un des carrés est à gauche de l’autre
        jellyfish.getX() + jellyfish.getWidth() < other.getX() || other.getX() + other.getWidth() < jellyfish.getX()
        // Un des carrés est en haut de l’autre
                || jellyfish.getY() + jellyfish.getHeight() < other.getY()
                || other.getY() + other.getHeight() < jellyfish.getY());
    }

    /**
     * Method that places the jellyfish above a platform
     * @param other
     */
    public void pushOut(Platform other) {
        double deltaY = jellyfish.getY() + jellyfish.getHeight() - other.getY();
        jellyfish.setY(jellyfish.getY() - deltaY);
    }

    /**
     * Getters and setters below
     */

     public double getverticalSpaceBetweenPlatforms() {
        return verticalSpaceBetweenPlatforms;
    }

    public int getNumPlatforms() {
        return numPlatforms;
    }

    public int getNumPlatformsToUpdate() {
        return numPlatformsToUpdate;
    }

    public LinkedList<Platform> getPlatforms() {
        return platforms;
    }

    public boolean hasGameStarted() {
        return gameStarted;
    }

    public long getScore() {
        return score;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
