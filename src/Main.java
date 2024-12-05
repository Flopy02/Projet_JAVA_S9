import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        // Initialisation du monde avec le fichier CSV
        World world = new World("C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/airport-codes_no_comma.csv");

        // Affichage du nombre d'aéroports chargés
        System.out.println("Found " + world.getAeroportList().size() + " large airports.");

        try {
            // Chargement des données de vol depuis un fichier JSON
            World w = new World("./data/airport-codes_no_comma.csv");
            BufferedReader br = new BufferedReader(new FileReader("data/test.txt"));
            String test = br.readLine();
            JsonFlightFiller jsonFlightFiller = new JsonFlightFiller(test, w);

            // Afficher les vols générés
            jsonFlightFiller.displayFlights();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
