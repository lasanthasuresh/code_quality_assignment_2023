import csv

discounts = {}
taxes = {}
promotions = {}
currency_rates = {}
inventory = {}

def load_discounts(file_path):
    with open(file_path) as discounts_file:
        reader = csv.reader(discounts_file)
        for row in reader:
            discounts[row[0].upper()] = float(row[1])

def load_taxes(file_path):
    with open(file_path) as taxes_file:
        reader = csv.reader(taxes_file)
        for row in reader:
            taxes[row[0].upper()] = float(row[1])

def load_promotions(file_path):
    with open(file_path) as promotion_file:
        reader = csv.reader(promotion_file)
        for row in reader:
            promotions[row[0]] = float(row[1])

def load_currency_rates(file_path):
    with open(file_path) as currency_file:
        reader = csv.reader(currency_file)
        for row in reader:
            currency_rates[row[0].upper()] = float(row[1])

def load_inventory(file):
    with open(file) as inventory_file:
        reader = csv.reader(inventory_file)
        for row in reader:
            inventory[row[0].upper()] = int(row[1])

def main():

   load_discounts('data/discounts.csv')
    load_taxes('data/tax_rates.csv')
    load_promotions('data/promotions.csv')
    load_currency_rates('data/currency_rates.csv')
    load_inventory('data/inventory.csv')

    with open('shopping_cart.csv') as file:
    cart = {row[0]: int(row[1]) for row in csv.reader(file)}

    total_price_usd = 0

    for item, quantity in cart.items():

    item = item.upper()

    if inventory.get(item, 0) < quantity:
        print(f"Cannot proceed, insufficient inventory for {item}")
        return

    promotion = promotions.get(item)
    if promotion:
        quantity = quantity - (quantity // promotion)
    discount = discounts.get(item, 0)
    price = 1.0  
    discounted_price = price - (price * discount)
    item_price = discounted_price * quantity
    total_price_usd += item_price
    inventory[item] -= quantity

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

   available_currencies = list(currency_rates.keys())
selected_currency = input("Select currency for payment ({}): ".format(', '.join(available_currencies)))

final_price_currency = final_price_usd * currency_rates.get(selected_currency)

print(f"Total in {selected_currency}: {final_price_currency:.2f}")

if __name__ == "__main__":
    main()