//EG_2020_3827
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {

    // Define maps for hotel rates, exchange rates, and flight costs
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    // Method to load data from a CSV file into a map
    static void loadData(String file, Map<String, Double> dataMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            dataMap.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
    }

    // calculate total cost
    static void calculateTotalCost() throws IOException {
        loadData("data/hotel_rates.csv", hotelRates);
        loadData("data/exchange_rates.csv", exchangeRates);
        loadData("data/flight_costs.csv", flightCosts);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your destination: ");
        String destination = reader.readLine().toUpperCase();

        double flightCost = flightCosts.getOrDefault(destination, 0.0);
        double hotelCost = hotelRates.getOrDefault(destination, 0.0);

        System.out.print("Enter your stay duration in days: ");
        int stayDuration = Integer.parseInt(reader.readLine());
        hotelCost *= stayDuration;

        double totalCostUSD = flightCost + hotelCost;

        System.out.printf("Flight cost: USD %.2f\n", flightCost);
        System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
        System.out.printf("Total: USD %.2f\n", totalCostUSD);

        String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
        System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
        String selectedCurrency = reader.readLine();

        double finalPriceLocalCurrency = totalCostUSD * exchangeRates.get(selectedCurrency);

        System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
    }

    public static void main(String[] args) {
        try {
            calculateTotalCost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
