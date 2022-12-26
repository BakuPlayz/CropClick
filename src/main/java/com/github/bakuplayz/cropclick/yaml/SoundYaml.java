package com.github.bakuplayz.cropclick.yaml;

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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class SoundYaml implements Yamlable, Enableable {

    /**
     * A variable indicating how many milliseconds before the sound should be played.
     */
    private @Setter @Getter @Accessors(chain = true) double delay;

    /**
     * A variable indicating at what pitch the sound should be played at.
     */
    private @Setter @Getter @Accessors(chain = true) double pitch;

    /**
     * A variable indicating at what volume (or block radius) the sound should be played in.
     */
    private @Setter @Getter @Accessors(chain = true) double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }

    /**
     * It checks wheaten or not the {@link SoundYaml sound} is enabled in the yaml file.
     */
    @Override
    public boolean isEnabled() {
        return volume != 0.0 & pitch != 0.0;
    }

    /**
     * It converts {@link SoundYaml this} object to a yaml-styled map.
     *
     * @return A HashMap<String, Object>
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

}