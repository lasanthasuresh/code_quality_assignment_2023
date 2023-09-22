// EG_2020_4254
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

    static void l1(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i; 
        while ((i=reader.readLine()) != null) {
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
            double flightCost = c.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);
           

            System.out.print("Enter your stay duration in days: ");

            int stayDuration = Integer.parseInt(reader.readLine());

            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;
            String[] availableCurrencies = b.keySet().toArray(new String[0]);
            String selectedCurrency = reader.readLine();
            double finalPriceLocalCurrency = totalCostUsd * b.get(selectedCurrency);


            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } 

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
