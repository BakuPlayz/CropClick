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