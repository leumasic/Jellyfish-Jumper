import javafx.application.Application;;
import javafx.stage.Stage;

public class View extends Application {
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
        System.out.println("Hello world");
    }
}