//EG_2020_4353
from csv import *

hotelRate = {}
exchangeRate = {}
flightCostforCity = {}

def calculateHotelCost(file):  
    with open(file) as hotelCost: 
        for row in reader(hotelCost):
            hotelRate[row[0]] = float(row[1])

def calculateExchangeRate(file): 
    with open(file) as e:
        for row in reader(e):
            exchangeRate[row[0].upper()] = float(row[1])

def calculateFlightCost(file):
    with open(file) as flightCost:
        for row in reader(flightCost):
            flightCostforCity[row[0]] = float(row[1])

def main():
    lhr('data/hotel_rates.csv')
    ler('data/exchange_rates.csv')
    lfr('data/flight_costs.csv')


    flight_Cost = flightCostforCity.get(input("Enter your destination: ").upper(), 0.0)
    hotel_Cost = hotelRate.get(input("Enter your destination: ").upper(), 0.0)

    stay_days = int(input("Enter your stay duration in days: "))
    hotel_Cost *= stay_days
    total_Cost = flight_Cost + hotel_Cost

    print(f"Flight cost: USD {flight_Cost:.2f}")
    print(f"Hotel cost for {stay_days} days: USD {hotel_Cost:.2f}")
    print(f"Total: USD {total_Cost:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(exchangeRate.keys())}): ")

    print(f"Total in {currency}: {total_Cost * exchangeRate[currency]:.2f}")

if __name__ == "__main__":
    main()
