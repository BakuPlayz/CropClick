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

package com.github.bakuplayz.cropclick.runnables;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;


/**
 * An interface designed to handle a Runnable.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Runnable {

    /**
     * Executes the implementing object's runnable when called.
     */
    void run();

    /**
     * Clears the implementing object's runnable {@link Timer timer} when called.
     *
     * @return a cleaning task.
     */
    @NotNull
    TimerTask clean();

}