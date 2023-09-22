/* 
    EG_2020_4143
    Ranawaka N.L.N. 
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRateSet = new HashMap<>();
    static Map<String, Double> exchangeRateSet = new HashMap<>();
    static Map<String, Double> flightRateSet = new HashMap<>();

    static void hotelRates (String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelRateSet.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void exchangeRates (String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRateSet.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void flightRates (String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightRateSet.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRates("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightRates("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightRateSet.getOrDefault(destination, 0.0);
            double hotelCost = hotelRateSet.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRateSet.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" +
                String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * exchangeRateSet.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } 

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
