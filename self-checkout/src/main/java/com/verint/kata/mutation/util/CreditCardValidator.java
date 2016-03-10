package com.verint.kata.mutation.util;

public class CreditCardValidator implements ICreditCardValidator {

    private static ICreditCardValidator instance;

    // Just a sample of ICreditCardValidator.validate() implementation
    public Boolean validate(String cardNumber) {
        return false;
    }

    public static ICreditCardValidator getInstance() {
        if (instance == null) {
            instance = new CreditCardValidator();
        }
        return instance;
    }

}
