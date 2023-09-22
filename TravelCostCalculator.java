// EG/2020/4141 
// Ranasinghe B.M.O.Y
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

//CSV File reader
    static void l_1(String file, Map array) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String data;
        while ((data = reader.readLine()) != null) {
            String[] para = data.split(",");
            array.put(para[0].toUpperCase(), Double.parseDouble(para[1]));
        }
    }

//Main Function
    public static void main(String[] args) {
        try {
            l_1("data/hotel_rates.csv",a);
            l_1("data/exchange_rates.csv", b);
            l_1("data/flight_costs.csv", c);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();
            
            double flightCost = c.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * b.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);

        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
