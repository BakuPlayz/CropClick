package com.github.bakuplayz.cropclick.particles;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Particle {

    private final @Getter String name;

    private final @Getter int amount;
    private final @Getter float speed;
    private final @Getter double delay;


    public Particle(@NotNull String name, int amount, double speed, double delay) {
        this.name = name;
        this.amount = amount;
        this.speed = (float) speed;
        this.delay = delay;
    }

}