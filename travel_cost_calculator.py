from csv import *

a = {}
b = {}
c = {}

def lhr(file):  
    with open(file) as h:
        r = reader(h)
    for row in r:
        a[row[0]] = float(row[1])

def ler(file): 
    with open(file) as e:
        r = reader(e)
    for row in r:
        b[row[0].upper()] = float(row[1]) * 1 

def lfr(file):
    with open(file) as f:
        r = reader(f)
    for row in r:
        c[row[0]] = float(row[1])

def main():
    lhr('data/hotel_rates.csv')
    ler('data/exchange_rates.csv')
    lfr('data/flight_costs.csv')

    d = input("Enter your destination: ").upper()

    f = c.get(d, 0.0)
    h = a.get(d, 0.0)

    days = int(input("Enter your stay duration in days: "))
    h *= days
    total = f + h

    print(f"Flight cost: USD {f:.2f}")
    print(f"Hotel cost for {days} days: USD {h:.2f}")
    print(f"Total: USD {total:.2f}")

    currency = input(f"Select your currency for final price estimation ({', '.join(b.keys())}): ")

    p = total * b[currency]
    print(f"Total in {currency}: {p:.2f}")

if __name__ == "__main__":
    main()
