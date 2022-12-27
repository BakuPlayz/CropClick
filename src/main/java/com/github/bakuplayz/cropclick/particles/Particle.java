package com.github.bakuplayz.cropclick.particles;

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
public final class Particle {

    private final @Getter String name;

    private final @Getter int amount;
    private final @Getter float speed;
    private final @Getter double delay;


    public Particle(@NotNull String name, int amount, double speed, double delay) {
        this.speed = (float) speed;
        this.amount = amount;
        this.delay = delay;
        this.name = name;
    }

}