// Hariharasakthy N.   EG/2020/3956

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator 
{
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    
   private  void loadHotelRates(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                hotelRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }
    
    private  void loadExchangeRates(String file) throws IOException 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                exchangeRates.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    
    private  void loadFlightCosts(String file) throws IOException 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                flightCosts.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    public static void main(String[] args) 
    {
        try 
        {
            loadHotelRates("data/hotel_rates.csv");
            loadExchangeRates("data/exchange_rates.csv");
            loadFlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUSD * exchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
