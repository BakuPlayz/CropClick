package com.github.bakuplayz.cropclick.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MathUtil {

    public static double round(double number, int numOfDecimals) {
        String rounded = new BigDecimal(number)
                .setScale(numOfDecimals, RoundingMode.HALF_UP)
                .toString();
        return Double.parseDouble(rounded);
    }

}
