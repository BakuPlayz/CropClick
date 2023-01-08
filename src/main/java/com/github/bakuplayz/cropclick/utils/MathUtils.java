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
     * The format for formatting numbers to two decimal points.
     */
    private final static DecimalFormat NUMBER_FORMAT = new DecimalFormat("###.##");


    /**
     * Rounds the passed number to two decimals places.
     *
     * @param number the number to round.
     *
     * @return the rounded number.
     */
    public static double round(double number) {
        return Double.parseDouble(NUMBER_FORMAT.format(number));
    }


    /**
     * Clamps the provided number to the min or max value.
     *
     * @param number the number to clamp.
     * @param min    the minimum value to clamp to.
     * @param max    the maximum value to clamp to.
     *
     * @return the clamped number.
     */
    public static int clamp(int number, int min, int max) {
        return Math.min(max, Math.max(min, number));
    }

}