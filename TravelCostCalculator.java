// EG/2020/3806
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRates = new HashMap<>(); 
    static Map<String, Double> exchangeRates = new HashMap<>(); 
    static Map<String, Double> flightCosts = new HashMap<>(); 

    static void loadHotelRates(String file) throws IOException {  
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line; 
        while ((line = reader.readLine()) != null) {
            String[] part = line.split(","); 
            hotelRates.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
        }
    }

    static void loadExchangeRates(String file) throws IOException {  
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] part = i.split(",");
            exchangeRates.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
        }
    }

    static void loadFlightCosts(String file) throws IOException { 
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] part = line.split(",");
            flightCosts.put(part[0].toUpperCase(), Double.parseDouble(part[1]));
        }
    }

    public static void main(String[] args) {
        try {
            // load datas from data file 
            loadHotelRates("data/hotel_rates.csv");
            loadExchangeRates("data/exchange_rates.csv");
            loadFlightCosts("data/flight_costs.csv");

            // read the files
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase(); // change to uppercase

            double flight_cost = flightCosts.getOrDefault(destination, 0.0); // set default value for flightCosts
            double hotel_cost = hotelRates.getOrDefault(destination, 0.0); // set default value for hotelRates

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());
            hotel_cost *= stay_duration;

            double total_cost_usd = flight_cost + hotel_cost;

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd);

            String[] available_currencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            double final_price_local_currency = total_cost_usd * exchangeRates.get(selected_currency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
