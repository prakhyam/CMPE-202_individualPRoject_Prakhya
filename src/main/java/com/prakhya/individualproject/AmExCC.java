package com.prakhya.individualproject;

public class AmExCC extends CreditCard {
    public AmExCC(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        return cardNumber.startsWith("3") && (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7') && cardNumber.length() == 15;
    }
    @Override
    public String getCardType() {
        return "Amex";
    }
}