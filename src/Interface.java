import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Interface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Configuration de la fenêtre
        primaryStage.setTitle("Textured Earth");

        // Création de l'objet Earth (sphère avec texture)
        Earth earth = new Earth();

        // Création d'une caméra
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000); // Position initiale de la caméra
        camera.setNearClip(0.1);     // Distance minimale visible
        camera.setFarClip(2000.0);   // Distance maximale visible
        camera.setFieldOfView(35);   // Champ de vision

        // Création de la scène
        Scene theScene = new Scene(earth, 800, 600, true);
        theScene.setCamera(camera);

        // Gestion du zoom et dézoom avec la molette
        theScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY(); // Valeur du défilement de la molette
            camera.setTranslateZ(camera.getTranslateZ() + deltaY * 0.1); // Ajuste la position Z
        });

        // Gestion des clics pour afficher les coordonnées
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on: X=" + event.getSceneX() + ", Y=" + event.getSceneY());
            }
        });

        // Ajout de la scène à la fenêtre
        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
