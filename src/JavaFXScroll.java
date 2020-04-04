import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFXScroll extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int w = 300;
        int h = 300;

        Pane root = new Pane();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(w, h);
        root.getChildren().add(canvas);
        
        /* 
         NOTEZ : Il s'agit un petit exemple pour démontrer
         l'idée derrière le "scroll" de la fenêtre, ce programme
         ne suit *pas une structure MVC
         */
        Jeu jeu = new Jeu();

        /* Lorsqu'on appuie sur les flèches du clavier,
           on déplace la fenêtre affichée dans jeu */
        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case LEFT:
                    jeu.gauche();
                    break;
                case RIGHT:
                    jeu.droite();
                    break;
                case UP:
                    jeu.haut();
                    break;
                case DOWN:
                    jeu.bas();
            }

            switch (e.getCode()) {
                case LEFT:
                    System.out.println("you moving left man!");
                    break;
                case RIGHT:
                    System.out.println("you moving left man!");
                    break;
                case UP:
                    System.out.println("you moving left man!");
                    break;
                case DOWN:
                    System.out.println("you moving left man!");
            }
        });

        GraphicsContext context = canvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                context.clearRect(0, 0, w, h);
                jeu.update(dt);
                jeu.draw(context);

                lastTime = now;
            }
        };

        timer.start();

        primaryStage.setTitle("Testing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
