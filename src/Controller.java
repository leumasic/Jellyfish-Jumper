import java.util.ArrayList;

import components.*;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Controller {
    
    // Allows the controller to modify the view internally
    private View view;

    private Window window;
    private Jellyfish jellyfish;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private AnimationTimer timer;
    private Boolean gameStarted;

    private Image[] moveRightFrames = new Image[] {
        new Image("/assets/jellyfish1.png", 50, 50, false, false),
        new Image("/assets/jellyfish2.png", 50, 50, false, false),
        new Image("/assets/jellyfish3.png", 50, 50, false, false),
        new Image("/assets/jellyfish4.png", 50, 50, false, false),
        new Image("/assets/jellyfish5.png", 50, 50, false, false),
        new Image("/assets/jellyfish6.png", 50, 50, false, false)
    };

    private Image[] moveLeftFrames = new Image[] {
        new Image("/assets/jellyfish1g.png", 50, 50, false, false),
        new Image("/assets/jellyfish2g.png", 50, 50, false, false),
        new Image("/assets/jellyfish3g.png", 50, 50, false, false),
        new Image("/assets/jellyfish4g.png", 50, 50, false, false),
        new Image("/assets/jellyfish5g.png", 50, 50, false, false),
        new Image("/assets/jellyfish6g.png", 50, 50, false, false)
    };

    public Controller(View view) {
        // Set view to modify
        this.view = view;

        // Set whether or not the game has started
        this.gameStarted = false;
    }

    public void startGame() {
        // Place the medusa
        gameStarted = true;

        // Start to move the screen down; use AnimationTimer
        // moveWindow();
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
                
                window.updateSpeed(timeDelta);
                window.updatePosition(timeDelta);

                view.updateWindowPosition();

                lastTime = now;
            }
        };

        timer.start();
    }

    private void stopWindow() {
        timer.stop();
    }

    public void resumeGame() {
        gameStarted = true;
        moveWindow();
    }
    public void stopGame() {
        gameStarted = false;
        stopWindow();
    }

    public void handleKeyLeft() {
        if (!gameStarted) startGame();

        
    }
    public void handleKeyRight() {
        if (!gameStarted) startGame();


    }
    public void handleKeyUp() {
        if (!gameStarted) startGame();


    }
    public void enterDebugMode() {

    }
}