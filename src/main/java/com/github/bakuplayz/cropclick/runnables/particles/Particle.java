package com.github.bakuplayz.cropclick.runnables.particles;

import com.github.bakuplayz.cropclick.menu.menus.particles.ParticleMenu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a Particle in Bukkit.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class Particle {

    private final @Getter String name;


    /**
     * The given delay before playing the particle effect ({@link ParticleMenu#MIN_DELAY min delay} & {@link ParticleMenu#MAX_DELAY max delay}).
     */
    private final @Getter double delay;

    /**
     * The given speed the particle effect should be played at ({@link ParticleMenu#MIN_SPEED min speed} & {@link ParticleMenu#MAX_SPEED max speed}).
     */
    private final @Getter float speed;

    /**
     * The given amount of this particle effect to play at once ({@link ParticleMenu#MIN_AMOUNT min amount} & {@link ParticleMenu#MAX_AMOUNT max amount}).
     */
    private final @Getter int amount;


    public Particle(@NotNull String name, int amount, double speed, double delay) {
        this.speed = (float) speed;
        this.amount = amount;
        this.delay = delay;
        this.name = name;
    }

}