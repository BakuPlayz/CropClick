package com.github.bakuplayz.cropclick.runnables.sounds;

import com.github.bakuplayz.cropclick.menu.menus.sounds.SoundMenu;
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

    private final @Getter String name;


    /**
     * The given delay before playing the sound ({@link SoundMenu#MIN_DELAY min delay} & {@link SoundMenu#MAX_DELAY max delay}).
     */
    private final @Getter double delay;

    /**
     * The given pitch the sound should be played at ({@link SoundMenu#MIN_PITCH min pitch} & {@link SoundMenu#MAX_PITCH max pitch}).
     */
    private final @Getter float pitch;

    /**
     * The given range-in-blocks or volume the sound should be played at ({@link SoundMenu#MIN_VOLUME min volume} & {@link SoundMenu#MAX_VOLUME max volume}).
     */
    private final @Getter float volume;


    public Sound(@NotNull String name, double pitch, double volume, double delay) {
        this.volume = (float) volume;
        this.pitch = (float) pitch;
        this.delay = delay;
        this.name = name;
    }

}