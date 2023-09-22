#Hettiarachchi UADD
#EG_2020_3964

#this is a calculator for a shop
import csv


#defining of variables
discounts = {}
taxes = {}
promotions = {}
current_rates = {}
inventry = {}


#functions to calculate discount
def ld_discounts(file):
    with open(file) as discount_file:
        r = csv.reader(discount_file)
        for rw in r:
            discounts[rw[0].upper()] = float(rw[1])

#function to calculate taxes
def ld_taxes(file):
    with open(file) as tax_file:
        r = csv.reader(tax_file)
        for rw in r:
            taxes[rw[0].upper()] = float(rw[1])


#function to calculate promotions
def ld_promotions(file):
    with open(file) as promotion_file:
        r = csv.reader(promotion_file)
        for rw in r:
            promotions[rw[0]] = float(rw[1])


#function to calculate current rates
def ld_current_rates(file):
    with open(file) as currency_file:
        r = csv.reader(currency_file)
        for rw in r:
            current_rates[rw[0].upper()] = float(rw[1])

#function to calculate inventries
def ld_inventry(file):
    with open(file) as inventry_file:
        r = csv.reader(inventry_file)
        for rw in r:
            inventry[rw[0].upper()] = int(rw[1])

#defining the main function
def main():

    ld_discounts('data/discounts.csv')
    ld_taxes('data/tax_rates.csv')
    ld_promotions('data/promotions.csv')
    ld_current_rates('data/currency_rates.csv')
    ld_inventry('data/inventory.csv')

    with open('shopping_cart.csv') as file:
        cart = {rw[0]: int(rw[1]) for rw in csv.reader(file)}

    total_price_usd = 0

    for item, quantity in cart.items():

        item = item.upper()

        if inventry.get(item, 0) < quantity:
            print(f"Cannot proceed, insufficient inventory for {item}")
            return

        promo = promotions.get(item)
        if promo:
            quantity = quantity - (quantity // promo)
        discount = discounts.get(item, 0)
        price = 1.0  
        discounted_price = price - (price * discount)
        item_price = discounted_price * quantity
        total_price_usd += item_price
        inventry[item] -= quantity 

    available_states = list(taxes.keys())
    print("Enter your state. Available states are: ", ', '.join(available_states))
    state = input()

    tax_rate = taxes.get(state, 0)
    tax_amount = total_price_usd * tax_rate

    final_price_usd = total_price_usd + tax_amount

    print("-------- SHOPPING CART SUMMARY --------")
    print("Item   | Qty | Price")
    print("-----------------------------")
    for item, quantity in cart.items():
        print(f"{item.ljust(6)} | {str(quantity).ljust(3)} | USD {discounted_price * quantity:.2f}")
    print("-----------------------------")
    print(f"Subtotal: USD {total_price_usd:.2f}")
    print(f"Tax ({state}): USD {tax_amount:.2f}")
    print(f"Total: USD {final_price_usd:.2f}")

    available_currencies = list(current_rates.keys())
    selected_currency = input("Select currency for payment ({}): ".format(', '.join(available_currencies)))

    final_price_currency = final_price_usd * current_rates.get(selected_currency)

    print(f"Total in {selected_currency}: {final_price_currency:.2f}")

if __name__ == "__main__":
    main()
