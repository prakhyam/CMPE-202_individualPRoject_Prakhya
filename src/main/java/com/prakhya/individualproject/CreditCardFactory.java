package com.prakhya.individualproject;

public class CreditCardFactory {
    public static CreditCard createCreditCard(String cardNumber, String expirationDate, String cardHolderName) {
        System.out.println("Creating card for number: " + cardNumber); // Debug 
    
        if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
            return new VisaCC(cardNumber, expirationDate, cardHolderName);
        } else if (cardNumber.startsWith("5") && cardNumber.length() == 16) {
            return new MasterCC(cardNumber, expirationDate, cardHolderName);
        } else if (cardNumber.startsWith("3") && (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7') && cardNumber.length() == 15) {
            return new AmExCC(cardNumber, expirationDate, cardHolderName);
        } else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
            return new DiscoverCC(cardNumber, expirationDate, cardHolderName);
        } 
        
        else {
            System.out.println("Invalid card number: " + cardNumber); // Debug 
            throw new IllegalArgumentException("Invalid credit card number");
        }
    }

    public static CreditCard createCard(String visaNumber) {
        return null;
    }
    
}


