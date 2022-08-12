package com.github.bakuplayz.cropclick.yaml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public final class SoundYaml extends YamlItem {

    private @Setter @Getter double delay;
    private @Setter @Getter double pitch;
    private @Setter @Getter double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }


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