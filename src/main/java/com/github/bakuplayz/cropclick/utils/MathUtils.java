package com.github.bakuplayz.cropclick.utils;

import java.text.DecimalFormat;


/**
 * A utility class for mathematics.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MathUtils {


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


    /**
     * If x is less than min, return min. If x is greater than max, return max. Otherwise, return x.
     *
     * @param x   The value to clamp
     * @param min The minimum value that the returned value can be.
     * @param max The maximum value that the returned value can be.
     *
     * @return The minimum of the max and the maximum of the min and x.
     */
    public static int clamp(int x, int min, int max) {
        return Math.min(max, Math.max(min, x));
    }

}