package com.ocado.basket.utils;

import java.math.BigDecimal;

public class Utils {

    private Utils() {}

    public static BigDecimal max(BigDecimal b1, BigDecimal b2) {
        return b1.compareTo(b2) > 0 ? b1 : b2;
    }
}
