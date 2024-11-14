import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Aeroport> aeroportList = new ArrayList<>();

    public World(String fileName) {
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            System.out.println("File opened successfully: " + fileName);
            String line;
            while ((line = buf.readLine()) != null) {
                line = line.replaceAll("\"", ""); // Retire les guillemets
                String[] fields = line.split(",");

                System.out.println("Analyzing line: " + line);

                if (fields.length <= 12) {
                    System.out.println("Rejected: not enough fields.");
                    continue;
                }

                String type = fields[1];
                if (!"large_airport".equals(type) && !"small_airport".equals(type)) {
                    System.out.println("Rejected: type is not large_airport or small_airport.");
                    continue;
                }

                String name = fields[2];
                String codeIATA = fields[9];
                String latitudeField = fields[12]; // Correction : latitude est maintenant dans fields[12]
                String longitudeField = fields[11]; // Correction : longitude est maintenant dans fields[11]

                if (latitudeField.isEmpty() || longitudeField.isEmpty()) {
                    System.out.println("Rejected: latitude or longitude is missing.");
                    continue;
                }

                try {
                    double latitude = Double.parseDouble(latitudeField.trim()); // Latitude correcte
                    double longitude = Double.parseDouble(longitudeField.trim()); // Longitude correcte

                    Aeroport aeroport = new Aeroport(name, latitude, longitude, codeIATA);
                    aeroportList.add(aeroport);
                    System.out.println("Added airport: " + aeroport);
                } catch (Exception e) {
                    System.err.println("Error parsing latitude/longitude for line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public Aeroport findByCode(String code) {
        for (Aeroport aeroport : aeroportList) {
            if (aeroport.getCodeIATA().equals(code)) {
                return aeroport;
            }
        }
        return null;
    }

    public Aeroport findNearest(double latitude, double longitude) {
        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : aeroportList) {
            double distance = calculDistance(latitude, longitude, aeroport.getLatitude(), aeroport.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = aeroport;
            }
        }
        return nearest;
    }

    public double calculDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371; // Rayon moyen de la Terre en kilomètres

        // Convertir les latitudes et longitudes de degrés en radians
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        // Formule de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance en kilomètres
        return EARTH_RADIUS_KM * c;
    }


    public List<Aeroport> getAeroportList() {
        return aeroportList;
    }
}
