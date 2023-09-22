// Samoda S.M.S.T- EG/2020/4185

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

    static void BufferReaderLine2(String file,letter) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((String line = reader.readLine()) != null) {
            String[] words = line.split(",");
            if(letter=='a')
                a.put(words[0].toUpperCase(), Double.parseDouble(words[1]));
            else if (letter=='b')
                b.put(words[0].toUpperCase(), Double.parseDouble(words[1]));
            else if (letter=='b')
                c.put(words[0].toUpperCase(), Double.parseDouble(words[1]));
        }
    }

    public static void main(String[] args) {
        try {
            BufferReaderLine1("data/hotel_rates.csv",a);
            BufferReaderLine2("data/exchange_rates.csv",b);
            BufferReaderLine3("data/flight_costs.csv",c);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = c.getOrDefault(destination, 0.0);
            double hotelCost = a.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());
           
            double totalCostUsd = flightCost + ( hotelCost *= stayDuration);

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * b.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        }   catch (IOException e) {
                e.printStackTrace();
        }
    }
}
