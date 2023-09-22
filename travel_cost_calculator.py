"""
Morawaliyadda M.G.H.S.M
EG/2020/4078
"""
from csv import *

a = {}
b = {}
c = {}

def lhr(file): 
    """
    Function of Ihr

    """
    with open(file) as _hours:
        _reader = reader(_hours)
        for row in _reader:
            a[row[0]] = float(row[1])

def ler(file): 
    """
    Function of ler

    """
    with open(file) as e:
        _reader = reader(e)
        for row in _reader:
            b[row[0].upper()] = float(row[1]) * 1 

def lfr(file):
    """
    Function of lfr

    """
    with open(file) as f:
        _reader = reader(f)
        for row in _reader:
            c[row[0]] = float(row[1])

def main():
    """
    Main Function 

    """
    lhr('data/hotel_rates.csv')
    ler('data/exchange_rates.csv')
    lfr('data/flight_costs.csv')

    _days = input("Enter your destination: ").upper()

    f = c.get(_days, 0.0)
    _hours = a.get(_days, 0.0)

    days = int(input("Enter your stay duration in days: "))
    _hours *= days
    total = f + _hours

    print(f"Flight cost: USD {f:.2f}")
    print(f"Hotel cost for {days} days: USD {_hours:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    new = total * b[currency]
    print(f"Total in {currency}: {new:.2f}")

if __name__ == "__main__":
    main()
