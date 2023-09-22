//Sulaiman M.N. 
//EG.2020.4342
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator { //making 3 maps to get inputs
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    static void HotelRates(String file) throws IOException { 
        //function to calculate hotel rates
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input1; 
        while ((input1 = reader.readLine()) != null) {
            String[] p = i.split(","); //converting the single word into seperate letters
            a.put(p[0].toUpperCase(), Double.parseDouble(p[1]));//Converting all the letters to capital letters and pushing to the map
        }
        }
    }

    static void ExchangeRates(String file) throws IOException {
        //function to calculate exchange rates
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");//converting the single word into seperate letters
            b.put(p[0].toUpperCase(), Double.parseDouble(p[1]));//Converting all the letters to capital letters
        }
    }

    static void FlightCosts(String file) throws IOException {
        //function to calculate fligtcosts
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");//converting the single word into seperate letters
            c.put(p[0].toUpperCase(), Double.parseDouble(p[1])); //Converting all the letters to capital letters
        }
        }
    }

    public static void main(String[] args) {
        //doing exception handling to avoid unwanted inputs 
        try {
            HotelRates("data/hotel_rates.csv");
            ExchangeRates("data/exchange_rates.csv");
            FlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase(); //getting the destination input and converting to capital letters

            double flightCost = c.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());
            hotelCost *= stay_duration;

            double totalCostUsd = flightCost + hotelCost; //calculating the total cost

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

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
