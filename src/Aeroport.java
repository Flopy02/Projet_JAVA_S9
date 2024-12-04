public class Aeroport {
    private String name;       // Nom de l'aéroport
    private double latitude;   // Latitude de l'aéroport
    private double longitude;  // Longitude de l'aéroport
    private String codeIATA;   // Code IATA de l'aéroport

    // Constructeur
    public Aeroport(String name, double latitude, double longitude, String codeIATA) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codeIATA = codeIATA;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCodeIATA() {
        return codeIATA;
    }

    // Calcul de la distance vers un autre point (latitude, longitude)
    public double calculateDistance(double otherLatitude, double otherLongitude) {
        double earthRadius = 6371; // Rayon de la Terre en kilomètres
        double dLat = Math.toRadians(otherLatitude - this.latitude);
        double dLon = Math.toRadians(otherLongitude - this.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(otherLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c; // Retourne la distance en kilomètres
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", codeIATA='" + codeIATA + '\'' +
                '}';
    }
}
