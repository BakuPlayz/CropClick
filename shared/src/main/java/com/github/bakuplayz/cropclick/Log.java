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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class for logging messages tagged with "CropClick" at various levels: info, severe, and debug.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class Log {

    private final static boolean DEBUG = true;

    private final static Logger LOGGER = Logger.getLogger("CropClick");


    static {
        try {
            File LOG_FILE = new File(
                    CropClick.getInstance().getDataFolder().getAbsolutePath() + "/logs/cropclick.log"
            );
            Files.createDirectories(Paths.get(LOG_FILE.getParentFile().getPath()));
            LOGGER.addHandler(new FileHandler(LOG_FILE.getAbsolutePath(), true));
        } catch (IOException e) {
            severe("(Setup): Failed to setup logger handler.");
        }
    }

    /**
     * Logs a debug message and throwable, if debug mode is enabled.
     *
     * @param message   The message to log
     * @param throwable The throwable to log
     */
    public static void debug(@NotNull String message, @NotNull Throwable throwable) {
        if (DEBUG) info(message, throwable);
    }


    /**
     * Logs a formatted debug message with parameters, if debug mode is enabled.
     *
     * @param message The message to log
     * @param params  Parameters to insert into the message
     */
    public static void debug(@NotNull String message, @NotNull Object... params) {
        if (DEBUG) info(message, params);
    }


    public static void debug(@NotNull String message, @NotNull Tag tag, @NotNull Object... params) {
        if (DEBUG) info(message, tag, params);
    }


    /**
     * Logs a severe level message with parameters.
     *
     * @param message The message to log
     * @param params  Parameters to insert into the message
     */
    public static void severe(@NotNull String message, @NotNull Object... params) {
        LOGGER.log(Level.SEVERE, "[CropClick] " + message, params);
    }


    /**
     * Logs a severe level message with a throwable.
     *
     * @param message   The message to log
     * @param throwable The throwable to log
     */
    public static void severe(@NotNull String message, @NotNull Throwable throwable) {
        LOGGER.log(Level.SEVERE, "[CropClick] " + message, throwable);
    }


    /**
     * Logs an info level message with a throwable.
     *
     * @param message   The message to log
     * @param throwable The throwable to log
     */
    public static void info(@NotNull String message, @NotNull Throwable throwable) {
        LOGGER.log(Level.INFO, "[CropClick] $message", throwable);
    }


    /**
     * Logs an info level message with parameters.
     *
     * @param message The message to log
     * @param params  Parameters to insert into the message
     */
    public static void info(@NotNull String message, @NotNull Object... params) {
        LOGGER.log(Level.INFO, "[CropClick] " + message, params);
    }


    public static void info(@NotNull String message, @NotNull Tag tag, @NotNull Object... params) {
        LOGGER.log(Level.INFO, "[CropClick] (" + tag.name() + ") " + message, params);
    }


    public enum Tag {

        ENTITY,

        AUTOFARM,

        CROP,

        PLAYER,

    }

}
