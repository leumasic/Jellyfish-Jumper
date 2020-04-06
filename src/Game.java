import java.util.ArrayList;

import components.*;

public class Game {

    private Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private Boolean playing;
    
    private double topBound, bottomBound;
    private double leftBound, rightBound;

    public Game(double leftBound, double rightBound, double topBound, double bottomBound) {

        // Set the bounds of the game
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.topBound = topBound;
        this.bottomBound = bottomBound;

        // Set whether or not the game has started
        this.playing = false;

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(150, 300);

    }

    public void startGame() {

        // Set the status of playing to true
        playing = true;

        // Instantiate the jellyfish
        this.jellyfish = new Jellyfish(50, 0);

        // Start to move the screen down; use AnimationTimer
        // moveWindow();
    }
    private void moveWindow() {

    }
    private void stopWindow() {

    }
    public void resumeGame() {
        playing = true;
        moveWindow();
    }
    public void stopGame() {
        playing = false;
        stopWindow();
    }
    public void enterDebugMode() {
        playing = false;
    }
    public boolean isPlaying() {return playing;}
}
