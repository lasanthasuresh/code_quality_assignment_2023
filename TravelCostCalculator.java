//EG_2020_3990
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    private static final Map<String, Double> hotel = new HashMap<>();
    private static final Map<String, Double> exchange = new HashMap<>();
    private static final Map<String, Double> flight = new HashMap<>();

    // load hotel from line
    private static void loadHotel(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                hotelRates.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
            }
        }
    }

    // load exchange from line
    static void loadExchange(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] part = line.split(",");
            b.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
        }
    }

    // load flight from line
    private static void loadFlight(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                flightCosts.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
            }
        }
    }

    public static void main(String[] args) {
        try {
            loadHotel("data/hotel_rates.csv");
            loadExchange("data/exchange_rates.csv");
            loadFlight("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flight.getOrDefault(destination, 0.0);
            double hotelCost = hotel.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = exchange.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUSD * exchange.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}