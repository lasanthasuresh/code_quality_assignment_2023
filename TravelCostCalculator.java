import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
//EG/2020/4252
public class TravelCostCalculator 
{
    static Map<String, Double> hotelRates = new HashMap<>();
    static Map<String, Double> exchangeRates = new HashMap<>();
    static Map<String, Double> flightCosts = new HashMap<>();

    static void getHotelRatesData(String file) throws IOException
     {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i; 
        while ((i = reader.readLine()) != null) 
        {
            String[] dataSet = i.split(",");
            hotelRates.put(dataSet[0].toUpperCase(), Double.parseDouble(dataSet[1]));
        }
    }

    static void getExchangeRatesData(String file) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String i;
        while ((i = reader.readLine()) != null) 
        {
            String[] dataSet = i.split(",");
            exchangeRates.put(dataSet[0].toUpperCase(), Double.parseDouble(dataSet[1]));
        }
    }

    static void getFlightCostsData(String file) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String i;
        while ((i = reader.readLine()) != null) 
        {
            String[] dataSet = i.split(",");
            flightCosts.put(dataSet[0].toUpperCase(), Double.parseDouble(dataSet[1]));
        }
    }

    static double getTotalCost(double flightCost, double hotelCost,int stayDuration)
    {

             hotelCost *= stayDuration;
            double total_cost_usd = flightCost + hotelCost;

            return total_cost_usd;

    }

    static double getFinalPrice(double totalCost,double currencyValue )
    {

         double final_price_local_currency = totalCost * currencyValue;
         return final_price_local_currency;
    }




    public static void main(String[] args) 
    {
        try
         {
            getHotelRatesData("data/hotel_rates.csv");
            getExchangeRatesData("data/exchange_rates.csv");
            getFlightCostsData("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flight_cost = flightCosts.getOrDefault(destination, 0.0);
            double hotel_cost = hotelRates.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stay_duration = Integer.parseInt(reader.readLine());

            double totalCost =getTotalCost();

            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", totalCost);

            String[] available_currencies = exchangeRates.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            String selected_currency = reader.readLine();


            double currencyValue =exchangeRates.get(selected_currency);
            
            double finalPrice=getFinalPrice(totalCost,currencyValue);

            System.out.printf("Total in %s: %.2f\n", selected_currency, finalPrice);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
