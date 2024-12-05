import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

public class Interface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Configuration de la fenêtre
        primaryStage.setTitle("Rotating Earth with Interaction");

        // Création de l'objet Earth
        Earth earth = new Earth();

        // Chargement du monde avec les données des aéroports
        World world = new World("C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/airport-codes_no_comma.csv");

        // Création d'une caméra
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000); // Position initiale de la caméra
        camera.setNearClip(0.1);     // Distance minimale visible
        camera.setFarClip(2000.0);   // Distance maximale visible
        camera.setFieldOfView(35);   // Champ de vision

        // Création de la scène
        Scene theScene = new Scene(earth, 800, 600, true);
        theScene.setCamera(camera);

        // Gestion du zoom avec la molette
        theScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY(); // Valeur du défilement de la molette
            camera.setTranslateZ(camera.getTranslateZ() + deltaY * 0.1); // Ajuste la position Z
        });

        // Gestion des clics pour afficher les coordonnées
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null && pickResult.getIntersectedTexCoord() != null) {
                    // Récupérer les coordonnées de la texture (U, V)
                    double u = pickResult.getIntersectedTexCoord().getX();
                    double v = pickResult.getIntersectedTexCoord().getY();

                    // Conversion en latitude et longitude
                    double latitude = 90 - v * 180;
                    double longitude = u * 360 - 180;

                    // Afficher les coordonnées calculées
                    System.out.println("Clicked at Latitude: " + latitude + ", Longitude: " + longitude);

                    // Rechercher l'aéroport le plus proche
                    Aeroport nearestAirport = world.findNearestAirport(longitude, latitude);

                    if (nearestAirport != null) {
                        System.out.println("Nearest Airport: " + nearestAirport);
                        earth.displayRedSphere(nearestAirport);

                        // Interroger l'API et afficher les sphères jaunes
                        try {
                            String apiKey = "25e23df470b214c2684a44888e9d5673"; // Remplacez par votre clé API
                            String url = "http://api.aviationstack.com/v1/flights?access_key=" + apiKey + "&arr_iata=" + nearestAirport.getCodeIATA();

                            HttpClient client = HttpClient.newHttpClient();
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(url))
                                    .GET()
                                    .build();

                            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                            // Analyse de la réponse JSON
                            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(response.body(), world);

                            // Utiliser un ensemble pour éviter les doublons d'aéroports
                            Set<Aeroport> departureAirports = new HashSet<>();

                            // Affichage des sphères jaunes pour les aéroports de départ
                            for (Flight flight : jsonFlightFiller.getList()) {
                                Aeroport departureAirport = world.findByCode(flight.getDepartureIata());
                                if (departureAirport != null && !departureAirports.contains(departureAirport)) {
                                    earth.displayYellowSphere(departureAirport);
                                    departureAirports.add(departureAirport);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No nearest airport found.");
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
