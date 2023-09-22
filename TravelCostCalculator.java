// GALPAYAGE G D T G           EG/2020/3935

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    static void retrieveHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelRates.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void retrieveExchnageRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRates.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void retrieveFlightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCosts.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            retrieveHotelRates("data/hotel_rates.csv");
            retrieveExchnageRates("data/exchange_rates.csv");
            retrieveFlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();
            
            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double  finalPriceLocalCurrency = totalCostUsd * exchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
