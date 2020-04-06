import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application {

    private double WIDTH = 350;
    private double HEIGHT = 480;

    // Graphical Elements
    GraphicsContext context;

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

        controller = new Controller(this);

        Pane root = new Pane();
        Scene scene = new Scene(root);

        Canvas background = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(background);

        GraphicsContext backgroundContext = background.getGraphicsContext2D();
        backgroundContext.setFill(Color.rgb(10, 24, 173));
        backgroundContext.fillRect(0, 0, 350, 480);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();

        controller.startGame();

        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case LEFT:
                    controller.handleKeyLeft();
                    break;
                case UP:
                    controller.handleKeyUp();
                    break;
                case RIGHT:
                    controller.handleKeyRight();
                    break;
                case T:
                    controller.enterDebugMode();
                case ESCAPE:
                    Platform.exit();
                    break;
            }
        });

        scene.setOnKeyReleased((e) -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                controller.stopJellyfish();
            }
        });

        primaryStage.setTitle("High Sea Tower");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void drawImage(Image img, double xPosition, double yPosition) {
        // context.clearRect(xPosition, yPosition, img.getWidth(), img.getHeight());
        context.clearRect(0, 0, WIDTH, HEIGHT);
        context.drawImage(img, xPosition, yPosition);
    }
    public void drawWindow() {
        
    }
    public double getWidth() {return WIDTH;}
    public double getHeight() {return HEIGHT;}
}