package com.github.bakuplayz.cropclick.particles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class ParticleYaml {

    private @Setter @Getter double delay;
    private @Setter @Getter double speed;
    private @Setter @Getter double volume;
    private @Setter @Getter int amount;


    public ParticleYaml(double delay, double speed, double volume, int amount) {
        this.delay = delay;
        this.speed = speed;
        this.volume = volume;
        this.amount = amount;
    }

}