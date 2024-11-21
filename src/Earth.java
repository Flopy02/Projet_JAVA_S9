import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Earth extends Group {
    private Sphere sph;

    public Earth() {
        // Création de la sphère avec un rayon de 300 pixels
        sph = new Sphere(300);

        // Création d'un matériau pour appliquer une texture
        PhongMaterial material = new PhongMaterial();

        // Chargement de l'image de la texture avec un chemin absolu
        material.setDiffuseMap(new Image("file:/C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/earth_lights_4800.png"));

        // Application du matériau à la sphère
        sph.setMaterial(material);

        // Ajout de la sphère au groupe
        this.getChildren().add(sph);
    }
}
