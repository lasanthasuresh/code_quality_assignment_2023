# Name: Chnaduka P.A.K      Index : EG/2020/3865


from csv import reader

# Initialize dictionaries to store data from CSV files
hotel_rates = {}
exchange_rates = {}
flight_costs = {}

# Function to load hotel rates from a CSV file
def load_hotel_rates(file_path):
    with open(file_path) as hotel_file:
        csv_reader = reader(hotel_file)
        for row in csv_reader:
            hotel_rates[row[0]] = float(row[1])

# Function to load exchange rates from a CSV file
def load_exchange_rates(file_path):
    with open(file_path) as exchange_file:
        csv_reader = reader(exchange_file)
        for row in csv_reader:
            exchange_rates[row[0].upper()] = float(row[1])

# Function to load flight costs from a CSV file
def load_flight_costs(file_path):
    with open(file_path) as flight_file:
        csv_reader = reader(flight_file)
        for row in csv_reader:
            flight_costs[row[0]] = float(row[1])

# Main function for the program
def main():
    # Load data from CSV files
    load_hotel_rates('data/hotel_rates.csv')
    load_exchange_rates('data/exchange_rates.csv')
    load_flight_costs('data/flight_costs.csv')

    # Get user input for the destination
    destination = input("Enter your destination: ").upper()

    # Calculate flight and hotel costs
    flight_cost = flight_costs.get(destination, 0.0)
    hotel_cost = hotel_rates.get(destination, 0.0)

    # Get user input for the stay duration
    days = int(input("Enter your stay duration in days: "))
    
    # Calculate the total hotel cost for the specified duration
    total_hotel_cost = hotel_cost * days
    
    # Calculate the total cost (flight + hotel)
    total_cost = flight_cost + total_hotel_cost

    # Display the costs in USD
    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {days} days: USD {total_hotel_cost:.2f}")
    print(f"Total: USD {total_cost:.2f}")

    # Get user input for the desired currency
    currency = input(f"Select your currency for final price estimation ({', '.join(exchange_rates.keys())}): ")

    # Convert the total cost to the selected currency
    price_in_selected_currency = total_cost * exchange_rates[currency]
    print(f"Total in {currency}: {price_in_selected_currency:.2f}")

if __name__ == "__main__":
    main()
