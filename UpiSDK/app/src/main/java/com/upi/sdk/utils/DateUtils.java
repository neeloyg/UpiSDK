package com.upi.sdk.utils;

import java.util.Date;

/**
 * Created by SwapanP on 31-05-2016.
 */
public class DateUtils {

    /**
     * Determines the difference between two dates
     * @param date1
     * @param date2
     * @return difference in days in long
     */
    public static long getDifferenceInDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Both dates are mandatory");
        }
        long diff = Math.abs(date1.getTime() - date2.getTime());
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }
}
