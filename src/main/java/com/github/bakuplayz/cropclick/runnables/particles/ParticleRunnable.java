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

    private final Timer timer;

    private final Location location;

    private final List<Particle> queuedParticles;


    public ParticleRunnable(@NotNull Block block) {
        this.queuedParticles = new ArrayList<>();
        this.timer = new Timer(true);
        this.location = block.getLocation();
    }


    /**
     * Adds a particle to the {@link #queuedParticles queue of particles}.
     *
     * @param name   the name of the particle.
     * @param amount the amount of the particle to spawn.
     * @param speed  the speed at which the particle should be played.
     * @param delay  the delay in milliseconds before playing the particle.
     */
    public void queueParticle(@NotNull String name, int amount, double speed, double delay) {
        queuedParticles.add(new Particle(name, amount, speed, delay));
    }


    /**
     * Runs each {@link Particle} one by one till done.
     */
    @Override
    public void run() {
        long delay = 0;
        for (Particle particle : queuedParticles) {
            delay += particle.getDelay();
            timer.schedule(
                    new ParticleTask(particle, location),
                    delay
            );
        }
        timer.schedule(clean(), delay + 10);
    }


    /**
     * Clears the {@link Timer timer} and removes the {@link ParticleTask} when called.
     *
     * @return a cleaning task.
     */
    @Override
    @Contract(value = " -> new", pure = true)
    public @NotNull TimerTask clean() {
        return new TimerTask() {

            @Override
            public void run() {
                timer.purge();
            }

        };
    }

}