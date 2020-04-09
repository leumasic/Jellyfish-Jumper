import components.Platform;
import components.Jellyfish.Orientation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Controller {

    private Image[] rightFacingJellyfishFrames;
    private Image[] leftFacingJellyfishFrames;

    // Allows the controller to modify the view internally
    private View view;
    private Game game;
    private boolean inDebugMode;

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

        inDebugMode = false;

        // Start animating the jellyfish
        animateJellyfish();

        // Start animating the platforms
        animatePlatforms();

        // Start animating the game
        animateGame();

        // Start animating the window
        animateWindow();

        // Start animating the score
        animateScore();
    }

    /**
     * Method that animates the score
     */
    private void animateScore() {
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                
                // Update the game's score
                game.updateScore();

                // Clear the score from the canvas
                view.clearText(0, 0, view.getWidth(), view.getHeight());

                // Draw the score
                view.drawText(Long.toString(game.getScore()) + "m", view.getWidth() / 2, 35);
            }   
        };
        timer.start();
    }

    /**
     * Method that animates the jellyfish
     */
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
                game.updateJellyfish(deltaTimeSinceLast);

                // Update the jellyfish's frame (orientation)
                double deltaTimeSinceStart = (now - startTime);
                int frame = (int) (deltaTimeSinceStart * frameRate);

                Image img;

                // Choose the frame to draw based on the jellyfish's orientation
                if (game.jellyfish.getOrientation() == Orientation.LEFT) {
                    img = leftFacingJellyfishFrames[frame % leftFacingJellyfishFrames.length];
                } else {
                    img = rightFacingJellyfishFrames[frame % rightFacingJellyfishFrames.length];
                }

                // Draw jellyfish
                view.drawImage(img, game.jellyfish.getX() - game.window.getX(),
                        game.jellyfish.getY() - game.window.getY());

                lastTime = now;
            }
        };
        timer.start();
    }

    /**
     * Method that animates the platforms
     */
    private void animatePlatforms() {
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // Update the platforms
                game.updatePlatforms();

                // Remove (undraw) the platforms
                view.clearRectangle(0, game.window.getY(),
                        view.getWidth(), Math.abs(game.window.getY()) + view.getHeight());

                // Draw every other platform
                for (Platform platform : game.getPlatforms()) {
                    view.drawRectangle(platform.getX() - game.window.getX(), platform.getY() - game.window.getY(),
                            platform.getWidth(), platform.getHeight(), platform.getColor());
                }
            }
        };

        timer.start();
    }

    /**
     * Method that animates the game
     */
    private void animateGame() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Update the game
                double deltaTime = (now - lastTime) * 1e-9;
                game.updateGame(deltaTime);

                // Set lastTime to now
                lastTime = now;
            }
        };
        timer.start();
    }

    /**
     * Method that animates the window
     */
    private void animateWindow() {
        AnimationTimer windowTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double timeDelta = (now - lastTime) * 1e-9;
                game.updateWindow(timeDelta);

                // Set lastTime to now
                lastTime = now;
            }
        };

        windowTimer.start();
    }

    /**
     * Event on left-key press
     */
    public void handleKeyLeft() {
        
        // Start moving the window and start the game if it hasn't started
        if (!game.hasGameStarted()) {
            game.startWindow();
            game.setGameStarted(true);
        }

        // Make the jellyfish face left
        if (game.jellyfish.getOrientation() != Orientation.LEFT)
            game.jellyfish.setOrientation(Orientation.LEFT);

        // Move the jellyfish to the left
        game.jellyfish.moveLeft();
    }

    /**
     * Event on right key press
     */
    public void handleKeyRight() {

        // Start moving the window and start the game if it hasn't started
        if (!game.hasGameStarted()) {
            game.startWindow();
            game.setGameStarted(true);
        }

        // Make the jellyfish face right
        if (game.jellyfish.getOrientation() != Orientation.RIGHT)
            game.jellyfish.setOrientation(Orientation.RIGHT);

        // Move the jellyfish to the right
        game.jellyfish.moveRight();
    }

    /**
     * Event on key up/space bar press
     */
    public void handleKeyUp() {

        // Start moving the window and start the game if it hasn't started
        if (!game.hasGameStarted()) {
            game.startWindow();
            game.setGameStarted(true);
        }

        // Make the jellyfish jump
        game.jellyfishJump();
    }

    /**
     * Toggles the debug mode
     */
    public void toggleDebugMode() {
        inDebugMode = !inDebugMode;

        if (inDebugMode) {
            // context.setFill(Color.BLACK);
            // context.fillText("Utilisez les flèches pour déplacer la fenêtre", 5, 15);
            // context.fillText("Origine de la fenêtre: (" + fenetreX + ", " + fenetreY +
            // ")", 5, 30);
            game.stopWindow();
        } else {
            game.startWindow();
        }
    }

    /**
     * Stops the horizontal movement of the jellyfish
     */
    public void stopJellyfish() {
        game.jellyfish.setHorizontalAcceleration(0.0);
        game.jellyfish.setHorizontalVelocity(0.0);
    }
}