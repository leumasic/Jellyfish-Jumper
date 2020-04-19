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
    GraphicsContext debugContext;
    GraphicsContext circlesContext;
    
    // Application Controller
    private Controller controller;

    /**
     * @param args the command line arguments
     */
    public static void init(String[] args) {
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
        
        // Center the score
        scoreContext.setTextAlign(TextAlignment.CENTER);
        scoreContext.setTextBaseline(VPos.CENTER);

        /**
         * A canvas intended for text to be drawn onto it
         * Used to draw the debug information
         */
        Canvas debugCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(debugCanvas);
        debugContext = debugCanvas.getGraphicsContext2D();

        /**
         * A canvas intended for circles to be drawn onto it
         * Used to draw the bubbles that are part of the game's decoration
         */
        Canvas circleCanvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(circleCanvas);
        circlesContext = circleCanvas.getGraphicsContext2D();

        
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
        imageContext.clearRect(0, 0, WIDTH, HEIGHT);
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
        scoreContext.setFont(Font.font(28));
        scoreContext.fillText(text, xPosition, yPosition);
    }

    /**
     * Removes a rectangular area from the scoreContext canvas 
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     */
    public void clearScoreContext() {
        scoreContext.clearRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * Removes a rectangular area from the rectanglesContext canvas 
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     */
    public void clearRectanglesContext(double xPosition, double yPosition, double width, double height) {
        rectanglesContext.clearRect(xPosition, yPosition, width, height);
    }

    /**
     * Draws a circle onto the circlesContext Canvas
     * @param xCenter
     * @param yCenter
     * @param radius
     */
    public void drawCircle(double xCenter, double yCenter, double radius, Color color) {
        circlesContext.setFill(color);
        circlesContext.fillOval(xCenter, yCenter, radius * 2, radius * 2);
    }

    /**
     * Clears all drawings from the circlesContext canvas
     */
    public void clearCirclesContext() {
        circlesContext.clearRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * Draws a rectangle onto the debugContext canvas
     * @param xPosition
     * @param yPosition
     * @param width
     * @param height
     * @param color
     */
    public void drawRectangleOnDebugContext(double xPosition, double yPosition, double width, double height, Color color) {
        debugContext.setFill(color);
        debugContext.fillRect(xPosition, yPosition, width, height);        
    }

    /**
     * Clears all drawings from the debugContext canvas
     */
    public void clearDebugContext() {
        debugContext.clearRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * Draws text onto the debugContext canvas
     */
    public void drawTextOnDebugContext(String text, int xPosition, int yPosition) {
        debugContext.setFill(Color.WHITE);
        debugContext.setFont(Font.font(18));
        debugContext.fillText(text, xPosition, yPosition);
    }

    // Getters and setters for the height and the width of the game
    public double getWidth() {return WIDTH;}
    public double getHeight() {return HEIGHT;}
}