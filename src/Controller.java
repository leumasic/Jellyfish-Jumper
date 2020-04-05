import java.util.ArrayList;

import components.*;
import javafx.animation.AnimationTimer;

public class Controller {
    
    // Allows the controller to modify the view internally
    private View view;

    // Models 
    // private Model gorgeousModel;
    private Medusa medusa;
    private ArrayList<Platform> platforms;
    private ArrayList<Bubble> bubbles;
    private AnimationTimer timer;

    public Controller(View view) {
        // Set view to modify
        this.view = view;

        // Initialize gorgeous models
        this.medusa = new Medusa();

        // Initialize
    }

    // To-be added functions below, called on event trigger
    public void startGame() {
        // Place the medusa
        
        // Start to move the screen down; use AnimationTimer
        timer = new AnimationTimer(){
        
            @Override
            public void handle(long now) {
                
            }
        };

        timer.start();
    }

    public void handleKeyLeft() {

    }
    public void handleKeyRight() {

    }
    public void handleKeyUp() {

    }
    public void enterDebugMode() {

    }
}