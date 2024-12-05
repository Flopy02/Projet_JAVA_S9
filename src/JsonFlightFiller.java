import javax.json.*;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JsonFlightFiller {
    private ArrayList<Flight> list = new ArrayList<>();

    public JsonFlightFiller(String jsonString, World w) {
        try {
            JsonReader rdr = Json.createReader(new StringReader(jsonString));
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");

            if (results != null) {
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    try {
                        // Extraction des données du vol
                        JsonObject airline = result.getJsonObject("airline");
                        JsonObject departure = result.getJsonObject("departure");
                        JsonObject arrival = result.getJsonObject("arrival");
                        JsonObject flightObj = result.getJsonObject("flight");

                        String airlineCode = airline.getString("iata", "");
                        String airlineName = airline.getString("name", "");
                        String departureTimeStr = departure.getString("scheduled", "");
                        String arrivalTimeStr = arrival.getString("scheduled", "");
                        String flightNumberStr = flightObj.getString("number", "");
                        String departureIata = departure.getString("iata", "");

                        // Conversion des dates en LocalDateTime
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                        LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);
                        LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);

                        int flightNumber = Integer.parseInt(flightNumberStr);

                        // Création de l'objet Flight
                        Flight flight = new Flight(airlineCode, airlineName, arrivalTime, departureTime, flightNumber, departureIata);
                        list.add(flight);
                    } catch (Exception e) {
                        System.err.println("Error processing flight data: " + e.getMessage());
                    }
                }
            } else {
                System.err.println("No flight data available in JSON response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtenir la liste des vols
    public ArrayList<Flight> getList() {
        return list;
    }

    // Méthode pour afficher les informations de tous les vols
    public void displayFlights() {
        for (Flight flight : list) {
            System.out.println(flight);
        }
    }
}
