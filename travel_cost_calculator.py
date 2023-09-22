from csv import *

a = {}
b = {}
c = {}

def hotel_rates(file):  
    with open(file) as h:
        r = reader(h)
        for row in r:
            a[row[0]] = float(row[1])

def exchange_rates(file): 
    with open(file) as e:
        r = reader(e)
        for row in r:
            b[row[0].upper()] = float(row[1]) * 1 

def flight_costs(file):
    with open(file) as f:
        r = reader(f)
        for row in r:
            c[row[0]] = float(row[1])

def main():
    hotel_rates('data/hotel_rates.csv')
    exchange_rates('data/exchange_rates.csv')
    flight_costs('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()

    flight_cost = c.get(destination, 0.0)
    hotel_cost = a.get(destination, 0.0)

    days = int(input("Enter your stay duration in days: "))
    hotel_cost *= days
    total = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {days} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    p = total * b[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
