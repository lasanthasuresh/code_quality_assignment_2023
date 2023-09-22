//EG/2020/4200
//Senapathy J.D
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hashMap1 = new HashMap<>();
    static Map<String, Double> hashMap2 = new HashMap<>();
    static Map<String, Double> hashMap3 = new HashMap<>();



    //Function to find hotel rates
    
    static void hotelRates(String file) throws IOException {
    
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        while ((theReadingFile = reader.readLine()) != null) {
            String[] p = theReadingFile.split(",");
            hashMap1.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    //Function to find exchange rates

    static void exchangeRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String theReadingFile;
        while ((theReadingFile = reader.readLine()) != null) {
            String[] p = theReadingFile.split(",");
            hashMap2.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    //Function to find flight costs

    static void flightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String theReadingFile;
        while ((theReadingFile = reader.readLine()) != null) {
            String[] p = theReadingFile.split(",");
            hashMap3.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] args) {
        try {
            
            //Data sets of hotel rates, exchange rates and flight costs
            hotelRates("data/hotel_rates.csv");
            exchangeRates("data/exchange_rates.csv");
            flightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //Enter destination
            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            //Getting cost of flight
            double costOfFlight = hashMap3.getOrDefault(destination, 0.0);
            //Getting cost of hotel
            double costOfHotel = hashMap1.getOrDefault(destination, 0.0);

            //Enter duration of stay
            System.out.print("Enter your stay duration in days: ");
            int durationOfStay = Integer.parseInt(reader.readLine());

            costOfHotel *= durationOfStay;

            //Calculating the total cost in USD
            double totalCostInUSD = costOfFlight + costOfHotel;

            System.out.printf("Flight cost: USD %.2f\n", costOfFlight);

            System.out.printf("Hotel cost (%d days): USD %.2f\n", durationOfStay, costOfHotel);

            System.out.printf("Total: USD %.2f\n", totalCostInUSD);

            String[] availableCurrencies = hashMap2.keySet().toArray(new String[0]);

            //Enter preferred currency
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();
            
            //Calculating the final price in the preferred currency
            double finalPriceInlocalCurrency = totalCostInUSD * hashMap2.get(selectedCurrency);

            //Outputing the final cost
            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceInLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
