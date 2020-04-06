import java.util.ArrayList;
import java.util.LinkedList;

import components.*;

public class Game {

    private static int platformsInMemory = 15;
    private static double platformsVerticalSpacing = 100;

    protected Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Bubble> bubbles;
    private LinkedList<Platform> platforms;
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

        // Instantiate platforms
        platforms = getNPlatforms(platformsInMemory, height - platformsVerticalSpacing);
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
        if (jellyfish.getY() - window.getY() > height) {
            gameOver = true;
        }

        updateJellyfish(timeDelta);
        updatePlatforms(5);
    }
    public void updateScore() {
        this.score = (long) Math.abs(window.getY());
    }
    public void updatePlatforms(int numPlatformsToVerify) {
        if (numPlatformsToVerify > platformsInMemory) {
            throw new IllegalArgumentException("Number of platforms to verify greater than that in memory");
        }

        // Update the platforms after numPlatformsToVerify of them are below the window
        if (platforms.peek().getY() - window.getY() > platformsVerticalSpacing * numPlatformsToVerify) {
            removeNPlatforms(numPlatformsToVerify);
            getNPlatforms(numPlatformsToVerify, platforms.getLast().getY());
        }
    }
    public void updateJellyfish(double timeDelta) {
        // Loop through all NEARBY `platforms and check whether the jellyfish is on any of them
        // If yes, set the jellyfish's vertical acceleration and velocity to 0
        /*
        for (Platform platform: platforms) {
            
            if (true) {
                jellyfishOnPlatform = true;
                jellyfish.setY(Math.min(jellyfish.getY(), height - jellyfish.getHeight() / 2));
                jellyfish.setY(Math.max(jellyfish.getY(), jellyfish.getHeight()/2));
            }
        }
        */

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
    public void jellyfishJump() {
        jellyfishOnPlatform = false;
        jellyfish.jump();
    }
    public boolean isGameOver() {return gameOver;}
    public double getScore() {return score;}
    private void removeNPlatforms(int n) {
        if (n > platformsInMemory) {
            throw new IllegalArgumentException("Number of platforms to remove greater than that in memory");
        }

    }
    private LinkedList<Platform> getNPlatforms(int n, double startY) {
        LinkedList<Platform> generatedPlatforms = new LinkedList<Platform>();
        boolean lastAddedWasSolid = false;

        for (int i = 0; i < n; i++) {
            Platform platformToAdd;

            double rand = Math.random();
            double platformWidth = 80.0 + (95.0 * rand);
            double platformX = rand * (width - platformWidth);
            double platformY = startY + (i * platformsVerticalSpacing);

            if (!lastAddedWasSolid) {
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
                platformToAdd = new SimplePlatform(platformX, platformY, platformWidth, 10);
                lastAddedWasSolid = false;
            }

            generatedPlatforms.add(platformToAdd);
        }

        return generatedPlatforms;
    }
}
