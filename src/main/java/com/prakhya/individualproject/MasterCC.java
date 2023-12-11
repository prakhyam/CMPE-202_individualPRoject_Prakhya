package com.prakhya.individualproject;


public class MasterCC extends CreditCard {
    public MasterCC(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        return cardNumber.startsWith("5") && cardNumber.length() == 16;
    }

    @Override
    public String getCardType() {
        return "Master";
    }
}