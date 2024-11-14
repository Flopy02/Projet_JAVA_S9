public class Aeroport {
    private String name;
    private double latitude;
    private double longitude;
    private String codeIATA;

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
