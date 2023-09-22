// EG/2020/4075, Mihiran K.M.T.S



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, double> a = new HashMap<>();
    static Map<String, double> b = new HashMap<>();
    static Map<String, double> c = new HashMap<>();


    //hostal data file give and they are map to a
    static void hostal_rates_data(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            a.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    //USD exchange rate data file give and they are map to b
    static void exchange_rates_data(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            b.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    //flight costs data file given and they are map to c
    static void flight_costs_data(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            c.put(p[0].toUpperCase(), double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hostal_rates_data("data/hotel_rates.csv");
            exchange_rates_data("data/exchange_rates.csv");
            flight_costs_data("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = c.getOrDefault(destination, 0.0);
            double hotel_cost = a.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost;

            System.out.print("Flight cost: USD %.2f\n", flight_cost); //print total flight cost with currency USD 2 decimal point
            System.out.print("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost); //print total hostal cost with days and given by USD currency and 2 decimal point
            System.out.print("Total: USD %.2f\n", total_cost_usd); //print total cost of the travel

            String[] available_currencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * b.get(selected_currency);

            System.out.print("Total in %s: %.2f\n", selected_currency, final_price_local_currency); //print total cost given with local currency 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
