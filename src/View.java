import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class View extends Application {

    private static double width = 300;
    private static double height = 300;

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
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();

        Image[] frames = new Image[] {
            new Image("/assets/jellyfish1.png", 50, 50, false, false),
            new Image("/assets/jellyfish2.png", 50, 50, false, false),
            new Image("/assets/jellyfish3.png", 50, 50, false, false),
            new Image("/assets/jellyfish4.png", 50, 50, false, false),
            new Image("/assets/jellyfish5.png", 50, 50, false, false),
            new Image("/assets/jellyfish6.png", 50, 50, false, false)
        };

        double frameRate = 8 * 1e-9;

        AnimationTimer timer = new AnimationTimer(){
            private long startTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;
                    return;
                }

                double deltaTime = now - startTime;
                int frame = (int) (deltaTime * frameRate);

                Image img = frames[frame % frames.length];

                context.clearRect(0, 0, img.getWidth(), img.getHeight());
                context.drawImage(img, 0, 0);
            }
        };

        timer.start();

        /*
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
                    controller.stopGame();
            }
        });
        */

        primaryStage.setTitle("HighSeaTower");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateWindowPosition() {

    }

    public void drawJellyfish() {

    }
}