//Rusham
//EG_20_4170

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    private static final Map<String, Double> hotelCharge = new HashMap<>();      
    private static final Map<String, Double> currencyConversion = new HashMap<>();
    private static final Map<String, Double> ticketPrice = new HashMap<>();

//Function for loading Hotel charges
    private static void retrievehotelCharge(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                hotelCharge.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

//Function for loading currency conversion
    private static void retrievecurrencyConversion(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                currencyConversion.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

//Function for loading Ticket price 
    private static void retrieveticketPrice(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                ticketPrice.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }

    public static void main(String[] args) {
        try {
            retrieveHotelRates("data/hotel_rates.csv");
            retrievecurrencyConversion("data/exchange_rates.csv");
            retrieveticketPrice("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = ticketPrice.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = currencyConversion.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUSD * currencyConversion.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

The changes of the code;

1. Added some comments to understand the code well 
2. Formatted the code by adding indents and spacing.
3. The variables `a`, `b`, and `c` were changed to`hotelCharge`, `currencyConversion`, and `ticketPrice` 
    to make the variable and the function more meaningful and improve the readability of the code.
    Moreover the variable and the functions were named according to the java naming conventions to improve
    readability.
4. Used the try operation to make sure there are no errors when closing.
5. The funtion for retrieveing the datas are wriiten on seperate functions to clean the code and avoid 
    repetitions(In this case there are no repetitions but it can be used in case of an update)



