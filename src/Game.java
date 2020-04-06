import java.util.ArrayList;

import components.*;

public class Game {

    private Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private Boolean playing;

    private double width, height;

    public Game(double width, double height) {

        // Set the bounds of the game
        this.width = width;
        this.height = height;

        // Set whether or not the game has started
        this.playing = false;

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(150, 300, 50, 50);

    }
    public void update(double timeDelta) {
        jellyfish.update(timeDelta, width);
    }
    public void startGame() {

        // Set the status of playing to true
        playing = true;

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
