// EG/2020/3986 Jayasinghe D.M.S.N.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotel = new HashMap<>();
    static Map<String, Double> currency = new HashMap<>();
    static Map<String, Double> flightCost = new HashMap<>();

    static void hotelRate(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String rate; 
        while ((rate = reader.readLine()) != null) {
            String[] p = rate.split(",");
            hotel.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            currency.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void flightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCost.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRate("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = flightCost.getOrDefault(destination, 0.0);
            double hotel_cost = hotel.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());

            // calculating the hotel cost
            hotel_cost *= stay_duration;

            // calculating the total cost
            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: \tUSD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): \tUSD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: \tUSD %.2f\n", total_cost_usd);

            String[] available_currencies = currency.keySet().toArray(new String[0]);

            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * currency.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
