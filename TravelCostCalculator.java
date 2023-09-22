// EG_2020_3912
// DISSANAYAKE D.M.M.I.T

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class TravelCostCalculator {

    // Constants for data files
    private static final String HOTEL_RATES_FILE = "data/hotel_rates.csv";
    private static final String EXCHANGE_RATES_FILE = "data/exchange_rates.csv";
    private static final String FLIGHT_COSTS_FILE = "data/flight_costs.csv";

    // Maps to store data
    private static final Map<String, Double> hotelRates = new HashMap<>();
    private static final Map<String, Double> exchangeRates = new HashMap<>();
    private static final Map<String, Double> flightCosts = new HashMap<>();

    // Load hotel rates from a file into the hotelRates map
    private static void loadHotelRates(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                hotelRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    // Load exchange rates from a file into the exchangeRates map
    private static void loadExchangeRates(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                exchangeRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    // Load flight costs from a file into the flightCosts map
    private static void loadFlightCosts(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                flightCosts.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Load data from files
            loadHotelRates("data/hotel_rates.csv");
            loadExchangeRates("data/exchange_rates.csv");
            loadFlightCosts("data/flight_costs.csv");

            // Input from user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            // Calculate flight cost and hotel cost
            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            // Input number of days stay from user 
            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());

            // Update hotelCost
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            /**
             * Print flightCost
             * Print stayDuration and hotelCost
             * Print totalCoust
             */
            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            // Calculate finalPrice
            double finalPriceLocalCurrency = totalCostUSD * exchangeRates.get(selectedCurrency);

            // Print selectedCurrency & finalPriceLocalCurrency
            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
