import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View extends Application {

    private double WIDTH = 350;
    private double HEIGHT = 480;

    // Graphical Elements
    GraphicsContext imageContext;
    GraphicsContext rectanglesContext; 
    GraphicsContext scoreContext; 
    
    // Application Controller
    private Controller controller;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Instantiate a controller to have access to the viewer's drawing methods
        controller = new Controller(this);

        // Set the root (pane) and the scene
        Pane root = new Pane();
        Scene scene = new Scene(root);

        // A canvas to show the background static of the game
        Canvas backgroundCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(backgroundCanvas);
        GraphicsContext backgroundContext = backgroundCanvas.getGraphicsContext2D();
        backgroundContext.setFill(Color.rgb(10, 24, 173));
        backgroundContext.fillRect(0, 0, 350, 480);

        /**
         * A canvas intended for images to be drawn onto it
         * Used to draw the jellyfish
         */
        Canvas imageCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(imageCanvas);
        imageContext = imageCanvas.getGraphicsContext2D();

        /**
         * A canvas intended for the rectangles to be drawn onto it
         * Used to draw platforms
         */
        Canvas rectanglesCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(rectanglesCanvas);
        rectanglesContext = rectanglesCanvas.getGraphicsContext2D();

        /**
         * A canvas intended for text to be drawn onto it
         * Used to draw the score
         */
        Canvas scoreCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(scoreCanvas);
        scoreContext = scoreCanvas.getGraphicsContext2D();
        
        // Center the text
        scoreContext.setTextAlign(TextAlignment.CENTER);
        scoreContext.setTextBaseline(VPos.CENTER);

        // Events triggered whenever the keys in the switch block are pressed
        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case LEFT:
                    controller.handleKeyLeft();
                    break;
                case UP:
                    controller.handleKeyUp();
                    break;
                case SPACE:
                    controller.handleKeyUp();
                    break;
                case RIGHT:
                    controller.handleKeyRight();
                    break;
                case T:
                    controller.toggleDebugMode();
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }
        });
        
        // Events triggered whenever the left and right key are released
        scene.setOnKeyReleased((e) -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                controller.stopJellyfish();
            }
        });

        // Set the application's icon
        primaryStage.getIcons().add(new Image("/assets/jellyfish1.png"));

        // Set the application's title
        primaryStage.setTitle("High Sea Tower");

        // Set the application's scene and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Draws an image onto the imageContext canvas
     * @param img
     * @param xPosition
     * @param yPosition
     */
    public void drawImage(Image img, double xPosition, double yPosition) {
        imageContext.clearRect(xPosition - 50, yPosition - 100, WIDTH, HEIGHT);
        imageContext.drawImage(img, xPosition, yPosition);
    }

    /**
     * Draws a rectangle onto the rectanglesContext canvas
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     * @param color
     */
    public void drawRectangle(double xPosition, double yPosition, double width, double height, Color color) {
        rectanglesContext.setFill(color);
        rectanglesContext.fillRect(xPosition, yPosition, width, height);        
    }

    /**
     * Draws text onto the scoreContext canvas
     * @param text
     * @param xPosition
     * @param yPosition
     */
    public void drawText(String text, double xPosition, double yPosition) {
        scoreContext.setFill(Color.WHITE);
        scoreContext.setFont(Font.font("Purisa", 28));
        scoreContext.fillText(text, xPosition, yPosition);
    }

    /**
     * Removes a rectangular area from the scoreContext canvas 
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     */
    public void clearText(double xPosition, double yPosition, double width, double height) {
        scoreContext.clearRect(xPosition, yPosition, width, height);
    }

    /**
     * Removes a rectangular area from the rectanglesContext canvas 
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     */
    public void clearRectangle(double xPosition, double yPosition, double width, double height) {
        rectanglesContext.clearRect(xPosition, yPosition, width, height);
    }

    // Getters and setters for the height and the width of the game
    public double getWidth() {return WIDTH;}
    public double getHeight() {return HEIGHT;}
}