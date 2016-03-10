package com.verint.kata.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.verint.kata.mutation.store.GiftCard.GIFT_CARD_STATE;
import com.verint.kata.mutation.util.ICreditCardValidator;

public class SelfCheckoutMachineTest {

    private SelfCheckoutMachine machine;
    private ICreditCardValidator ccValidator;

    @Before
    public void setUp() {
        ccValidator = mock(ICreditCardValidator.class);
        machine = new SelfCheckoutMachine(ccValidator);
    }

    @Test
    public void testScanProductWithValidProductIDReturnPrice() {
        Double price = machine.scanProduct("ABC0001");
        assertDoubleEquals(5000, price.doubleValue());
    }

    @Test
    public void testScanProductWithInvalidProductIDReturnNull() {
        Double price = machine.scanProduct("XYZ");
        assertNull(price);
    }

    /*
     * Test Update Cart and Total Purchase
     */
    @Test
    public void testScanValidProductWillUpdateShoppingCartAndTotalPurchase() {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        assertEquals(2, machine.getShoppingCart().size());
        assertDoubleEquals(11000, machine.getCurrentTotal());
    }

    @Test
    public void testScanInvalidProductWillNotUpdateShoppingCartAndTotalPurchase() {
        machine.scanProduct("ABC0001");
        machine.scanProduct("XYZ");

        assertEquals(1, machine.getShoppingCart().size());
        assertDoubleEquals(5000, machine.getCurrentTotal());
    }

    /*
     * Test Add and validate gift card
     */
    @Test
    public void testAddValidGiftCard() throws Exception {
        GIFT_CARD_STATE state = machine.addGiftCard("987ZYX");

        assertEquals(GIFT_CARD_STATE.ACCEPTABLE, state);
        assertDoubleEquals(10000, machine.getCashTotal());
    }

    @Test
    public void testAddInvalidGiftCard() throws Exception {
        GIFT_CARD_STATE state = machine.addGiftCard("XYZ");

        assertEquals(GIFT_CARD_STATE.INVALID, state);
        assertDoubleEquals(0, machine.getCashTotal());
    }

    @Test
    public void testAddExpiredGiftCard() throws Exception {
        GIFT_CARD_STATE state = machine.addGiftCard("987BKD");

        assertEquals(GIFT_CARD_STATE.EXPIRED, state);
        assertDoubleEquals(0, machine.getCashTotal());
    }

