package com.github.bakuplayz.cropclick.sounds;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class Sound {

    private final @Getter String name;

    private final @Getter double delay;
    private final @Getter double pitch;
    private final @Getter double volume;


    public Sound(@NotNull String name, double pitch, double volume, double delay) {
        this.volume = volume;
        this.pitch = pitch;
        this.delay = delay;
        this.name = name;
    }

}