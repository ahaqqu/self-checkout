package com.verint.kata.mutation.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class GiftCardTest {

    private GiftCard giftCard;

    @Before
    public void setup() {
        giftCard = new GiftCard("code", 10000D, new Date(1000));
    }

    @Test
    public void testGiftCardId() {
        assertEquals("code", giftCard.getCode());
    }

    @Test
    public void testGiftCardPrice() {
        assertEquals(10000D, giftCard.getValue(), 0.001);
    }

    @Test
    public void testGiftCardExpirationDate() {
        assertEquals(new Date(1000), giftCard.getExpirationDate());
    }

    @Test
    public void testGiftCardIsExpired() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);

        GiftCard card = new GiftCard(null, 0, c.getTime());
        assertTrue(card.isExpired());
    }

    @Test
    public void testGiftCardTodayIsNotExpired() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        GiftCard card = new GiftCard(null, 0, c.getTime());
        assertFalse(card.isExpired());
    }
}
