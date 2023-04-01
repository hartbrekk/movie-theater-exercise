package com.tj.theater.util;

public class NumberUtil {
    public static double roundToTwoDecimals(double value) {
        return (Math.round(value * 100)) / 100d;
    }
}
