import java.util.ArrayList;
import java.util.LinkedList;

import components.*;

public class Game {

    private int numPlatforms = 15;
    private double verticalSpaceBetweenPlatforms = 100;

    protected Window window;
    protected Jellyfish jellyfish;
    private ArrayList<Bubble> bubbles;
    private LinkedList<Platform> platforms;

    private boolean gameOver;
    private boolean playing;

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
        this.platforms = getNPlatforms(numPlatforms, height - verticalSpaceBetweenPlatforms);
    }

    public void startWindow() {
        this.window.setVerticalAcceleration(-2);
        this.window.setVerticalVelocity(-50);
    }

    public void stopWindow() {
        this.window.setVerticalAcceleration(0);
        this.window.setVerticalVelocity(0);
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
            System.out.println("Game restarted!");
        }

        updateJellyfish(timeDelta);
        updatePlatforms(5);
    }

    public void updateScore() {
        this.score = (long) Math.abs(window.getY());
    }

    public void updatePlatforms(int numPlatformsToVerify) {
        if (numPlatformsToVerify > numPlatforms) {
            throw new IllegalArgumentException("Number of platforms to verify greater than that in memory");
        }

        // Update the platforms after numPlatformsToVerify of them are below the window
        if (platforms.peek().getY() - window.getY() > verticalSpaceBetweenPlatforms * numPlatformsToVerify) {
            removeFirstPlaforms(numPlatformsToVerify);
            getNPlatforms(numPlatformsToVerify, platforms.peekLast().getY());
        }
    }

    public void updateJellyfish(double timeDelta) {
        // Loop through all NEARBY `platforms and check whether the jellyfish is on any
        // of them
        // If yes, set the jellyfish's vertical acceleration and velocity to 0

        if (playing) {
            for (Platform platform : platforms) {

                // if (jellyfish.getVerticalVelocity() > 0) {
                // jellyfishOnPlatform = false;
    
                // // If the jellyfish's feet are below half of one of the platforms, then it is
                // on
                // // one.
                // // Replace the jellyfish above the platform, if so.
                // if (jellyfish.getY() + jellyfish.getHeight() <= platform.getY() +
                // platform.getHeight()
                // && jellyfish.getY() + jellyfish.getHeight() >= platform.getY()) {
                // if (jellyfish.getX() >= platform.getX()
                // && jellyfish.getX() <= platform.getX() + platform.getWidth()) {
                // System.out.println("Jelly on platform!");
                // jellyfishOnPlatform = true;
                // break;
                // }
                // }
                // }
    
                if (jellyfish.getY() + jellyfish.getHeight() <= platform.getY() + platform.getHeight()
                        && jellyfish.getY() + jellyfish.getHeight() >= platform.getY()
                        && jellyfish.getX() >= platform.getX()
                        && jellyfish.getX() <= platform.getX() + platform.getWidth()
                        && jellyfish.getVerticalVelocity() <= 0
                        ) {
    
                    System.out.println("Jelly on platform!");
                    jellyfishOnPlatform = true;
                    break;
                } else {
                    jellyfishOnPlatform = false;
                }
                /*
                if ( || jellyfish.getVerticalVelocity() > 0) {
                    jellyfishOnPlatform = false;
                    break;
                } else {
                    jellyfishOnPlatform = true;
                }
                */
    
                if (jellyfishOnPlatform) {
                    jellyfish.setVerticalAcceleration(0);
                    jellyfish.setVerticalVelocity(0);
                } else {
                    jellyfish.setVerticalAcceleration(1200);
                }
            }
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
        if (jellyfishOnPlatform) {
            jellyfish.jump();
            jellyfishOnPlatform = false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isGamePlaying() {return playing;}
    public void setGamePlaying(boolean isGamePlaying) {this.playing = isGamePlaying;} 
    public double getScore() {
        return score;
    }

    private void removeFirstPlaforms(int n) {
        if (n > numPlatforms) {
            throw new IllegalArgumentException("Number of platforms to remove greater than that in memory");
        }

        for (int i = 0; i < n; i++) {
            platforms.poll();
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
            double platformY = startY + (-i * verticalSpaceBetweenPlatforms);

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

    public double getverticalSpaceBetweenPlatforms() {
        return verticalSpaceBetweenPlatforms;
    }

    public int getNumPlatforms() {
        return numPlatforms;
    }

    public LinkedList<Platform> getPlatforms() {
        return platforms;
    }
}
