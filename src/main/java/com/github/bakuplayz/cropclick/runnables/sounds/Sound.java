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

package com.github.bakuplayz.cropclick.runnables.sounds;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a Sound in Bukkit.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class Sound {

    /**
     * A variable measured in milliseconds.
     */
    public static final int MIN_DELAY = 0;

    /**
     * A variable measured in milliseconds.
     */
    public static final int MAX_DELAY = 5000;

    /**
     * A variable measured in range-of-blocks.
     */
    public static final int MIN_VOLUME = 0;

    /**
     * A variable measured in range-of-blocks.
     */
    public static final int MAX_VOLUME = 500;

    public static final int MIN_PITCH = 0;
    public static final int MAX_PITCH = 2;


    private final @Getter String name;

    /**
     * The given delay before playing the sound ({@link #MIN_DELAY min delay} & {@link #MAX_DELAY max delay}).
     */
    private final @Getter double delay;

    /**
     * The given pitch the sound should be played at ({@link #MIN_PITCH min pitch} & {@link #MAX_PITCH max pitch}).
     */
    private final @Getter float pitch;

    /**
     * The given range-in-blocks or volume the sound should be played at ({@link #MIN_VOLUME min volume} & {@link #MAX_VOLUME max volume}).
     */
    private final @Getter float volume;


    public Sound(@NotNull String name, double pitch, double volume, double delay) {
        this.volume = (float) volume;
        this.pitch = (float) pitch;
        this.delay = delay;
        this.name = name;
    }

}