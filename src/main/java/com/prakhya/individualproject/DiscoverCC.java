package com.prakhya.individualproject;

public class DiscoverCC extends CreditCard{
    public DiscoverCC(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        return cardNumber.startsWith("6011") && cardNumber.length() == 16;
    }   
    @Override
    public String getCardType() {
        return "Discover";
    }
    
}
