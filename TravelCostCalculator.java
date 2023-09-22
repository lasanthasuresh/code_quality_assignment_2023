//EG-2020-4257
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {

    static Map<String, Double> mapHotelRates = new HashMap<>();
    static Map<String, Double> mapExchangeRates = new HashMap<>();
    static Map<String, Double> mapFlightCosts = new HashMap<>();

    static void hotelRates(String hotelRatesFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(hotelRatesFile));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] s = i.split(",");
            mapHotelRates.put(s[0].toUpperCase(), Double.parseDouble(s[1]));
        }
    }

    static void exchangeRates(String exchangeRatesFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(exchangeRatesFile));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] s = i.split(",");
            mapExchangeRates.put(s[0].toUpperCase(), Double.parseDouble(s[1]));
        }
    }

    static void flightCosts(String flightCostsFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(flightCostsFile));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] s = i.split(",");
            mapFlightCosts.put(s[0].toUpperCase(), Double.parseDouble(s[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRates("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = mapFlightCosts.getOrDefault(destination, 0.0);
            double hotelCost = mapHotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = mapExchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUSD * mapExchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
