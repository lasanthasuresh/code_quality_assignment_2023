//EG_2020_4113
//PERERA K.R.D

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

// This will calculate the Total cost of the travel.
public class TravelCostCalculator {
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    //Inserting data to hotelRateList
    static void hotelRateList(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input; 
        while ((input = reader.readLine()) != null) {
            String[] parameters = input.split(",");
            hotelRates.put(parameters[0].toUpperCase(), Double.parseDouble(parameters[1])); //adding inputs to map
        }
    }

    //Inserting data to exchangeRateList
    static void exchangeRateList(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] parameters = input.split(",");
            exchangeRates.put(parameters[0].toUpperCase(), Double.parseDouble(parameters[1]));//adding inputs to map
        }
    }

    //Inserting data to flightCostList
    static void flghtCostList(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] parameters = input.split(",");
            flightCosts.put(parameters[0].toUpperCase(), Double.parseDouble(parameters[1]));//adding inputs to map
        }
    }



    public static void main(String[] args) {
        try {
            //datafiles
            hotelRateList("data/hotel_rates.csv");
            exchangeRateList("data/exchange_rates.csv");
            flightCostList("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = flightCosts.getOrDefault(destination, 0.0);
            double hotel_cost = hotelRates.getOrDefault(destination, 0.0);

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
