package com.verint.kata.mutation.store;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    private Product product;

    @Before
    public void setup() {
        product = new Product("id", "name", 10000D);
    }

    @Test
    public void testProductId() {
        assertEquals("id", product.getId());
    }

    @Test
    public void testProductName() {
        assertEquals("name", product.getName());
    }

    @Test
    public void testProductPrice() {
        assertEquals(10000D, product.getPrice(), 0.001);
    }
    //Experiment branch
}
