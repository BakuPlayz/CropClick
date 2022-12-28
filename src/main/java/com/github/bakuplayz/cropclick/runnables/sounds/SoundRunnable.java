package com.github.bakuplayz.cropclick.runnables.sounds;

import com.github.bakuplayz.cropclick.runnables.Runnable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Represents a Sound as a {@link Runnable}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundRunnable implements Runnable {

    private final Timer runnable;

    private final Location location;

    private final List<Sound> queuedSounds;


    public SoundRunnable(@NotNull Block block) {
        this.runnable = new Timer(true);
        this.queuedSounds = new ArrayList<>();
        this.location = block.getLocation();
    }


    /**
     * This function queues a sound to the queuedSounds list, when harvesting a crop.
     *
     * @param name   The name of the sound.
     * @param pitch  The pitch of the sound.
     * @param volume The volume of the sound.
     * @param delay  The delay in milliseconds before the sound is played.
     */
    public void queueSound(@NotNull String name, double pitch, double volume, double delay) {
        queuedSounds.add(
                new Sound(name, pitch, volume, delay)
        );
    }


    /**
     * For each sound, schedule a task to run after the sound's delay.
     */
    @Override
    public void run() {
        long delay = 0;
        for (Sound sound : queuedSounds) {
            delay += sound.getDelay();
            runnable.schedule(
                    new SoundTask(sound, location),
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