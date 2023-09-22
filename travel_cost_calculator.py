# EG/2020/3817
# Amarasinghe A.U

import csv
#this calulates the hotel rates
def loads_hotel_rates(file):
    hotel_rates = {}
    with open(file) as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            hotel_rates[row[0]] = float(row[1])
    return hotel_rates

#this calcualtes the exchange rates
def loads_exchange_rates(file):
    exchange_rates = {}
    with open(file) as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            exchange_rates[row[0].upper()] = float(row[1])
    return exchange_rates

def loads_flight_costs(file):
    flight_costs = {}
    with open(file) as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            flight_costs[row[0]] = float(row[1])
    return flight_costs

def main():
    hotel_rates = loads_hotel_rates('data/hotel_rates.csv')
    exchange_rates = loads_exchange_rates('data/exchange_rates.csv')
    flight_costs = loads_flight_costs('data/flight_costs.csv')

    destination = input("Enter your destination: ").upper()
    flight_cost = flight_costs.get(destination, 0.0)

    stay_duration = int(input("Enter the duration you will stay: "))
    hotel_cost = hotel_rates.get(destination, 0.0) * stay_duration
    total_cost = flight_cost + hotel_cost

    print(f"Flight cost: USD {flight_cost:.2f}")
    print(f"Hotel cost for {stay_duration} days: USD {hotel_cost:.2f}")
    print(f"Total: USD {total_cost:.2f}")

    available_currencies = ', '.join(exchange_rates.keys())
    selected_currency = input(f"Select the currency for final estimated value ({available_currencies}): ").upper()

    if selected_currency in exchange_rates:
        final_price = total_cost * exchange_rates[selected_currency]
        print(f"Total in {selected_currency}: {final_price:.2f}")
    else:
        print("Invalid currency has been selected!")

if __name__ == "__main__":
    main()
