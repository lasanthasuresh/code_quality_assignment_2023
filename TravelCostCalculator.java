// Kavindya P.P 
// EG/2020/4021

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> Hotel= new HashMap<>();
    static Map<String, Double> currency = new HashMap<>();
    static Map<String, Double> Flight = new HashMap<>();

    static void hotel_rates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            Hotel.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void exchangerates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            currency.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void flight_costs(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            Flight.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotel_rates("data/hotel_rates.csv");
            exchange_rates("data/exchange_rates.csv");
            flight_costs("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = Flight.getOrDefault(destination, 0.0);
            double hotelCost = Hotel.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = currency.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double localPrice = totalCostUsd * currency.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, localPrice);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
