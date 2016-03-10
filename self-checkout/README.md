Self Checkout - Mutation Test Kata
==================================

In this exercise, you will build a self checkout machine. It will scan a product, sum the product prices, accept gift card, receive payment by cash and make change, or receive payment by credit card. All the things that you might expect a self checkout machine to be capable of.

Features
========

Scan Product
------------
_As a customer_
_I want a self checkout machine that scan product for its price_
_So that I can do 'self-entry' of the item they want to buy_

The machine will accepting product ID as input, validate it, returns (display) the price. If the ID is not valid, it shall not return its price.


Calculate Total 
---------------
_As a customer_
_I want the self checkout machine automatically add the scanner product to my cart_
_So that I can get the total prices I need to pay at the end of checkout_
 
 A product that had been scanned, and it's valid, will automatically added to the customer 'cart', and the updating the current total. 
 

Add Gift Card(s)
----------------
_As a customer_
_I want to be able to entering my gift card(s) code_
_SO that I can use my gift card(s) as part of payment_

The machine will accept any number of gift card(s). The customer shall be able to entered their gift card code(s). Valid code will deducting the total that should be paid by the customer. However,if the total value of the gift card(s) are more than the total purchases, no change will be given as of store policy.
The machine should also validate the expiration date of the card.
A gift card can only be used once.


Accept Payment by Cash
----------------------
_As a customer_
_I want to be able to pay by using cash_
-So that I can pay by cash_

In this exercise, the cash payment is simulated by letting the customer entering the nominal value of the money they use. The customer can do this multiple times. The machine will end the checkout if total is equal or more than the total purchase. The machine will also give change if there is any.
Checkout should ended afterward.


Accept Payment by Credit Card
-----------------------------
_As a customer_
_I want to be able to pay by using credit card_
-So that I can pay with my credit card_

The customer can entered their credit card number, and the machine will use third party service to validate the card. If valid, checkout should ended afterward.


Gift Card Can Be Used Again If Previous Transaction Is Cancelled
_As a customer_
_I want to cancel use my transaction using gift card_
_So that I can use my gift card for next transaction_

Bonus
=====
- Print receipt
- Add mutation test coverage for the classes in the util package