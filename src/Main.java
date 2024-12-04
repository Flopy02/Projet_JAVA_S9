public class Main {
    public static void main(String[] args) {
        // Initialisation du monde avec le fichier CSV
        World world = new World("C:/Users/floba/IdeaProjects/Projet_JAVA_S9/src/data/airport-codes_no_comma.csv");

        // Affichage du nombre d'aéroports chargés
        System.out.println("Found " + world.getAeroportList().size() + " large airports.");

        // Test 1 : Trouver l'aéroport le plus proche des coordonnées (Paris : 48.866, 2.316)
        Aeroport nearestToParis = world.findNearestAirport(2.316, 48.866); // longitude, latitude
        if (nearestToParis != null) {
            System.out.println("Nearest airport to Paris: " + nearestToParis);
        } else {
            System.out.println("No nearest airport found for Paris.");
        }

        // Test 2 : Trouver l'aéroport par code IATA (CDG et ORY)
        Aeroport cdg = world.findByCode("CDG");
        Aeroport orly = world.findByCode("ORY");

        System.out.println("CDG: " + (cdg != null ? cdg : "Not found"));
        System.out.println("ORY: " + (orly != null ? orly : "Not found"));

        // Test 3 : Calculer la distance entre Paris et CDG (si CDG est trouvé)
        if (cdg != null) {
            double distanceToCDG = world.calculDistance(48.866, 2.316, cdg.getLatitude(), cdg.getLongitude());
            System.out.println("Distance from Paris to CDG: " + distanceToCDG + " km");
        }

        // Test 4 : Calculer la distance entre Paris et Orly (si ORY est trouvé)
        if (orly != null) {
            double distanceToOrly = world.calculDistance(48.866, 2.316, orly.getLatitude(), orly.getLongitude());
            System.out.println("Distance from Paris to Orly: " + distanceToOrly + " km");
        }

        // Optionnel : Affichage des aéroports chargés (décommenter si nécessaire)
        // System.out.println("Loaded large airports:");
        // for (Aeroport aeroport : world.getAeroportList()) {
        //     System.out.println(aeroport);
        // }
    }
}
