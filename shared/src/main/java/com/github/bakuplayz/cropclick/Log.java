/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log {

    private final static boolean DEBUG = false;

    private final static Logger logger = Logger.getLogger("CropClick");


    public static void debug(@NotNull String message, @NotNull Throwable throwable) {
        if (DEBUG) {
            logger.log(Level.INFO, message, throwable);
        }
    }


    public static void debug(@NotNull String message, @NotNull Object... params) {
        if (DEBUG) {
            logger.log(Level.INFO, message, params);
        }
    }


    public static void severe(@NotNull String message) {
        logger.severe(message);
    }


    public static void info(@NotNull String message, @NotNull Throwable throwable) {
        logger.log(Level.INFO, message, throwable);
    }


    public static void info(@NotNull String message, @NotNull Object... params) {
        logger.log(Level.INFO, message, params);
    }

}
