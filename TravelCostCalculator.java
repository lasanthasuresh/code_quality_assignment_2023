//EG/2020/3978 - Jayakody J.A.T.K.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flyingDestination = new HashMap<>();

    static void CalculateHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String hotelRateInput; 
        
        while ((hotelRateInput = reader.readLine()) != null) {
            String[] convertHotelRateToValue = hotelRateInput.split(",");
            hotelRates.put(convertHotelRateToValue[0].toUpperCase(), 
            Double.parseDouble(convertHotelRateToValue[1]));
        }
    }

    static void CalculateExchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String exchangeRateInput;
        while ((exchangeRateInput = reader.readLine()) != null) {
            String[] p = exchangeRateInput.split(",");

            exchangeRates.put(p[0].toUpperCase(), 
            Double.parseDouble(p[1]));
        }
    }

    static void CalculateFlightCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String flyingDestinationInput;
        while ((flyingDestinationInput = reader.readLine()) != null) {
            String[] p = flyingDestinationInput.split(",");
            flyingDestination.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            CalculateHotelRates("data/hotel_rates.csv");
            CalculateExchangeRates("data/exchange_rates.csv");
            CalculateFlightCost("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");

            String destination = reader.readLine().toUpperCase();

            double flight_cost = flyingDestination.getOrDefault(destination, 0.0);
            double hotel_cost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");

            int stay_duration = Integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd);

            String[] available_currencies = exchangeRates.keySet().toArray(new String[0]);

            System.out.print("Select your currency for final price estimation("
                            + String.join(", ", available_currencies) 
                            + "): ");

            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * exchangeRates.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
