package com.verint.kata.mutation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date createDate(String dateInString) {
        Date date = null;
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return date;
    }

    @SuppressWarnings("deprecation")
    public static int compareDate(Date date1, Date date2) {
        int day1 = date1.getDay();
        int month1 = date1.getMonth();
        int year1 = date1.getYear();
        int day2 = date2.getDay();
        int month2 = date2.getMonth();
        int year2 = date2.getYear();

        Date temp1 = new Date(year1, month1, day1);
        Date temp2 = new Date(year2, month2, day2);

        if (temp1.getTime() == temp2.getTime()) {
            return 0;
        } else if (temp1.getTime() > temp2.getTime()) {
            return 1;
        } else {
            return -1;
        }

    }
}