    /*
     * Test the effects of using gift cards to total purchase
     */
    @Test
    public void testAddValidGiftCardExceedTotalPrice() throws Exception {
        machine.scanProduct("ABC0001");

        GIFT_CARD_STATE state = machine.addGiftCard("987ZYX");

        assertEquals(GIFT_CARD_STATE.ACCEPTABLE, state);
        assertDoubleEquals(5000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test(expected = Exception.class)
    public void testAddMultipleGiftCardsExceedTotalPrice() throws Exception {
        machine.scanProduct("ABC0001");

        GIFT_CARD_STATE state = machine.addGiftCard("987ZYX");
        machine.addGiftCard("987PIS");

        assertEquals(GIFT_CARD_STATE.ACCEPTABLE, state);
        assertDoubleEquals(5000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testAddValidGiftCardBelowTotalPrice() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");

        GIFT_CARD_STATE state = machine.addGiftCard("987ZYX");

        assertEquals(GIFT_CARD_STATE.ACCEPTABLE, state);
        assertDoubleEquals(15000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertFalse(machine.isCheckoutCompleted());
    }

    @Test
    public void testAddValidMultpleGiftCards() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");

        machine.addGiftCard("987ZYX");
        machine.addGiftCard("987PIS");

        assertDoubleEquals(20000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertFalse(machine.isCheckoutCompleted());
    }

    @Test
    public void testAddValidSameGiftCards() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");

        machine.addGiftCard("987ZYX");
        GIFT_CARD_STATE state = machine.addGiftCard("987ZYX");

        assertEquals(GIFT_CARD_STATE.INVALID, state);
        assertDoubleEquals(20000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertFalse(machine.isCheckoutCompleted());
    }

    /*
     * Test paying with cash, and receive change if necessary
     */
    @Test
    public void testPayWithExactSingleCash() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        machine.payWithCash(11000);

        assertDoubleEquals(11000, machine.getCurrentTotal());
        assertDoubleEquals(11000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testPayWithExactMultipleCash() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        machine.payWithCash(1000);
        machine.payWithCash(10000);

        assertDoubleEquals(11000, machine.getCurrentTotal());
        assertDoubleEquals(11000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test(expected = Exception.class)
    public void testPayWithMultipleCashExceedTotal() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        machine.payWithCash(1000);
        machine.payWithCash(10000);
        machine.payWithCash(10000);

        assertDoubleEquals(11000, machine.getCurrentTotal());
        assertDoubleEquals(11000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testPayWithCashAndReturnChange() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        machine.payWithCash(5000);
        machine.payWithCash(10000);

        assertDoubleEquals(11000, machine.getCurrentTotal());
        assertDoubleEquals(15000, machine.getCashTotal());
        assertDoubleEquals(4000, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testPayWithCashNotCompleted() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0002");

        machine.payWithCash(5000);

        assertDoubleEquals(11000, machine.getCurrentTotal());
        assertDoubleEquals(5000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertFalse(machine.isCheckoutCompleted());
    }

    @Test
    public void testAddValidMultpleGiftCardsAndCash() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");

        machine.addGiftCard("987ZYX");
        machine.payWithCash(10000);

        assertDoubleEquals(15000, machine.getCurrentTotal());
        assertDoubleEquals(20000, machine.getCashTotal());
        assertDoubleEquals(5000, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testAddValidMultpleGiftCardsAndCashButNotCompleted() throws Exception {
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");
        machine.scanProduct("ABC0001");

        machine.addGiftCard("987ZYX");
        machine.payWithCash(10000);

        assertDoubleEquals(25000, machine.getCurrentTotal());
        assertDoubleEquals(20000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertFalse(machine.isCheckoutCompleted());
    }

    /*
     * Test paying with credit card
     */
    @Test
    public void testPayWithCreditCardAccepted() {
        String creditNumber = "123";
        machine.scanProduct("ABC0001");

        when(ccValidator.validate(creditNumber)).thenReturn(true);
        machine.payWithCreditCard(creditNumber);

        assertTrue(machine.isCheckoutCompleted());
    }

    @Test
    public void testPayWithCreditCardNotAcceptedThenUseGiftCard() throws Exception {
        String creditNumber = "123";
        machine.scanProduct("ABC0001");

        when(ccValidator.validate(creditNumber)).thenReturn(false);
        machine.payWithCreditCard(creditNumber);

        assertFalse(machine.isCheckoutCompleted());

        machine.addGiftCard("987ZYX");

        assertDoubleEquals(5000, machine.getCurrentTotal());
        assertDoubleEquals(10000, machine.getCashTotal());
        assertDoubleEquals(0, machine.getChange());
        assertTrue(machine.isCheckoutCompleted());
    }

    private static void assertDoubleEquals(double d1, double d2) {
        assertEquals(d1, d2, 0.001);
    }

    // @Test
    // public void testAddValidMultpleGiftCardsAndCashThenPrintReceipt() {
    // machine.scanProduct("ABC0001");
    // machine.scanProduct("ABC0001");
    // machine.scanProduct("ABC0001");
    // machine.scanProduct("ABC0001");
    // machine.scanProduct("ABC0002");
    //
    // machine.addGiftCard("987ZYX");
    // machine.addGiftCard("987PIS");
    // machine.payWithCash(10000);
    //
    // assertDoubleEquals(26000, machine.getCurrentTotal());
    // assertDoubleEquals(30000, machine.getCashTotal());
    // assertDoubleEquals(4000, machine.getChange());
    // assertTrue(machine.isCheckoutCompleted());
    //
    // machine.printReceipt();
    // String expectedReceipt = "ABC0001 | 4 | 5000 | 20000\n" +
    // "ABC0002 | 1 | 6000 | 6000\n" +
    // "Total: 16000\n" +
    // "\n" +
    // "Gift Cards:\n" +
    // "987ZYX | 10000\n" +
    // "987PIS | 10000\n" +
    // "Cash: 10000\n" +
    // "Total payment: 30000\n" +
    // "Changes: 4000";
    // }
}
