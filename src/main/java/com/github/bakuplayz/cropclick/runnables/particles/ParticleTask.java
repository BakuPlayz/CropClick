package com.github.bakuplayz.cropclick.runnables.particles;

import com.github.bakuplayz.cropclick.runnables.RunnableTask;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Random;
import java.util.TimerTask;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleTask extends TimerTask implements RunnableTask {

    private final Particle particle;
    private final Location location;
    private final Random random;


    public ParticleTask(@NotNull Particle particle, @NotNull Location location) {
        this.random = new Random();
        this.particle = particle;
        this.location = location;
    }


    /**
     * Displays the {@link #particle provided particle} at the {@link #location provided location}, as a {@link RunnableTask}.
     * */
    @Override
    public void run() {
        ParticleEffect effect = ParticleEffect.valueOf(
                particle.getName()
        );

        effect.display(
                location,
                random.nextFloat() + 0.5f,
                random.nextFloat() + 2,
                random.nextFloat() + 0.5f,
                particle.getSpeed(),
                particle.getAmount(),
                null
        );
    }

}