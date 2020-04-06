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

    private AnimationTimer timer;
    private Game game;

    public Controller(View view) {
        // Set view to modify
        this.view = view;

        // Instantiate the game
        this.game = new Game(view.getWidth(), view.getHeight());

        // Set the animation frames with the jellfish's dimensions
        this.leftFacingJellyfishFrames = new Image[] {
                new Image("/assets/jellyfish1g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish2g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish3g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish4g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish5g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish6g.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false) };
        this.rightFacingJellyfishFrames = new Image[] {
                new Image("/assets/jellyfish1.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish2.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish3.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish4.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish5.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false),
                new Image("/assets/jellyfish6.png", game.jellyfish.getWidth(), game.jellyfish.getHeight(), false,
                        false) };
    }

    public void startGame() {

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

                // Update the game
                double deltaTimeSinceLast = (now - lastTime) * 1e-9;
                game.update(deltaTimeSinceLast);

                // Update the jellyfish's frame (orientation)
                double deltaTimeSinceStart = (now - startTime);
                int frame = (int) (deltaTimeSinceStart * frameRate);

                Image img;

                if (game.jellyfish.getOrientation() == Orientation.LEFT) {
                    img = leftFacingJellyfishFrames[frame % leftFacingJellyfishFrames.length];
                } else {
                    img = rightFacingJellyfishFrames[frame % rightFacingJellyfishFrames.length];
                }

                // Draw jellyfish
                view.drawImage(img, game.jellyfish.getX() - game.window.getX(),
                        game.jellyfish.getY() - game.window.getY());

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
        moveWindow();
    }

    public void stopGame() {
        stopWindow();
    }

    public void handleKeyLeft() {
        if (game.isPlaying())
            startGame();

        if (game.jellyfish.getOrientation() != Orientation.LEFT)
            game.jellyfish.setOrientation(Orientation.LEFT);

        game.jellyfish.moveLeft();
    }

    public void handleKeyRight() {
        if (game.isPlaying())
            startGame();

        if (game.jellyfish.getOrientation() != Orientation.RIGHT)
            game.jellyfish.setOrientation(Orientation.RIGHT);

        game.jellyfish.moveRight();
    }

    public void handleKeyUp() {
        game.jellyfish.jump();
    }

    public void enterDebugMode() {
    }

    public void stopJellyfish() {
        game.jellyfish.setHorizontalAcceleration(0.0);
        game.jellyfish.setHorizontalVelocity(0.0);
    }
}