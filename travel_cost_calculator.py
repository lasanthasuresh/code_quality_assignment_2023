#EG/2020/3922

from csv import *


HotelRatesData = {}
ExchangeRatesData = {}
FlightCostData = {}

#These data stored in hotel_rates.csv, exchange_rates.csv, and flight_costs.csv located in the data folder.
def hotelRates(file):  
    with open(file) as hotel:
        read = reader(hotel)
        for row in read:
            HotelRatesData[row[0]] = float(row[1])

def exchangeRates(file): 
    with open(file) as exchange:
        read = reader(exchange)
        for row in read:
            ExchangeRatesData[row[0].upper()] = float(row[1]) * 1 

def flightCosts(file):
    with open(file) as flight:
        read = reader(flight)
        for row in read:
            FlightCostData[row[0]] = float(row[1])

def main():
    hotelRates('data/hotel_rates.csv')
    exchangeRates('data/exchange_rates.csv')
    flightCosts('data/flight_costs.csv')

    Destination = input("Enter your destination: ").upper()

    flightCost = FlightCostData.get(Destination, 0.0)
    hotelCost = HotelRatesData.get(Destination, 0.0)

    days = int(input("Enter your stay duration in days: "))
    hotelCost *= days
    totalCost = flightCost + hotelCost

    print(f"Flight cost: USD {flightCost:.2f}")
    print(f"Hotel cost for {days} days: USD {hotelCost:.2f}")
    print(f"Total: USD {totalCost:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(ExchangeRatesData.keys())}): ")

    price = totalCost * ExchangeRatesData[currency]
    print(f"Total in {currency}: {price:.2f}")

if __name__ == "__main__":
    main()
