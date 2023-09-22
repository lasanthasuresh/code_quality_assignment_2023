import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotel = new HashMap<>();
    static Map<String, Double> currencyExchange = new HashMap<>();
    static Map<String, Double> flight = new HashMap<>();

    static void list1(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String hotelList; 
        while ((hotelList = reader.readLine()) != null) {
            String[] hotelData = hotelList.split(",");
            flight.put(hotelData[0].toUpperCase(), Double.parseDouble(hotelData[1]));
        }
    }

    static void list2(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currencyExchangeList;
        while ((currencyExchangeList = reader.readLine()) != null) {
            String[] currencyExchangeData = currencyExchangeList.split(",");
            currencyExchange.put(currencyExchangeData[0].toUpperCase(), Double.parseDouble(currencyExchangeData[1]));
        }
    }

    static void list3(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String flightList;
        while ((flightList = reader.readLine()) != null) {
            String[] flightData = flightList.split(",");
            flight.put(flightData[0].toUpperCase(), Double.parseDouble(flightData[1]));
        }
    }

    public static void main(String[] args) {
        try {
            list1("data/hotel_rates.csv");
            list2("data/exchange_rates.csv");
            list3("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flight.getOrDefault(destination, 0.0);
            double hotelCost = hotel.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = currencyExchange.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double final_price_local_currency = totalCostUSD * currencyExchange.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, final_price_local_currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
