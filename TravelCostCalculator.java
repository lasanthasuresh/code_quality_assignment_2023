// EG/2020/3976 janugopan s
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> mapOne = new HashMap<>();
    static Map<String, Double> mapTwo = new HashMap<>();
    static Map<String, Double> mapThree = new HashMap<>();

    static void lineOne(String fileOne) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileOne));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            mapOne.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void lineTwo(String fileTwo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileTwo));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            mapTwo.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void lineThree(String fileThree) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileThree));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            mapThree.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
                lineOne("data/hotel_rates.csv");
                lineTwo("data/exchange_rates.csv");
                lineThree("data/flightCosts.csv");

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                System.out.print("Enter your destination: ");
                String destination = reader.readLine().toUpperCase();

                double flightCost = c.getOrDefault(destination, 0.0);
                double hotelCost = a.getOrDefault(destination, 0.0);

                System.out.print("Enter your stay duration in days: ");
                int stayDuration = Integer.parseInt(reader.readLine());
                hotelCost *= stayDuration;

                double total_cost_usd = flightCost + hotelCost;

                System.out.printf("Flight cost: USD %.2f\n", flightCost);
                System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
                System.out.printf("Total: USD %.2f\n", total_cost_usd);

                String[] availableCurrencies = b.keySet().toArray(new String[0]);
                System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
                String selectedCurrency = reader.readLine();

                double finalPriceLocalCurrency = total_cost_usd * b.get(selectedCurrency);

                System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);

            } 
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}
