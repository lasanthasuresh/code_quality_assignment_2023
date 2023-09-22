#EG/2020/4340 Piyankara n w m g p

from csv import *


user_a = {}
user_b = {}
user_c = {}

#function of hotel rates
def hotelRates(file):  
    with open(file) as hotel:
        r = reader(hotel)
        for row in r:
            user_a[row[0]] = float(row[1])

#function of exchange rates
def exchangeRates(file): 
    with open(file) as exchange:
        r = reader(exchange)
        for row in r:
            user_b[row[0].upper()] = float(row[1]) * 1 

#function of flight rates
def flightRates(file):
    with open(file) as flight:
        r = reader(flight)
        for row in r:
            user_c[row[0]] = float(row[1])

#function of main
def main():
    hotelRates('data/hotel_rates.csv')
    exchangeRates('data/exchange_rates.csv')
    flightRates('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper() #input of user destination

    f = user_c.get(destination, 0.0)
    h = user_a.get(destination, 0.0)

    days = int(input("Enter your stay duration in days: ")) #input of user stay duration
    h *= days
    total = f + h

    print(f"Flight cost: USD {f:.2f}") #printflight cost
    print(f"Hotel cost for {days} days: USD {h:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(user_b.keys())}): ")

    p = total * b[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
