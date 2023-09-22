// CHANDEEPA H.G.S
// EG/2020/3862

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {

    //defining the map for storing the data in the file hotel_rates.csv 
    static Map<String, Double> fileMapperOne = new HashMap<>();
    //defining the map for storing the data in the file exchange_rates.csv
    static Map<String, Double> fileMapperTwo = new HashMap<>();
    //defining the map for storing the data in the file flight_costs.csv
    static Map<String, Double> fileMapperThree = new HashMap<>();


    //definig the functions for reading the files

    //define the function for reading the first file and store the data in to the map fileMapperOne 
    static void onReadAndStoreDataOne(String fileOne) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileOne));
        String fileName; 
        while ((fileName = fileReader.readLine()) != null) {
            String[] dataArray = fileName.split(",");
            fileMapperOne.put(dataArray[0].toUpperCase(), Double.parseDouble(dataArray[1]));
        }
    }

    //define the function for reading the second file and store the data in to the map fileMapperTwo 
    static void onReadAndStoreDataTwo(String fileTwo) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileTwo));
        String fileName;
        while ((fileName = fileReader.readLine()) != null) {
            String[] dataArray = fileName.split(",");
            fileMapperTwo.put(dataArray[0].toUpperCase(), Double.parseDouble(dataArray[1]));
        }
    }

    // define the function for reading the third file and store the data in to the map fileMapperThree
    static void onReadAndStoreDataThree(String fileThree) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileThree));
        String fileName;
        while ((fileName = fileReader.readLine()) != null) {
            String[] dataArray = fileName.split(",");
            fileMapperThree.put(dataArray[0].toUpperCase(), Double.parseDouble(dataArray[1]));
        }
    }


    //define the function for calculating the total cost of the flight and hotel cost in USD 
    public static void main(String[] args) {
        try {
            //defining the variables for storing the data in to the maps
            double flightCost ;
            double hotelCost;
            String userDestination;
            String selectedCurrency;
            double finalPriceLocalCurrency;
            double totalCostInUSD;


            //calling the functions for reading the files and store the data in to the maps
            onReadAndStoreDataOne("data/hotel_rates.csv");
            onReadAndStoreDataTwo("data/exchange_rates.csv");
            onReadAndStoreDataThree("data/flight_costs.csv");

            //defining the variables for storing the data in to the maps
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));

            //destination input
            System.out.print("Enter your destination: ");
            userDestination = reader.readLine().toUpperCase();
            fileMapperThree.getOrDefault(destination, 0.0);
            fileMapperOne.getOrDefault(destination, 0.0);

            //stay duration input
            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;
            totalCostInUSD = flightCost + hotelCost;

            //calculating the final price in the selected currency
            System.out.printf("Flight cost: USD %.2f\n", flight_cost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stay_duration, hotel_cost);
            System.out.printf("Total: USD %.2f\n", totalCostInUSD);

            //getting the user input for the currency
            String[] available_currencies = b.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", available_currencies) + "): ");
            selectedCurrency = reader.readLine();

            //calculating the final price in the selected currency
            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
