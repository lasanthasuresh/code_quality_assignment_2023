from csv import *

a = {}
b = {}
c = {}

def l_hotel_rates(file):  
    with open(file) as hotel:
        r = reader(hotel)
        for row in r:
            a[row[0]] = float(row[1])

def l_exchange_rates(file): 
    with open(file) as exchange_rate:
        r = reader(exchange_rate)
        for row in r:
            b[row[0].upper()] = float(row[1]) * 1 

def l_flight_costs(file):
    with open(file) as flight:
        r = reader(flight)
        for row in r:
            c[row[0]] = float(row[1])

def main():
    l_hotel_rates('data/hotel_rates.csv')
    l_exchange_rates('data/exchange_rates.csv')
    l_flight_costs('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()

    flight = c.get(destination, 0.0)
    hotel = a.get(destination, 0.0)

    int days = input("Enter your stay duration in days: ")
    hotel *= days
    total = flight + hotel

    print(f"Flight cost: USD {flight:.2f}")
    print(f"Hotel cost for {days} days: USD {hotel:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    p = total * b[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
