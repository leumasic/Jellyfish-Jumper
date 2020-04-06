import java.util.ArrayList;

import components.*;

public class Game {

    protected Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private Boolean gameOver;

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
        if (jellyfish.getY() - window.getY() > 480) {
            gameOver = true;
        }

        updateJellyfish(timeDelta);
    }
    public void updateScore() {
        this.score = (long) Math.abs(window.getY());
    }
    public void updatePlatforms(double timeDelta) {
        // Every 400m, remove old platforms, render new ones

    }
    public void updateJellyfish(double timeDelta) {
        // Loop through all platforms and check whether the jellyfish is on any of them
        // If yes, set the jellyfish's vertical acceleration and velocity to 0
        if (jellyfishOnPlatform) {
            jellyfish.setVerticalAcceleration(0);
            jellyfish.setVerticalVelocity(0);
        } else {
            jellyfish.setVerticalAcceleration(1200);
        }

        jellyfish.update(timeDelta, width);
    }
    public void updateWindow(double timeDelta) {
        window.update(timeDelta);
    }
    public void updateBubbles(double timeDelta) {
        // Show bubbles if % 5 seconds

    }
    public boolean isGameOver() {return gameOver;}
    public double getScore() {return score;}
}
