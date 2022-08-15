package com.github.bakuplayz.cropclick.utils;

import java.text.DecimalFormat;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MathUtil {


    /**
     * It takes a double, formats it to two decimal places, and returns the formatted double.
     *
     * @param number The number to be rounded.
     *
     * @return A double
     */
    public static double round(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        return Double.parseDouble(
                decimalFormat.format(number)
        );
    }

}