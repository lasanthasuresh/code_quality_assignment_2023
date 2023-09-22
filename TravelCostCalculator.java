//Sankha A.D.N
//EG_2020_4194

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, double> hotelRates = new HashMap<>();
    static Map<String, double> exchangeRates = new HashMap<>();
    static Map<String, double> flightCosts = new HashMap<>();

    static void hotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelRates.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRates.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    static void flightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            c.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRates("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightCosts("data/flight_costs.csv");

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

            double finalPriceLocalCurrency = totalCostUsd * exchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
