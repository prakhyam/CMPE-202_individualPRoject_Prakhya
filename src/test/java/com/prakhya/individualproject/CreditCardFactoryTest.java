
package com.prakhya.individualproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardFactoryTest {

    @Test
    void testCreateVisaCard() {
        CreditCard card = CreditCardFactory.createCreditCard("4123456789012", "12/23", "John Doe");
        assertTrue(card instanceof VisaCC);
        assertTrue(card.isValid());
    }

    @Test
    void testCreateMasterCard() {
        CreditCard card = CreditCardFactory.createCreditCard("5123456789012345", "01/24", "Jane Smith");
        assertTrue(card instanceof MasterCC);
        assertTrue(card.isValid());
    }

    @Test
    void testCreateAmExCard() {
        CreditCard card = CreditCardFactory.createCreditCard("341234567890123", "11/22", "Alice Johnson");
        assertTrue(card instanceof AmExCC);
        assertTrue(card.isValid());
    }

    @Test
    void testCreateDiscoverCard() {
        CreditCard card = CreditCardFactory.createCreditCard("6011123456789012", "03/25", "Bob Brown");
        assertTrue(card instanceof DiscoverCC);
        assertTrue(card.isValid());
    }

    @Test
    void testCreateInvalidCard() {
        assertThrows(IllegalArgumentException.class, () -> {
            CreditCardFactory.createCreditCard("0000000000000000", "07/23", "Invalid User");
        });
    }
}
