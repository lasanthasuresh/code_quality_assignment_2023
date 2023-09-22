import csv

# SHESHAN K.H.N -> EG/2020/4330

discounts = {}
taxes = {}
promotions = {}
current_Rates = {}
inventory = {}

def ld_Discounts(file):
    with open(file) as discount_file:
        r = csv.reader(discount_file)
        for row in r:
            discounts[row[0].upper()] = float(rw[1])

def ld_Taxes(file):
    with open(file) as tax_file:
        r = csv.reader(tax_file)
        for row in r:
            taxes[row[0].upper()] = float(row[1])

def ld_Promotions(file):
    with open(file) as prmtn_file:
        r = csv.reader(prmtn_file)
        for row in r:
            promotions[row[0]] = float(row[1])

def ld_Current_Rates(file):
    with open(file) as currency_file:
        r = csv.reader(currency_file)
        for rw in r:
            current_Rates[rw[0].upper()] = float(rw[1])

def ld_Inventory(file):
    with open(file) as inv_file:
        r = csv.reader(inv_file)
        for row in r:
            inventory[row[0].upper()] = int(row[1])

def main():

    ld_Discounts('data/discounts.csv')
    ld_Taxes('data/tax_rates.csv')
    ld_Promotions('data/promotions.csv')
    ld_Current_Rates('data/currency_rates.csv')
    ld_Inventory('data/inventory.csv')

    with open('shopping_cart.csv') as file:
        cart = {row[0]: int(row[1]) for row in csv.reader(file)}

    total_Price_USD = 0

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
        total_Price_USD += item_price
        inventory[item] -= quantity 

    available_States = list(taxes.keys())
    print("Enter your state. Available states are: ", ', '.join(available_states))
    state = input()

    tax_Rate = taxes.get(state, 0)
    tax_Amount = total_Price_USD * tax_Rate

    final_Price_USD=   total_Price_USD + tax_Amount

    print("-------- SHOPPING CART SUMMARY --------")
    print("Item   | Quantity | Price")
    print("-----------------------------")
    for item, quantity in cart.items():
        print(f"{item.ljust(6)} | {str(quantity).ljust(3)} | USD {discounted_price * quantity:.2f}")
    print("-----------------------------")
    print(f"Subtotal: USD {total_Price_USD:.2f}")
    print(f"Tax ({state}): USD {tax_Amount:.2f}")
    print(f"Total: USD {final_price_USD:.2f}")

    available_Currencies = list(current_Rates.keys())
    selected_Currency = input("Select currency for payment ({}): ".format(', '.join(available_Currencies)))

    final_Price_Currency = final_Price_USD * current_Rates.get(selected_Currency)

    print(f"Total in {selected_Currency}: {final_Price_Currency:.2f}")

if __name__ == "__main__":
    main()
