// Nettasinghe N.A.O.D : EG/2020/4097

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exhcnageRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();
    
    /*
        @parameter file : csv file path which containes hotel rates
        return void 
        read the file from given file path and extracts the data to the hotel Rate map
    */
    static void loadHotelRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i; 
        while ((i = reader.readLine()) != null) {
            String[] hotelRateReadLine = i.split(",");
            hotelRates.put(hotelRateReadLine[0].toUpperCase(), Double.parseDouble(hotelRateReadLine[1]));
        }
    }

    /*
        @parameter file : csv file path which containes exchange rates
        return : void 
        read the csv file from given file path and extracts the data to the exchange rate map
    */
    static void loadExchangerRates(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] exchangeReadLine = i.split(",");
            exhcnageRates.put(exchangeReadLine[0].toUpperCase(), Double.parseDouble(exchangeReadLine[1]));
        }
    }

    /*
        @parameter  file : csv file path which containes flight costs
        return void 
        read the file from given file path and extracts the data to the flightCost Map
    */
    static void loadFlightCosts(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String i;
        while ((i = reader.readLine()) != null) {
            String[] flightCostLine = i.split(",");
            flightCosts.put(flightCostLine[0].toUpperCase(), Double.parseDouble(flightCostLine[1]));
        }
    }

    static double calculateTotalPrice(double flightCost, double hotelCost, double stayDuration){
        hotelCost *= stayDuration;    // hotel cost = (hotel cost) x (number of dates stayed)
        double totalCostUSD = flightCosts + hotelCost;  // calculate the total cost by adding flight cost and hotelCost 
        return totalCost; 

    }

    public static void main(String[] args) {
        try {
            loadHotelRates("data/hotel_rates.csv");
            loadExchangerRates("data/exchange_rates.csv");
            loadFlightCosts("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCosts = flightCosts.getOrDefault(destination, 0.0);
            double hotelCost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
                                           

            double totalCostUSD = calculateTotalPrice(flightCost,hotelCost, stayDuration);    
            

            System.out.printf("Flight cost: USD %.2f\n", flightCosts);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUSD);

            String[] availableCurrencies = exhcnageRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceInLocalCurrency = totalCostUSD * exhcnageRates.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceInLocalCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
