// Name: Vihan D. I.
// Index No: EG/2020/4323

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRatesData = new HashMap<>();
    static Map<String, Double> exchangeRatesData = new HashMap<>();
    static Map<String, Double> flightCostsData = new HashMap<>();

    // For getting data from hotel_rates.csv file
    static void fetchHotelRatesData(String hotelRatesDataFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(hotelRatesDataFile));
        String input; 
        while ((input = reader.readLine()) != null) {
            String[] p = input.split(",");
            hotelRatesData.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // For getting data from exchange_rates.csv file
    static void fetchExchangeRatesData(String exchangeRatesDataFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(exchangeRatesDataFile));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] p = input.split(",");
            exchangeRatesData.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // For getting data from flight_costs.csv file
    static void fetchFlightCostsData(String flightCostsDataFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(flightCostsDataFile));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] p = input.split(",");
            flightCostsData.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            fetchHotelRatesData("data/hotel_rates.csv");
            fetchExchangeRatesData("data/exchange_rates.csv");
            fetchFlightCostsData("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCostsData.getOrDefault(destination, 0.0);
            double hotelCost = hotelRatesData.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRatesData.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * exchangeRatesData.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
