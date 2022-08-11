package com.github.bakuplayz.cropclick.sounds;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Sound {

    private final @Getter String name;

    private final @Getter double delay;
    private final @Getter double pitch;
    private final @Getter double volume;


    public Sound(@NotNull String name, double pitch, double volume, double delay) {
        this.name = name;
        this.pitch = pitch;
        this.volume = volume;
        this.delay = delay;
    }

}