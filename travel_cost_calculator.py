"""
EG/2020/4289
Wijebandara P.A.I

this calculator calculate cost for a travel

"""
from csv import *

a = {}
b = {}
c = {}

def _l_h_r(file):
        with open(file) as files:
            _r = reader(files)
        for row in _r:
         a[row[0]] = float(row[1])

def _l_e_r(file):
    """ read the file""" 
    with open(file) as files:
        _r = reader(files)
        for row in _r:
            b[row[0].upper()] = float(row[1]) * 1

def _l_f_r(file):
    with open(file) as files:
        _r = reader(files)
        for row in _r:
            c[row[0]] = float(row[1])

def main():
    """this is the """
    _l_h_r('data/hotel_rates.csv')
    _l_e_r('data/exchange_rates.csv')
    _l_f_r('data/flight_costs.csv')

    destiation = input("Enter your destination: ").upper()

    flight_cost = c.get(destiation, 0.0)
    hotel_cost = a.get(destiation, 0.0)

    days = int(input("Enter your stay duration in days: "))
    hotel_cost *= days
    total_cost = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {days} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total_cost:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    balance = total_cost * b[currency]
    print(f"Total in {currency}: {balance:.2f}")

if __name__ == "__main__":
    main()
