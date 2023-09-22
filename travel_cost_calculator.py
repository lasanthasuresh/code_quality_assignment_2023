# EG / 2020 / 3894 
# Didulani P.K.S

from csv import *

hotel_rates_list    = {}
exchange_rates_list = {}
flight_costs_list   = {}

def hotel_rates(file):  
    with open(file) as h:
        r = reader(h)
        for row in r:
            hotel_rates_list[row[0]] = float(row[1])

def exchange_rates(file): 
    with open(file) as e:
        r = reader(e)
        for row in r:
            exchange_rates_list[row[0].upper()] = float(row[1]) * 1 

def flight_costs(file):
    with open(file) as f:
        r = reader(f)
        for row in r:
            flight_costs_list[row[0]] = float(row[1])

def main():
    hotel_rates('data/hotel_rates.csv')
    exchange_rates('data/exchange_rates.csv')
    flight_costs('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()

    flight_cost = flight_costs_list.get(destination, 0.0)
    hotel_cost = hotel_rates_list.get(destination, 0.0)

    no_of_stay_days = int(input("Enter your stay duration in days: "))

    # to calculate the total cost we calculate the totel hotel_cost
    hotel_cost *= no_of_stay_days 

    total = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {days} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(exchange_rates_list.keys())}): ")

    # To get the totel amount in customer currency we use p
    p = total * exchange_rates_list[currency]

    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
