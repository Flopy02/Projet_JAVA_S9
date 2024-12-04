import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private Sphere sph;
    private Rotate rotationTransform;

    public Earth() {
        // Création de la sphère avec un rayon de 300 pixels
        sph = new Sphere(300);

        // Création d'un matériau pour appliquer une texture
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("file:/C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/earth_lights_4800.png"));

        // Application du matériau à la sphère
        sph.setMaterial(material);

        // Création d'une rotation autour de l'axe Y
        rotationTransform = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(rotationTransform);

        // Ajout de la sphère principale au groupe Earth
        this.getChildren().add(sph);
    }

    // Getter pour l'objet Rotate
    public Rotate getRotationTransform() {
        return rotationTransform;
    }

    // Méthode pour créer une sphère à un emplacement donné
    public Sphere createSphere(Aeroport aeroport, Color color) {
        double R = 300; // Rayon de la Terre
        double latitude = Math.toRadians(aeroport.getLatitude());
        double longitude = Math.toRadians(aeroport.getLongitude());

        // Calcul des coordonnées 3D
        double x = R * Math.cos(latitude) * Math.sin(longitude);
        double y = -R * Math.sin(latitude); // Latitude négative pour respecter l'orientation
        double z = -R * Math.cos(latitude) * Math.cos(longitude);

        // Affichage des coordonnées pour le débogage
        System.out.println("Creating sphere for airport: " + aeroport.getName());
        System.out.println("Coordinates: X=" + x + ", Y=" + y + ", Z=" + z);

        // Création de la sphère
        Sphere sphere = new Sphere(2); // Rayon de 2 pour les sphères rouges
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        sphere.setMaterial(material);

        // Positionnement dans le référentiel local de la Terre
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);

        return sphere;
    }

    // Méthode pour afficher une sphère rouge pour un aéroport
    public void displayRedSphere(Aeroport aeroport) {
        Sphere redSphere = createSphere(aeroport, Color.RED);
        if (redSphere != null) {
            this.getChildren().add(redSphere);
        }
    }
}
