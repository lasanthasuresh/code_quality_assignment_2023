import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelCostMap = new HashMap<>();
    static Map<String, Double> exchangeRateMap = new HashMap<>();
    static Map<String, Double> flightCostMap = new HashMap<>();


    // function to read the hostel_rates file Splitting the hostel_rates 
    // put the values to the map hostelCostMap 
    static void hostel_rates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelCostMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // function to read the exchange_rates file Splitting the exchange_rates 
    // put the values to the map exchangerateMap 
    static void exchange_rates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRateMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    // function to read the flight_costs file Splitting the flight_costs 
    // put the values to the map flightCostMap
    static void flight_costs(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCostMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hostel_rates("data/hotel_rates.csv");
            exchange_rates("data/exchange_rates.csv");
            flight_costs("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = flightCostMap.getOrDefault(destination, 0.0);
            double hotel_cost = hotelCostMap.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());

            hotel_cost *= stay_duration;
            //finding total cost
            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd);

            String[] available_currencies = exchangeRateMap.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.
            join(", ", available_currencies) + "):");
            String selected_currency = reader.readLine();

            //Changing the value to exchange rate
            double final_price_local_currency = total_cost_usd * exchangeRateMap.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Error in fetching data!!");
        }
    }
}
