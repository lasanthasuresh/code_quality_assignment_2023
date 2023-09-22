import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//Samasundara S.M.R.D.S
//EG_2020_4184


public class TravelCostCalculator {
    static Map<String, Double> hotelLists= new HashMap<>();
    static Map<String, Double> exchangeRateLists = new HashMap<>();
    static Map<String, Double> flightCostLists = new HashMap<>();

    static void hotelsList(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelLists.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRateLists.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void flightCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCostLists.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelsList("data/HotelRates.csv");
            exchangeRates("data/ExchangeRates.csv");
            flightCost("data/FlightCosts.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCostLists.getOrDefault(destination, 0.0);
            double hotelCost = hotelLists.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());


            //Calculate the Hotel Cost
            hotelCost *= stayDuration;


            //Total cost in USD
            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRateLists.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();


            //Convert the USD total into Local Currency Value
            double finalPriceLocalCurrency = totalCostUsd * exchangeRateLists.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
