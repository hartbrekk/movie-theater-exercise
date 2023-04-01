package com.tj.theater.util;

public enum DiscountCode {

    MORNING_SHOW(0.25D),
    SPECIAL_SHOW(0.2d),
    FIRST_SHOW(3d),
    SECOND_SHOW(2d),
    DAY7_SHOW(1);

    public final double value;

    DiscountCode(double value) {
        this.value = value;
    }

}
