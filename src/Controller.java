import java.util.ArrayList;

import components.*;
import components.Jellyfish.Orientation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Controller {
    
    private Image[] jellyfishFacingRightFrames = new Image[] {
        new Image("/assets/jellyfish1.png", 50, 50, false, false),
        new Image("/assets/jellyfish2.png", 50, 50, false, false),
        new Image("/assets/jellyfish3.png", 50, 50, false, false),
        new Image("/assets/jellyfish4.png", 50, 50, false, false),
        new Image("/assets/jellyfish5.png", 50, 50, false, false),
        new Image("/assets/jellyfish6.png", 50, 50, false, false)
    };

    private Image[] jellyfishFacingLeftFrames = new Image[] {
        new Image("/assets/jellyfish1g.png", 50, 50, false, false),
        new Image("/assets/jellyfish2g.png", 50, 50, false, false),
        new Image("/assets/jellyfish3g.png", 50, 50, false, false),
        new Image("/assets/jellyfish4g.png", 50, 50, false, false),
        new Image("/assets/jellyfish5g.png", 50, 50, false, false),
        new Image("/assets/jellyfish6g.png", 50, 50, false, false)
    };

    // Allows the controller to modify the view internally
    private View view;

    private Window window;
    private Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private AnimationTimer timer;
    private Boolean playing;

    public Controller(View view) {
        // Set view to modify
        this.view = view;

        // Set whether or not the game has started
        this.playing = false;
    }

    public void startGame() {

        // Set the status of playing to true
        playing = true;

        // Instantiate jellyfish and start animating it
        jellyfish = new Jellyfish(0, 0);
        animateJellyfish();

        // Start to move the screen down; use AnimationTimer
        // moveWindow();
    }
    private void animateJellyfish() {
        double frameRate = 8 * 1e-9;
        AnimationTimer timer = new AnimationTimer(){
            private long startTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;
                    return;
                }

                double deltaTime = now - startTime;
                int frame = (int) (deltaTime * frameRate);

                Image img;

                if (jellyfish.getOrientation() == Orientation.LEFT) {
                    img = jellyfishFacingLeftFrames[frame % jellyfishFacingLeftFrames.length];
                } else {
                    img = jellyfishFacingRightFrames[frame % jellyfishFacingRightFrames.length];
                }

                view.drawJellyfish(img);
            }
        };
        timer.start();
    }
    private void moveWindow() {
        timer = new AnimationTimer(){
            private long lastTime = 0;
            
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double timeDelta = (now - lastTime) * 1e-9;
                
                lastTime = now;
            }
        };

        timer.start();
    }

    private void stopWindow() {
        timer.stop();
    }


    public void resumeGame() {
        playing = true;
        moveWindow();
    }
    public void pauseGame() {
        
    }
    public void stopGame() {
        playing = false;
        stopWindow();
    }

    public void handleKeyLeft() {
        if (!playing) startGame();

        if (jellyfish.getOrientation() != Orientation.LEFT)
            jellyfish.setOrientation(Orientation.LEFT);
    }
    public void handleKeyRight() {
        if (!playing) startGame();

        if (jellyfish.getOrientation() != Orientation.RIGHT)
            jellyfish.setOrientation(Orientation.RIGHT);
    }
    public void handleKeyUp() {
        if (!playing) startGame();


    }
    public void enterDebugMode() {
        pauseGame();
        
    }
}