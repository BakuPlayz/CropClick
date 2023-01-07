package com.github.bakuplayz.cropclick.runnables.particles;

import com.github.bakuplayz.cropclick.runnables.Runnable;
import com.github.bakuplayz.cropclick.runnables.RunnableTask;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A class representing a Particle as a {@link RunnableTask}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleRunnable implements Runnable {


    private final Timer runnable;

    private final Location location;

    private final List<Particle> queuedParticles;


    public ParticleRunnable(@NotNull Block block) {
        this.queuedParticles = new ArrayList<>();
        this.runnable = new Timer(true);
        this.location = block.getLocation();
    }


    /**
     * This function queues a particle to the queuedParticles list, when harvesting a crop.
     *
     * @param name   The name of the particle.
     * @param amount The amount of particles to spawn
     * @param speed  The speed of the particle.
     * @param delay  The delay in milliseconds before the particle is played
     */
    public void queueParticle(@NotNull String name, int amount, double speed, double delay) {
        queuedParticles.add(
                new Particle(name, amount, speed, delay)
        );
    }


    /**
     * For each particle, schedule a task to run after the particle's delay.
     */
    @Override
    public void run() {
        long delay = 0;
        for (Particle particle : queuedParticles) {
            delay += particle.getDelay();
            runnable.schedule(
                    new ParticleTask(particle, location),
                    delay
            );
        }
        runnable.schedule(clean(), delay + 10);
    }


    /**
     * This function returns a new TimerTask that calls the purge() method of the RunnableScheduledFuture that was passed
     * to the constructor.
     *
     * @return A new TimerTask object.
     */
    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull TimerTask clean() {
        return new TimerTask() {

            @Override
            public void run() {
                runnable.purge();
            }

        };
    }

}