package com.verint.kata.mutation.store;

import java.util.Calendar;
import java.util.Date;

import com.verint.kata.mutation.util.DateUtil;

public class GiftCard {

    private String code;
    private double value;
    private Date expirationDate;

    public enum GIFT_CARD_STATE {
        ACCEPTABLE, EXPIRED, INVALID
    }

    public GiftCard(String code, double value, Date expirationDate) {
        this.code = code;
        this.value = value;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public double getValue() {
        return value;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired() {
        return DateUtil.compareDate(Calendar.getInstance().getTime(), expirationDate) == 1;
    }
}
