package com.github.bakuplayz.cropclick.particles;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleRunnable {


    private final Timer runnable;

    private final Location location;

    private final List<Particle> particles;


    public ParticleRunnable(@NotNull Block block) {
        this.runnable = new Timer(true);
        this.location = block.getLocation();
        this.particles = new ArrayList<>();
    }


    /**
     * Adds a particle to the list of particles to be spawned.
     *
     * @param name   The name of the particle.
     * @param amount The amount of particles to spawn
     * @param speed  The speed of the particle.
     * @param delay  The delay in milliseconds before the particle is played
     */
    public void addParticle(@NotNull String name, int amount, double speed, double delay) {
        particles.add(
                new Particle(name, amount, speed, delay)
        );
    }


    /**
     * For each particle, schedule a task to run after the particle's delay.
     */
    public void run() {
        long delay = 0;
        for (Particle particle : particles) {
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