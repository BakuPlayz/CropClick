/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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