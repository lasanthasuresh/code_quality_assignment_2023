// Kumarasiri L.I.N. EG/2020/4034
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

    static void hotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String stayDuration; 
        while ((stayDuration = reader.readLine()) != null)
        {
            String[] hotelCost = stayDuration.split(",");
            a.put(hotelCost[0].toUpperCase(), Double.parseDouble(hotelCost[1]));
        }
        
    }

    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String selectedCurrency;
        while (( selectedCurrency= reader.readLine()) != null) {
            String[] totalCostUsd = selectedCurrency.split(",");
            b.put(totalCostUsd[0].toUpperCase(), Double.parseDouble(totalCostUsd[1]));
        }
    }

    static void flightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String destination;
        while ((destination = reader.readLine()) != null) {
            String[] flightCost= destination.split(",");
            c.put(totalCostUsd[0].toUpperCase(), Double.parseDouble(totalCostUsd[1]));
        }
    }

    public static void main(String[] args) 
    {
        try {
            hotelRates("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();
            double flightCost = c.getOrDefault(destination, 0.0);
            
            System.out.println("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            double hotelCost = a.getOrDefault(stayDuration, 0.0);

            hotelCost = hotelCost*stayDuration;
            double totalCostUsd = flightCost + hotelCost;

            System.out.println("Flight cost: USD %.2f", flightCost);
            System.out.println("Hotel cost (%d days): USD %.2f", stayDuration, hotelCost);
            System.out.println("Total: USD %.2f", totalCostUsd);

            String[] availableCurrencies = b.keySet().toArray(new String[0]);
            System.out.println("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * b.get(selectedCurrency);

            System.out.println("Total in %s: %.2f", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
