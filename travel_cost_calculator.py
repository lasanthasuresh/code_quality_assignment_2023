
from csv import reader

flight_costs = {}
exchange_rates = {}
hotel_rates = {}

def load_hotel_rates(file):
    with open(file) as h:
        csv_reader = reader(h)
        for row in csv_reader:
            hotel_rates[row[0]] = float(row[1])

def load_exchange_rates(file):
    with open(file) as e:
        csv_reader = reader(e)
        for row in csv_reader:
            exchange_rates[row[0].upper()] = float(row[1]) * 1

def load_flight_costs(file):
    with open(file) as f:
        csv_reader = reader(f)
        for row in csv_reader:
            flight_costs[row[0]] = float(row[1])

def main():
    load_hotel_rates('data/hotel_rates.csv')
    load_exchange_rates('data/exchange_rates.csv')
    load_flight_costs('data/flight_costs.csv')

    destination = input("Enter your final destination: ").upper()

    flight_cost = flight_costs.get(destination, 0.0)
    hotel_cost = hotel_rates.get(destination, 0.0)

    stay_duration = int(input("Enter your current stay duration in days: "))
    hotel_cost *= stay_duration
    total_cost = flight_cost + hotel_cost

    print(f"Flights total cost: USD {flight_cost:.2f}")
    print(f"Hotels total cost for {stay_duration} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total_cost:.2f}")

    currency = input(f"Select your currency for the final price estimation ({', '.join(exchange_rates.keys())}): ")

    estimated_price = total_cost * exchange_rates[currency]
    print(f"Totals in {currency}: {estimated_price:.2f}")

if __name__ == "__main__":
    main()
