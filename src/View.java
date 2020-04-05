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

    // Application Controller
    private Controller controller;

       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        controller = new Controller(this);

        Pane root = new Pane();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        
        Image image = new Image("/img/medusa.png");
        
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

        GraphicsContext context = canvas.getGraphicsContext2D();

        primaryStage.setTitle("HighSeaTower");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateWindowPosition() {

    }

}