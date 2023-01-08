package com.github.bakuplayz.cropclick.runnables.particles;

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

    /**
     * A variable measured in milliseconds.
     */
    public static final int MIN_DELAY = 0;

    /**
     * A variable measured in milliseconds.
     */
    public static final int MAX_DELAY = 5000;

    public static final int MIN_SPEED = 0;
    public static final int MAX_SPEED = 50;

    public static final int MIN_AMOUNT = 0;
    public static final int MAX_AMOUNT = 20;

    private final @Getter String name;

    /**
     * The given delay before playing the particle effect ({@link #MIN_DELAY min delay} & {@link #MAX_DELAY max delay}).
     */
    private final @Getter double delay;

    /**
     * The given speed the particle effect should be played at ({@link #MIN_SPEED min speed} & {@link #MAX_SPEED max speed}).
     */
    private final @Getter float speed;

    /**
     * The given amount of this particle effect to play at once ({@link #MIN_AMOUNT min amount} & {@link #MAX_AMOUNT max amount}).
     */
    private final @Getter int amount;


    public Particle(@NotNull String name, int amount, double speed, double delay) {
        this.speed = (float) speed;
        this.amount = amount;
        this.delay = delay;
        this.name = name;
    }

}