// Nehara S.A.T. EG/2020/4092

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    private static final Map<String, Double> hotelRates = new HashMap<>();
    private static final Map<String, Double> exchangeRates = new HashMap<>();
    private static final Map<String, Double> flightCosts = new HashMap<>();

    /**
     * loads CSV type hotelData to a Map
     * @param-file contains csv type hotelRates data
     * @param-Map 
     */
    private static void loadHotelRates(String file,Map<String, Double> data) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
            }
        }
    }


    public static void main(String[] args) {
        try {
            loadData("data/hotel_rates.csv", hotelRates);
            loadData("data/exchange_rates.csv", exchangeRates);
            loadData("data/flight_costs.csv", flightCosts);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");

            String destination = reader.readLine().toUpperCase();
            double flightCost = flightCosts.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");

            int stayDuration = Integer.parseInt(reader.readLine());

            //hotelCost is calculated by multiplying hotelcost by stay_duration
            hotelCost *= stay_duration;

            //totalCostUsd is calculated by multiplying hotelcost by stay_duration
            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);

            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");

            String selectedCurrency = reader.readLine();
            double finalPriceLocalCurrency = totalCostUsd * exchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selected_currency, final_price_local_currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
