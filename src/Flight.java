import java.time.LocalDateTime;

public class Flight {
    private String airLineCode;
    private String airlineName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int number;
    private String departureIata; // Code IATA de l'aéroport de départ

    // Constructeur
    public Flight(String airLineCode, String airlineName, LocalDateTime arrivalTime, LocalDateTime departureTime, int number, String departureIata) {
        this.airLineCode = airLineCode;
        this.airlineName = airlineName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.number = number;
        this.departureIata = departureIata;
    }

    // Getter pour le code de la compagnie aérienne
    public String getAirLineCode() {
        return airLineCode;
    }

    // Getter pour le code IATA de l'aéroport de départ
    public String getDepartureIata() {
        return departureIata;
    }

    // Getter pour l'heure d'arrivée
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    // Getter pour l'heure de départ
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    // Redéfinition de la méthode toString
    @Override
    public String toString() {
        return "Flight{" +
                "airLineCode='" + airLineCode + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", number=" + number +
                ", departureIata='" + departureIata + '\'' +
                '}';
    }
}
