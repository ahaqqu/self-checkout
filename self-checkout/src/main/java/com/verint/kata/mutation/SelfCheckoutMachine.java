package com.verint.kata.mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.verint.kata.mutation.store.GiftCard;
import com.verint.kata.mutation.store.GiftCard.GIFT_CARD_STATE;
import com.verint.kata.mutation.store.Product;
import com.verint.kata.mutation.util.ICreditCardValidator;
import com.verint.kata.mutation.util.StoreBuilder;

public class SelfCheckoutMachine {

    private List<Product> shoppingCart = new ArrayList<Product>();

    private Double currentTotal = 0D;
    private Double cashTotal = 0D;
    private Double change = 0D;
    private boolean checkoutCompleted = false;

    private Map<String, GiftCard> availableGiftCardMap = new HashMap<String, GiftCard>();
    private ICreditCardValidator ccValidator;

    public SelfCheckoutMachine(ICreditCardValidator ccValidator) {
        availableGiftCardMap = StoreBuilder.getGiftCardMap();
        this.ccValidator = ccValidator;
    }

    public Double scanProduct(String id) {
        Product product = StoreBuilder.getProductMap().get(id);
        if (product != null) {
            shoppingCart.add(product);
            Double price = product.getPrice();
            currentTotal += price;
            return price;
        }
        return null;
    }

    public GiftCard.GIFT_CARD_STATE addGiftCard(String code) throws Exception {
        if (isCheckoutCompleted()) {
            throw new Exception("Checkout Completed");
        }

        GiftCard giftCard = availableGiftCardMap.get(code);
        if (giftCard == null) {
            return GIFT_CARD_STATE.INVALID;
        }
        if (giftCard.isExpired()) {
            return GIFT_CARD_STATE.EXPIRED;
        }

        cashTotal += giftCard.getValue();
        if (cashTotal >= currentTotal) {
            checkoutCompleted = true;
        }
        availableGiftCardMap.remove(code);
        return GIFT_CARD_STATE.ACCEPTABLE;
    }

    public void payWithCash(double value) throws Exception {
        if (isCheckoutCompleted()) {
            throw new Exception("Checkout Completed");
        }

        cashTotal += value;
        if (cashTotal >= currentTotal) {
            checkoutCompleted = true;
            change = cashTotal - currentTotal;
        }
    }

    public void payWithCreditCard(String cardNumber) {
        if (ccValidator.validate(cardNumber)) {
            checkoutCompleted = true;
        }
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public Double getCurrentTotal() {
        return currentTotal;
    }

    public Double getCashTotal() {
        return cashTotal;
    }

    public Double getChange() {
        return change;
    }

    public boolean isCheckoutCompleted() {
        return checkoutCompleted;
    }

}
