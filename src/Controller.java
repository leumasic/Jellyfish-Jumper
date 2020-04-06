import java.util.ArrayList;

import components.*;
import components.Jellyfish.Orientation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Controller {

    private Image[] rightFacingJellyfishFrames;
    private Image[] leftFacingJellyfishFrames;

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

        // Instantiate a jellyfish
        this.jellyfish = new Jellyfish(50, 0);

        // Set the animation frames with the jellfish's dimensions
        this.leftFacingJellyfishFrames = new Image[] {
                new Image("/assets/jellyfish1g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish2g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish3g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish4g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish5g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish6g.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false) };
        this.rightFacingJellyfishFrames = new Image[] {
                new Image("/assets/jellyfish1.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish2.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish3.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish4.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish5.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false),
                new Image("/assets/jellyfish6.png", jellyfish.getWidth(), jellyfish.getHeight(), false, false) };
    }

    public void startGame() {

        // Set the status of playing to true
        playing = true;

        // Start animating the jellyfish
        animateJellyfish();

        // Start to move the screen down; use AnimationTimer
        // moveWindow();
    }

    private void animateJellyfish() {
        double frameRate = 8 * 1e-9;
        AnimationTimer timer = new AnimationTimer() {
            private long startTime = 0;
            private long lastTime = 0;
            
            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;
                    lastTime = now;
                    return;
                }

                // Update the jellyfish's position 
                double deltaTimeSinceLast = (now - lastTime) * 1e-9;
                jellyfish.update(deltaTimeSinceLast);

                // Update the jellyfish's frame (orientation)
                double deltaTimeSinceStart = (now - startTime);
                int frame = (int) (deltaTimeSinceStart * frameRate);

                Image img;

                if (jellyfish.getOrientation() == Orientation.LEFT) {
                    img = leftFacingJellyfishFrames[frame % leftFacingJellyfishFrames.length];
                } else {
                    img = rightFacingJellyfishFrames[frame % rightFacingJellyfishFrames.length];
                }

                // Draw jellyfish
                view.drawJellyfish(img, jellyfish.getX(), jellyfish.getY());

                // Set last time to current time
                lastTime = now;
            }
        };
        timer.start();
    }

    private void moveWindow() {
        timer = new AnimationTimer() {
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
    public void stopGame() {
        playing = false;
        stopWindow();
    }

    public void handleKeyLeft() {
        if (!playing)
            startGame();

        if (jellyfish.getOrientation() != Orientation.LEFT)
            jellyfish.setOrientation(Orientation.LEFT);

        jellyfish.moveLeft();
    }

    public void handleKeyRight() {
        if (!playing)
            startGame();

        if (jellyfish.getOrientation() != Orientation.RIGHT)
            jellyfish.setOrientation(Orientation.RIGHT);

        jellyfish.moveRight();
    }

    public void handleKeyUp() {
        if (!playing)
            startGame();

    }

    public void enterDebugMode() {
        playing = false;

    }
}