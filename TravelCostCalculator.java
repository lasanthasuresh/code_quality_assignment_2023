// EG/2020/3975
// Iyenshi A.U.T.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    // hotelCostList, exchangeCostList, flightCostList are using for converting csv to a map and using whenever want.
    static Map<String, Double> hotelCostList = new HashMap<>();
    static Map<String, Double> exchangeCostList = new HashMap<>();
    static Map<String, Double> flightCostList = new HashMap<>();

    /*
    Each of these following functions are for taking the inputs of CSV files and making a map list for functioning.
    */

    static void getHotelCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input ; 
        while ((input = reader.readLine()) != null) {
            String[] item = input.split(",");
            hotelCostList.put(item[0].toUpperCase(), Double.parseDouble(item[1]));
        }
    }

    static void getExchangeCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] item = input.split(",");
            exchangeCostList.put(item[0].toUpperCase(), Double.parseDouble(item[1]));
        }
    }

    static void getFlightCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input;
        while ((input = reader.readLine()) != null) {
            String[] item = input.split(",");
            flightCostList.put(item[0].toUpperCase(), Double.parseDouble(item[1]));
        }
    }

    public static void main(String[] args) {
        try {
            getHotelCost("data/hotel_rates.csv");
            getExchangeCost("data/exchange_rates.csv");
            getExchangeCost("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            /*
            In this section the flight cost and hotel cost are calculated according to the given destination.
            Here, 
            filghtCost, hotelCost is found through the map created.
            */
            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCostList.getOrDefault(destination, 0.0);
            double hotelCost = hotelCostList.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            /*
            This section is for calculating the total cost and diplaying it for allowing to select a custome's preference currency
            */
            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            /*
            This section is for customer to select one of currencies available and to display the price should be paid according to their currency.
             */
            String[] availableCurrencies = exchangeCostList.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * flightCostList.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
