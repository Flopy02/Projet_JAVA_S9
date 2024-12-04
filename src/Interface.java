import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class Interface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Configuration de la fenêtre
        primaryStage.setTitle("Rotating Earth with Interaction");

        // Création de l'objet Earth
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

        // Gestion des clics pour afficher l'aéroport le plus proche
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();

                if (pickResult.getIntersectedNode() != null) {
                    // Récupérer les coordonnées de la texture (U, V)
                    double u = pickResult.getIntersectedTexCoord().getX();
                    double v = pickResult.getIntersectedTexCoord().getY();

                    // Conversion en latitude et longitude
                    double latitude = 180 * (0.5 - v);
                    double longitude = 360 * (u - 0.5);

                    // Afficher les coordonnées calculées
                    System.out.println("Clicked at Latitude: " + latitude + ", Longitude: " + longitude);

                    // Rechercher l'aéroport le plus proche
                    World world = new World("C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/airport-codes_no_comma.csv");
                    Aeroport nearestAirport = world.findNearestAirport(longitude, latitude);

                    // Afficher l'aéroport trouvé
                    if (nearestAirport != null) {
                        System.out.println("Nearest Airport: " + nearestAirport);
                    } else {
                        System.out.println("No airport found.");
                    }
                }
            }
        });

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
