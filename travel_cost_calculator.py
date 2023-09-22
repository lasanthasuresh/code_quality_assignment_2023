# EG/2020/4111 Perera G.A.L.S.

from csv import *

hotel_rates = {}
exchange_rates = {}
flight_costs = {}

def least_hotel_rate(file):  
    with open(file) as hotel:
        rate = reader(hotel)
        for row in rate:
            hotel_rates[row[0]] = float(row[1])

def least_exchange_rate(file): 
    with open(file) as exchange:
        rate = reader(exchange)
        for row in rate:
            exchange_rates[row[0].upper()] = float(row[1]) * 1 

def least_flight_cost(file):
    with open(file) as flight:
        rate = reader(flight)
        for row in rate:
            flight_costs[row[0]] = float(row[1])

def main():
    least_hotel_rate('data/hotel_rates.csv')
    least_exchange_rate('data/exchange_rates.csv')
    least_flight_cost('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()

    flight_cost = flight_costs.get(destination, 0.0)
    hotel_cost = hotel_rates.get(destination, 0.0)

    days = int(input("Enter your stay duration in days: "))
    hotel_cost *= days
    total = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {days} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(exchange_rates.keys())}): ")

    total_cost = total * exchange_rates[currency]
    print(f"Total in {currency}: {total_cost:.2f}")

if __name__ == "__main__":
    main()
