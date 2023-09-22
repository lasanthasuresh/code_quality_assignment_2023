# EG_2020_4040
import csv

discounts = {}
state_tax_rates = {}
promotions = {}
currency_rates = {}
inventory = {}

def load_discounts(file):
    with open(file) as discount_file:
        all_rows = csv.reader(discount_file)
        for one_row in all_rows:
            #We get first value of the row as the key and second value as the key
            discounts[one_row[0].upper()] = float(one_row[1])

def load_taxes(file):
    with open(file) as tax_file:
        all_rows = csv.reader(tax_file)
        for one_row in all_rows:
            #We get first value of the row as the key and second value as the key
            state_tax_rates[one_row[0].upper()] = float(one_row[1])

def load_promotions(file):
    with open(file) as promotion_file:
        all_rows = csv.reader(promotion_file)
        for one_row in all_rows:
            #We get first value of the row as the key and second value as the key
            promotions[one_row[0]] = float(one_row[1])

def load_currency_rates(file):
    with open(file) as currency_file:
        all_rows = csv.reader(currency_file)
        for one_row in all_rows:
            #We get first value of the row as the key and second value as the key
            currency_rates[one_row[0].upper()] = float(one_row[1])

def load_inventory(file):
    with open(file) as inventory_file:
        all_rows = csv.reader(inventory_file)
        for one_row in all_rows:
            #We get first value of the row as the key and second value as the key
            inventory[one_row[0].upper()] = int(one_row[1])


def main():

    load_discounts('data/discounts.csv')
    load_taxes('data/tax_rates.csv')
    load_promotions('data/promotions.csv')
    load_currency_rates('data/currency_rates.csv')
    load_inventory('data/inventory.csv')

    with open('shopping_cart.csv') as file:
        shopping_cart = {row[0]: int(row[1]) for row in csv.reader(file)}

    total_price_usd = 0

    for cart_item, item_quantity in shopping_cart.items():

        cart_item = cart_item.upper()

        if inventory.get(cart_item, 0) < item_quantity:
            print(f"Cannot proceed, insufficient inventory for {cart_item}")
            return

        promo = promotions.get(cart_item)
        if promo:
            item_quantity = item_quantity - (item_quantity // promo)

        discount = discounts.get(cart_item, 0)
        price = 1.0  
        discounted_price = price - (price * discount)
        item_price = discounted_price * item_quantity
        total_price_usd += item_price
        inventory[cart_item] -= item_quantity 

    #Ask user for states    
    available_states = list(state_tax_rates.keys())
    print("Enter your state. Available states are: ", ', '.join(available_states))
    input_state = input()


    tax_rate = state_tax_rates.get(input_state, 0)
    tax_amount = total_price_usd * tax_rate
    final_price_usd = total_price_usd + tax_amount

    print("-------- SHOPPING CART SUMMARY --------")
    print("Item   | Qty | Price")
    print("-----------------------------")

    #Show the bill summmary with items.
    for cart_item, item_quantity in shopping_cart.items():
        print(f"{cart_item.ljust(6)} | {str(item_quantity).ljust(3)} | USD {discounted_price * item_quantity:.2f}")
    
    #Show bill summary in usd
    print("-----------------------------")
    print(f"Subtotal: USD {total_price_usd:.2f}")
    print(f"Tax ({input_state}): USD {tax_amount:.2f}")
    print(f"Total: USD {final_price_usd:.2f}")

    #Handle currency inputs
    available_currencies = list(currency_rates.keys())
    selected_currency = input("Select currency for payment ({}): ".format(', '.join(available_currencies)))
    final_price_currency = final_price_usd * currency_rates.get(selected_currency)

    print(f"Total in {selected_currency}: {final_price_currency:.2f}")

if __name__ == "__main__":
    main()
