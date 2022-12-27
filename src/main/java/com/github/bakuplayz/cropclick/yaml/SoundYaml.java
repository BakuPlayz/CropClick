package com.github.bakuplayz.cropclick.yaml;

import com.github.bakuplayz.cropclick.menu.menus.sounds.SoundMenu;
import com.github.bakuplayz.cropclick.utils.Enableable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a Sound object as YAML.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class SoundYaml implements Yamlable, Enableable {

    /**
     * The given delay before playing the sound ({@link SoundMenu#MIN_DELAY min delay} & {@link SoundMenu#MAX_DELAY max delay}).
     */
    private @Setter @Getter double delay;

    /**
     * The given pitch the sound should be played at ({@link SoundMenu#MIN_PITCH min pitch} & {@link SoundMenu#MAX_PITCH max pitch}).
     */
    private @Setter @Getter double pitch;

    /**
     * The given range-in-blocks or volume the sound should be played at ({@link SoundMenu#MIN_VOLUME min volume} & {@link SoundMenu#MAX_VOLUME max volume}).
     */
    private @Setter @Getter double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }


    /**
     * It converts {@link SoundYaml this} object to a YAML-styled map.
     *
     * @return A {@link HashMap YAML-formatted HashMap}.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("pitch", pitch);
            put("volume", volume);
        }};
    }


    /**
     * It checks wheaten the Sound is enabled, by validating only ones with a {@link #volume} and {@link #pitch} more than zero.
     *
     * @return An enabled state, wheaten the Sound is enabled or not.
     */
    @Override
    public boolean isEnabled() {
        return volume > 0.0 && pitch > 0.0;
    }

}