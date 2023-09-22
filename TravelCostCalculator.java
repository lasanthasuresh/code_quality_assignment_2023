//EG/2020/4266
//Weerasekara W.M.N.S


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> HotelRates = new HashMap<>();
    static Map<String, Double> ExchangeRates = new HashMap<>();
    static Map<String, Double> FlightCost = new HashMap<>();

    static void setHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            HotelRates.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void setExchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            ExchangeRates.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    static void setflightCost(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            FlightCost.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            setHotelRates("data/hotel_rates.csv");
            setExchangeRates("data/exchange_rates.csv");
            setFlightCost("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightcost = FlightCost.getOrDefault(destination, 0.0);
            double hotelcost = HotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCost_usd);

            String[] availableCurrencies = ExchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * ExchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
