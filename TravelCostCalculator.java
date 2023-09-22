// EG/2020/4050 
// MADHUMALI W.H.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    static void CalculateHotelRate(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String HotelRate; 
        while ((HotelRate = reader.readLine()) != null) {
            String[] p = HotelRate.split(",");
            a.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void CalculateExchangeRate(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String Exchange;
        while ((Exchange = reader.readLine()) != null) {
            String[] p = Exchange.split(",");
            b.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void CalculateFlightRate(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String Flight;
        while ((Flight = reader.readLine()) != null) {
            String[] p = Flight.split(",");
            c.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            CalculateHotelRate("data/hotel_rates.csv");
            CalculateExchangeRate("data/exchange_rates.csv");
            CalculateFlightRate("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: "); //get destination from user
            String destination = reader.readLine().toUpperCase();

            double flight_cost = c.getOrDefault(destination, 0.0); //calculate the cost for flight 
            double hotel_cost = a.getOrDefault(destination, 0.0); //calculate the cost for hotel

            System.out.print("Enter your stay duration in days: "); //get the time duration from the user in days
            int stay_duration = Integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost; //total cost for flight and for hotel

            System.out.printf("Flight cost: USD %.2f\n", flight_cost); //display the flight cost 
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost); //display the hotel cost
            System.out.printf("Total: USD %.2f\n", total_cost_usd); // display the total cost

            String[] available_currencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): "); //get the cuurensy from the user for calculate the price
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * b.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency); //display the Total price for the customer's related currenncy
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
