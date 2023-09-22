// EG/2020/4065 
// Malisha A.P.D.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRateData = new HashMap<>();
    static Map<String, Double> exchangeRateData = new HashMap<>();
    static Map<String, Double> flightCostData = new HashMap<>();

    // Put .csv data into a HashMap
    static void toHashMapConverter(String file, HashMap hMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            // Put all data in three csv files into three separate HashMaps
            toHashMapConverter("data/hotel_rates.csv", hotelRateData);
            toHashMapConverter("data/exchange_rates.csv", exchangeRateData);
            toHashMapConverter("data/flight_costs.csv", flightCostData);

            // Get input stream ready
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            // Get flightCost and hotelCost depending on the destination user has input
            double flightCost = flightCostData.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);

            // Add cost according to stay duration user has input
            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRateData.keySet().toArray(new String[0]);

            System.out.print("Select your currency for final price estimation(" 
                + String.join(", ", availableCurrencies) + "): ");

            String selectedCurrency = reader.readLine();

            // calculate final price depending on local currency
            double finalPriceLocalCurrency = totalCostUsd * exchangeRateData.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, 
                finalPriceLocalCurrency);
        } catch (IOException e) {
            // Use a catch block in case of error
            e.printStackTrace();
        }
    }
}
