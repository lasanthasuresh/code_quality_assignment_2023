//EG_2020_4168  - Rupasinghe A.A.H.S

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    // Receiving Hotel Rate , Exchange Rate , Flight Costs Data From .csv Files and Split Those By ","

    static void hotelRatesData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            a.put(p[0].toUpperCase(),
            Double.parseDouble(p[1]));
        }
    }

    static void exchangeRatesData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            b.put(p[0].toUpperCase(), 
            Double.parseDouble(p[1]));
        }
    }

    static void flightCostsData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] p = i.split(",");
            c.put(p[0].toUpperCase(), Double.parseDouble(p[1]));
        }
    }

    public static void main(String[] arguments) {
        try {
            hotelRatesData("data/hotel_rates.csv");
            exchangeRatesData("data/exchange_rates.csv");
            flightCostsData("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Get Destination From the User and Assign to Destination

            System.out.print("Enter Your Destination: ");

            String destination = reader.readLine().toUpperCase();
            double flightCost = c.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);
            
            // Get Duration of Stay From the User and Assign to Stay Duration

            System.out.print("Enter Your Ytay Duration In Days: ");
            int stayDuration = Integer.parseInt(reader.readLine());

            //Calculating the Cost in USD

            hotelCost *= stayDuration;
            double totalCostUSD = flightCost + hotelCost;

            System.out.printf("Flight Cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel Cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total Cost: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = b.keySet().toArray(new String[0]);

            // Get Available Curruncy From the User and Assign to Selected Curruncy
            System.out.print("Select Your Currency for Final Price Estimation("
             + String.join(", ", availableCurrencies) + "): ");

            String selectedCurrency = reader.readLine();

            // Converting Cost from USD to Selected Curruncy and Print the Final Cost to Pay

            double finalPriceLocalCurrency = totalCostUSD * b.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);

        } 

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
