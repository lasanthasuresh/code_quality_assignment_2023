
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

    flightCost = flightRatesDetails.get(desination_String, 0.0)
    hotelCost = hotelRatesDetails.get(desination_String, 0.0)

    stayInDays = int(input("Enter your stay duration in days: "))
    hotelCost *= stayInDays
    total = flightRatesDetails + hotelCost

    print(f"Flight cost: USD {f:.2f}")
    print(f"Hotel cost for {stayInDays} days: USD {h:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(exchangeRatesDetails.keys())}): ")

    p = total * exchangeRatesDetails[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
