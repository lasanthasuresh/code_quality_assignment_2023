import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelCost = new HashMap<>();
    static Map<String, Double> exchange_rate_cost = new HashMap<>();
    static Map<String, Double> flightCost = new HashMap<>();

    static void hotelRateData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; // i is used for accessing a perticular file
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelCost.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void exchangeRateData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; // i is used for accessing a perticular file
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchange_rate_cost.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void flightCostData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCost.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRateData("data/hotel_rates.csv");
            exchangeRateData("data/exchange_rates.csv");
            flightCostData("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = flightCost.getOrDefault(destination, 0.0);
            double hotel_cost = hotelCost.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd);

            String[] available_currencies = exchange_rate_cost.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * exchange_rate_cost.get(selected_currency);

            System.out.printf("Total in %s: %.2f \n", selected_currency, final_price_local_currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
