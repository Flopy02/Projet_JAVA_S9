import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Interface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Configuration de la fenêtre
        primaryStage.setTitle("Rotating Earth");

        // Création de l'objet Earth
        Earth earth = new Earth();

        // Création d'une caméra
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000); // Position initiale de la caméra
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);

        // Création de la scène
        Scene theScene = new Scene(earth, 800, 600, true);
        theScene.setCamera(camera);

        // AnimationTimer pour mettre à jour la rotation
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long time) {
                if (lastUpdate > 0) {
                    // Temps écoulé entre deux frames en secondes
                    double delta = (time - lastUpdate) / 1e9;
                    double rotationSpeed = 20; // Vitesse de rotation (degrés par seconde)

                    // Mise à jour de l'angle de rotation autour de l'axe Y
                    double currentAngle = earth.getRotationTransform().getAngle();
                    earth.getRotationTransform().setAngle(currentAngle + rotationSpeed * delta);
                }

                lastUpdate = time; // Mise à jour du temps de la dernière frame
            }
        };

        // Démarrage de l'animation
        animationTimer.start();

        // Ajout de la scène à la fenêtre
        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
