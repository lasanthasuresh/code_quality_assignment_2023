import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//Calculates the Cost of Travel
public class TravelCostCalculator {

    //Initialize 3 Maps to store the data values for hotel rates, exchange rates and flight costs
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    //Gets the hotel rates input csv file and copies the data to the Map a
    static void l1(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            //Insert the first value (hotel name) of the line as uppercase and the second value (hotel rate) as a double
            a.put(p[0].toUpperCase(), Double.parseDouble(p[1])); 
        }
    }

    //Gets the exchange rates input csv file and copies the data to the Map b
    static void l2(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            //Insert the first value (currency name) of the line as uppercase and the second value (exchange rate) as a double
            b.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    //Gets the flight costs input csv file and copies the data to the Map c
    static void l3(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            //Insert the first value (Flight name) of the line as uppercase and the second value (Flight rate) as a double
            c.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {

            //Pass the 3 input csv files of hotel rates, exchange rates and flight costs to convert them to String arrrays
            l1("data/hotel_rates.csv");
            l2("data/exchange_rates.csv");
            l3("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Request and get the destination input string from the user
            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            //Get the flight cost for the relavant destination from the String array
            double flight_cost = c.getOrDefault(destination, 0.0);

            //Get the hotel cost for the relavant destination from the String array
            double hotel_cost = a.getOrDefault(destination, 0.0);

            //Request and get the stay duration number of days from the user as an integer
            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());

            //Hotel cost is calculated by multiplying the hotel cost per day with the stay duration
            hotel_cost *= stay_duration;

            //Total Flight and Hotel cost in USD is calculated 
            double total_cost_usd = flight_cost + hotel_cost;

            //Print the Flight cost, Hotel cost and Total cost respectively in separate lines in USD
            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", total_cost_usd);

            //Get the user input to select one currency out of the available currencies
            String[] available_currencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();

            //Calculate the final local currency values by multiplying the USD cost by the relavant exchange rate
            double final_price_local_currency = total_cost_usd * b.get(selected_currency);

            //Print the Final price in local currency
            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);

        } 
        //If there was a issue encountered when running the try block, print the stack trace
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
