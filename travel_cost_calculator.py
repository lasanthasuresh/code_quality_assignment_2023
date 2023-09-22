# EG_2020_4237     THAYANAN T.
from csv import *

hotel_rates = {}
exchange_rates = {}
flight_costs = {}


def load_hotel_rates(file_path):
    with open(file_path) as file:
        csv_reader = reader(file)
        for row in csv_reader:
            hotel_rates[row[0]] = float(row[1])


def load_exchange_rates(file_path):
    with open(file_path) as file:
        csv_reader = reader(file)
        for row in csv_reader:
            exchange_rates[row[0].upper()] = float(row[1]) * 1


def load_flight_costs(file_path):
    with open(file_path) as file:
        csv_reader = reader(file)
        for row in csv_reader:
            flight_costs[row[0]] = float(row[1])


def main():
    load_hotel_rates('data/hotel_rates.csv')
    load_exchange_rates('data/exchange_rates.csv')
    load_flight_costs('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()

    flight_cost = flight_costs.get(destination, 0.0)
    hotel_cost_per_night = hotel_rates.get(destination, 0.0)

    duration = int(input("Enter your stay duration in days: "))
    hotel_cost = duration * hotel_cost_per_night
    total_cost = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {duration} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total_cost:.2f}")

    available_currencies = input(
        f"Select your currency for final price estimation ({', '.join(exchange_rates.keys())}): ")

    total_currencies = total_cost * exchange_rates[available_currencies]
    print(f"Total in {available_currencies}: {total_currencies:.2f}")


if __name__ == "__main__":
    main()
