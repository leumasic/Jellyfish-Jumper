import java.util.ListIterator;

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
        // animateWindow();
    }
    public void startGame() {
        this.game.startWindow();
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

                double deltaTimeSinceLast = (now - lastTime) * 1e-9;
                game.updateJellyfish(deltaTimeSinceLast);

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

                lastTime = now;
            }
        };
        timer.start();
    }
    private void animatePlatforms() {
        AnimationTimer timer = new AnimationTimer() {
            private double firstPlaformVerticalPosition = -50;

            @Override
            public void handle(long now) {
                // Draw the platforms
                if (firstPlaformVerticalPosition != game.getPlatforms().peek().getY()) {

                    ListIterator<Platform> platformIterator;
                    platformIterator = game.getPlatforms().listIterator(0);

                    while (platformIterator.hasNext()) {
                        Platform platformToDraw = platformIterator.next();
                        view.drawRectangle(platformToDraw.getX(), platformToDraw.getY(), platformToDraw.getWidth(),
                                platformToDraw.getHeight(), platformToDraw.getColor());
                    }

                    // First platform vertical position
                    firstPlaformVerticalPosition = game.getPlatforms().peek().getY();

                    game.updatePlatforms(5);
                }
            }
        };
        timer.start();
    }
    private void animateGame() {
        AnimationTimer timer = new AnimationTimer(){
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

                // Restart the game if it is over
                if (game.isGameOver()) {
                    game.restartGame();
                }

                // Set lastTime to now
                lastTime = now;
            }
        };
        timer.start();
    }
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

                lastTime = now;
            }
        };

        windowTimer.start();
    }
    public void resumeGame() {
        animateWindow();
    }
    public void handleKeyLeft() {

        if (game.jellyfish.getOrientation() != Orientation.LEFT)
            game.jellyfish.setOrientation(Orientation.LEFT);

        game.jellyfish.moveLeft();
    }

    public void handleKeyRight() {

        if (game.jellyfish.getOrientation() != Orientation.RIGHT)
            game.jellyfish.setOrientation(Orientation.RIGHT);

        game.jellyfish.moveRight();
    }

    public void handleKeyUp() {
        
        game.jellyfishJump();
    }

    public void toggleDebugMode() {
        inDebugMode = !inDebugMode;

        if (inDebugMode) {
            // context.setFill(Color.BLACK);
            // context.fillText("Utilisez les flèches pour déplacer la fenêtre", 5, 15);
            // context.fillText("Origine de la fenêtre: (" + fenetreX + ", " + fenetreY +
            // ")", 5, 30);
            game.stopWindow();
        } else {
            animateWindow();
        }
    }

    public void stopJellyfish() {
        game.jellyfish.setHorizontalAcceleration(0.0);
        game.jellyfish.setHorizontalVelocity(0.0);
    }
}