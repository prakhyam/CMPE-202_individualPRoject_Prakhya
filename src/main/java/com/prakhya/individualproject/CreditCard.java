package com.prakhya.individualproject;

public abstract class CreditCard {

    protected String cardNumber;
    protected String expirationDate;
    protected String cardHolderName;

    public CreditCard(String cardNumber, String expirationDate, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

        public abstract String getCardType();

    public boolean isValid() {
        return true; // Simplified for this example
    }    


   
}
