// EG/2020/3822 - Anjana G.W.B.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    //declare hashmaps as files read
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    //Read cvs file and assign into hash map  
    static void readCsvFile(String file, HashMap<String, Double> hashMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line; 
        while ((line = reader.readLine()) != null) {
            String[] stringArray = line.split(",");
            hashMap.put(stringArray[0].toUpperCase(), Double.parseDouble(stringArray[1]));
        }
    }

    public static void main(String[] args) {
        try {
            //Read csv and assign into hash maps 
            readCsvFile("data/hotel_rates.csv", hotelRates);
            readCsvFile("data/exchange_rates.csv", exchangeRates);
            readCsvFile("data/flight_costs.csv", flightCosts);

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = userInputReader.readLine().toUpperCase();

            double flightCost = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(userInputReader.readLine());

            //Total hotel cost should propotional with days of stay.
            hotelCost *= stayDuration;

            //Total cost include both flight and hotel cost
            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = userInputReader.readLine();

            //Value of the local currency should update with exchange rate.
            double finalPriceLocalCurrency = totalCostUSD * exchangeRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
