// EG/2020/3798 Abesundara W.H.S.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator 
{
    static Map<String, Double> hotelCosts = new HashMap<>();
    static Map<String, Double> availableCurrancies = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();


    static void GetHotelCost(String file) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;

        while ((i = reader.readLine()) != null) 
        {
            String[] pharagraph = i.split(",");
            hotelCosts.put(pharagraph[0].toUpperCase(), Double.parseDouble(pharagraph[1]));
        }
    }


    static void GetAvailableCurrancies(String file) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;

        while ((i = reader.readLine()) != null) 
        {
            String[] pharagraph = i.split(",");
            availableCurrancies.put(pharagraph[0].toUpperCase(), Double.parseDouble(pharagraph[1]));
        }
    }


    static void GetFlightCost(String file) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;

        while ((i = reader.readLine()) != null) 
        {
            String[] pharagraph = i.split(",");
            flightCosts.put(pharagraph[0].toUpperCase(), Double.parseDouble(pharagraph[1]));
        }
    }


    public static void main(String[] args) {
        try {
            GetHotelCost("data/hotel_rates.csv");
            GetAvailableCurrancies("data/exchange_rates.csv");
            GetFlightCost("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCosts = c.getOrDefault(destination, 0.0);
            double hotelCost = hotelCosts.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayedDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayedDuration;

            // Calculating the total cost in USD
            double totalCostInUsd = flightCosts + hotelCost;


            System.out.printf("Flight cost: USD %.2f\n", flightCosts);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayedDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostInUsd);


            String[] availableCurrencies = availableCurrancies.keySet().toArray(new String[0]);


            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            // Converting the final price to User given currency
            double finalPriceLocalCurrency = totalCostInUsd * availableCurrancies.get(selectedCurrency);


            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
