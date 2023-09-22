//EG_2020_4181
//Samaraweera S.A.D.C.K.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRate = new HashMap<>();
    static Map<String, Double> exchangeRate = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    // This function retrieves hotel rates
    static void getHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelRate.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // This function retrieves exchange rates
    static void getExchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRate.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // This function retrieves flight costs
    static void getFlightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCosts.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            getHotelRates("data/hotel_rates.csv");
            getExchangeRates("data/exchange_rates.csv");
            getFlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt user for the destination
            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRate.getOrDefault(destination, 0.0);

            // Prompt user for the duration
            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            // Calculate total cost
            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRate.keySet().toArray(new String[0]);

            // Prompt user for the currency for final price estimation
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            // Calculate final price
            double finalPriceLocalCurrency = totalCostUsd * exchangeRate.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
