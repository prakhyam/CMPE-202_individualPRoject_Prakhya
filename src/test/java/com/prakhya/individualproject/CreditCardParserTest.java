package com.prakhya.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardProcessorTest {

    CreditCardProcessor processor = new CreditCardProcessor();

    @Test
    void testProcessValidVisaCard() {
        String cardDetails = "4123456789012,12/23,John Doe";
        CreditCard card = processor.processCardFromString(cardDetails);
        assertTrue(card instanceof VisaCC);
        assertTrue(card.isValid());
    }

    @Test
    void testProcessValidMasterCard() {
        String cardDetails = "5123456789012345,01/24,Jane Smith";
        CreditCard card = processor.processCardFromString(cardDetails);
        assertTrue(card instanceof MasterCC);
        assertTrue(card.isValid());
    }

    @Test
    void testProcessValidAmExCard() {
        String cardDetails = "341234567890123,11/22,Alice Johnson";
        CreditCard card = processor.processCardFromString(cardDetails);
        assertTrue(card instanceof AmExCC);
        assertTrue(card.isValid());
    }

    @Test
    void testProcessValidDiscoverCard() {
        String cardDetails = "6011123456789012,03/25,Bob Brown";
        CreditCard card = processor.processCardFromString(cardDetails);
        assertTrue(card instanceof DiscoverCC);
        assertTrue(card.isValid());
    }

//    @Test
//    void testProcessInvalidCard() {
//        String cardDetails = ",07/23,Invalid User";
//        CreditCard card = processor.processCardFromString(cardDetails);
//        assertNull(card, "Expected null for an invalid card number");
//    }
}

