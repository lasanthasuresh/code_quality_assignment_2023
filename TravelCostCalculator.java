// ABEYSEKARA P.K.
// EG/2020/3799

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    // Initialize maps to store hotel rates, exchange rates, and flight costs
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    // Method to read hotel rates from a file and populate the hotelRates map
    static void readHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            hotelRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
        reader.close();
    }

    // Method to read exchange rates from a file and populate the exchangeRates map
    static void readExchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            exchangeRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
        reader.close();
    }

    // Method to read flight costs from a file and populate the flightCosts map
    static void readFlightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            flightCosts.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
        reader.close();
    }

    public static void main(String[] args) {
        try {
            // Read data files and populate maps
            readHotelRates("data/hotel_rates.csv");
            readExchangeRates("data/exchange_rates.csv");
            readFlightCosts("data/flight_costs.csv");

            // Get user input for destination and stay duration
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());

            // Calculate total cost in USD
            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0) * stayDuration;
            double totalCostUSD = flightCost + hotelCost;

            // Display costs in USD
            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            // Get user input for currency and calculate final price in local currency
            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine().toUpperCase();
            double finalPriceLocalCurrency = totalCostUSD * exchangeRates.get(selectedCurrency);

            // Display final price in local currency
            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
