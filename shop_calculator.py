import csv;

discnts = {}
taxes = {}
prmtns = {}
curr_rates = {}
invntry = {}

def ld_discnts(file):
    with open(file) as dscnt_file:
        r = csv.reader(dscnt_file)
        for rw in r:
            discnts[rw[0].upper()] = float(rw[1])

def ld_taxes(file):
    with open(file) as tax_file:
        r = csv.reader(tax_file)
        for rw in r:
            taxes[rw[0].upper()] = float(rw[1])

def ld_prmtns(file):
    with open(file) as prmtn_file:
        r = csv.reader(prmtn_file)
        for rw in r:
            prmtns[rw[0]] = float(rw[1])

def ld_curr_rates(file):
    with open(file) as currency_file:
        r = csv.reader(currency_file)
        for rw in r:
            curr_rates[rw[0].upper()] = float(rw[1])

def ld_invntry(file):
    with open(file) as inv_file:
        r = csv.reader(inv_file)
        for rw in r:
            invntry[rw[0].upper()] = int(rw[1])

def main():

    ld_discnts('data/discounts.csv')
    ld_taxes('data/tax_rates.csv')
    ld_prmtns('data/promotions.csv')
    ld_curr_rates('data/currency_rates.csv')
    ld_invntry('data/inventory.csv')

    with open('shopping_cart.csv') as file:
        cart = {rw[0]: int(rw[1]) for rw in csv.reader(file)}

    total_price_usd = 0

    for itm, qty in cart.items():

        itm = itm.upper()

        if invntry.get(itm, 0) < qty:
            print(f"Cannot proceed, insufficient inventory for {itm}")
            return

        promo = prmtns.get(itm)
        if promo:
            qty = qty - (qty // promo)
        dscnt = discnts.get(itm, 0)
        price = 1.0  
        dscntd_price = price - (price * dscnt)
        item_price = dscntd_price * qty
        total_price_usd += item_price
        invntry[itm] -= qty 

    available_states = list(taxes.keys())
    print("Enter your state. Available states are: ", ', '.join(available_states))
    state = input()

    tax_rate = taxes.get(state, 0)
    tax_amt = total_price_usd * tax_rate

    final_price_usd = total_price_usd + tax_amt

    print("-------- SHOPPING CART SUMMARY --------")
    print("Item   | Qty | Price")
    print("-----------------------------")
    for itm, qty in cart.items():
        print(f"{itm.ljust(6)} | {str(qty).ljust(3)} | USD {dscntd_price * qty:.2f}")
    print("-----------------------------")
    print(f"Subtotal: USD {total_price_usd:.2f}")
    print(f"Tax ({state}): USD {tax_amt:.2f}")
    print(f"Total: USD {final_price_usd:.2f}")

    available_currencies = list(curr_rates.keys())
    selected_currency = input("Select currency for payment ({}): ".format(', '.join(available_currencies)))

    final_price_currency = final_price_usd * curr_rates.get(selected_currency)

    print(f"Total in {selected_currency}: {final_price_currency:.2f}")

if __name__ == "__main__":
    main()
