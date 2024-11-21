import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private Sphere sph;
    private Rotate rotationTransform; // Renommé pour éviter le conflit

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
        sph.getTransforms().add(rotationTransform); // Ajout de la rotation à la sphère

        // Ajout de la sphère au groupe
        this.getChildren().add(sph);
    }

    // Getter pour l'objet Rotate (renommé pour éviter le conflit)
    public Rotate getRotationTransform() {
        return rotationTransform;
    }
}
