//SENEVIRATHNA A.C. EG/2020/4206
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRatesMap = new HashMap<>();//To store hotel rates
    static Map<String, Double> exchangeRatesMap = new HashMap<>();//to store exchange rates
    static Map<String, Double> flightCostsMap = new HashMap<>();//To store flight costs
    //Store hotel rates to calculate later
    static void hotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            hotelRatesMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }
    //Store exchange rates to calculate later
    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            exchangeRatesMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }
    //Store flight costs to calculate later
    static void flightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            flightCostsMap.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            hotelRates("data/HotelRates.csv");
            exchangeRates("data/ExchangeRates.csv");
            flightCosts("data/FlightCosts.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCostsMap.getOrDefault(destination, 0.0);
            double hotelCost = hotelRatesMap.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;//Hotel rate multiply by staying days because stored hotel rate  values are for a single day

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRatesMap.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * exchangeRatesMap.get(selectedCurrency);//To show the total cost in required currency

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
