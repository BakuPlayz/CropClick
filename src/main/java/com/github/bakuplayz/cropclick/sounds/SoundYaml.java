package com.github.bakuplayz.cropclick.sounds;

import lombok.Getter;
import lombok.Setter;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundYaml {

    private @Setter @Getter double delay;
    private @Setter @Getter double pitch;
    private @Setter @Getter double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }

}