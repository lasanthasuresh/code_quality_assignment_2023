import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//class to calculate the cost for travel
public class TravelCostCalculator {
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    static void l1(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            a.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void l2(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            b.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void l3(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            c.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            l1("data/hotel_rates.csv"); 
            l2("data/exchange_rates.csv");
            l3("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = c.getOrDefault(destination, 0.0);
            double hotel_cost = a.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: "); //to get time duration
            int stay_duration = Integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd); //to get total cost

            String[] available_currencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * b.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
