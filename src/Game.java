import java.util.ArrayList;

import components.*;

public class Game {

    protected Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private Boolean playing;

    private double width, height;
    private double score;
    private boolean jellyfishOnPlatform;

    public Game(double width, double height) {

        // Set the bounds of the game
        this.width = width;
        this.height = height;

        // Set whether or not the game has started
        this.playing = false;

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(150, 300, 50, 50);
        this.jellyfishOnPlatform = true;

        // Instantiate window
        this.window = new Window(0, 0);
    }
    public void update(double timeDelta) {
        // Loop through all platforms and check whether the jellyfish is on any of them
        // If yes, set the jellyfish's vertical acceleration and velocity to 0
        if (jellyfishOnPlatform) {
            jellyfish.setVerticalAcceleration(0);
            jellyfish.setVerticalVelocity(0);
        } else {
            jellyfish.setVerticalAcceleration(1200);
        }

        jellyfish.update(timeDelta, width);

        // Show bubbles if % 5 seconds

        // Every 400m, remove old platforms, render new ones
    }
    public void updateWindow(double timeDelta) {
        window.update(timeDelta);
    }
    public void startGame() {

        // Set the status of playing to true
        playing = true;

        // Start to move the screen down; use AnimationTimer
        // moveWindow();
    }
    public void resumeGame() {
        playing = true;
    }
    public void pauseGame() {
        playing = false;
    }
    public void restartGame() {
        playing = false;
    }
    public void enterDebugMode() {
        playing = false;
    }
    public boolean isPlaying() {return playing;}
    public double getScore() {return score;}

}
