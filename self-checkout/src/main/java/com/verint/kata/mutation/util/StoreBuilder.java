package com.verint.kata.mutation.util;

import static com.verint.kata.mutation.util.DateUtil.createDate;

import java.util.HashMap;
import java.util.Map;

import com.verint.kata.mutation.store.GiftCard;
import com.verint.kata.mutation.store.Product;

public class StoreBuilder {

    private static Map<String, Product> productMap = generateProductMap();

    private static Map<String, Product> generateProductMap() {
        Map<String, Product> map = new HashMap<String, Product>();

        map.put("ABC0001", new Product("ABC0001", "Blue Apfax Cola", 5000));
        map.put("ABC0002", new Product("ABC0002", "Superbam Tea", 6000));
        map.put("DEF0001", new Product("DEF0001", "Inchlam Chips", 4500));
        map.put("DEF0002", new Product("DEF0002", "Unatech Crackers", 8000));
        map.put("DEF0003", new Product("DEF0003", "Transnix Nuts", 10000));
        map.put("GHI0001", new Product("GHI0001", "Funtough Soap", 17500));
        map.put("GHI0002", new Product("GHI0002", "Sanair Shampoo", 21000));

        return map;
    }

    public static Map<String, Product> getProductMap() {
        return productMap;
    }

    private static Map<String, GiftCard> generateGiftCardMap() {
        Map<String, GiftCard> map = new HashMap<String, GiftCard>();

        map.put("987ZYX", new GiftCard("987ZYX", 10000, createDate("31-12-2016")));
        map.put("987PIS", new GiftCard("987PIS", 10000, createDate("01-01-2016")));
        map.put("987KHF", new GiftCard("987KHF", 10000, createDate("31-12-2015")));
        map.put("987MKI", new GiftCard("987MKI", 10000, createDate("31-10-2015")));
        map.put("987BKD", new GiftCard("987BKD", 10000, createDate("30-06-2015")));

        return map;
    }

    public static Map<String, GiftCard> getGiftCardMap() {
        return generateGiftCardMap();
    }

}
