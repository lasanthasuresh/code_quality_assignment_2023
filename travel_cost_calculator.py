
from csv import *

hotelRatesDetails = {}
exchangeRatesDetails = {}
flightRatesDetails = {}

def ListHotelRates(file):  
    with open(file) as hotels:
        r = reader(hotels)
        for row in r:
            hotelRatesDetails[row[0]] = float(row[1])

def ListExchangeRates(file): 
    with open(file) as exchanges:
        r = reader(exchanges)
        for row in r:
            exchangeRatesDetails[row[0].upper()] = float(row[1]) * 1 

def ListFlightRates(file):
    with open(file) as flights:
        r = reader(flights)
        for row in r:
            flightRatesDetails[row[0]] = float(row[1])

def main():
    ListHotelRates('data/hotel_rates.csv')
    ListExchangeRates('data/exchange_rates.csv')
    ListFlightRates('data/flight_costs.csv')

    desination_String = input("Enter your destination: ").upper()

    f = flightRatesDetails.get(desination_String, 0.0)
    h = hotelRatesDetails.get(desination_String, 0.0)

    stayInDays = int(input("Enter your stay duration in days: "))
    h *= days
    total = flightRatesDetails + h

    print(f"Flight cost: USD {f:.2f}")
    print(f"Hotel cost for {days} days: USD {h:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    p = total * b[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
