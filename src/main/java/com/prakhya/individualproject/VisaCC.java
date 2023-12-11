package com.prakhya.individualproject;

public class VisaCC extends CreditCard {
    public VisaCC(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        return cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16);
    }
    @Override
    public String getCardType() {
        return "Visa";
    }
}

