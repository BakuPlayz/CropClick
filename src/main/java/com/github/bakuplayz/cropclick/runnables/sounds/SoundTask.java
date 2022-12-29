package com.github.bakuplayz.cropclick.runnables.sounds;

import com.github.bakuplayz.cropclick.runnables.RunnableTask;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;


/**
 * A class representing a Sound as a {@link RunnableTask}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundTask extends TimerTask implements RunnableTask {

    private final Sound sound;
    private final Location location;


    public SoundTask(@NotNull Sound sound, @NotNull Location location) {
        this.location = location;
        this.sound = sound;
    }


    /**
     * Plays the {@link #sound} at the {@link #location} as a {@link RunnableTask}.
     */
    @Override
    public void run() {
        assert location.getWorld() != null; // Only here for the compiler, since the location cannot be null and therefore not the world either.

        location.getWorld().playSound(
                location,
                org.bukkit.Sound.valueOf(sound.getName()),
                sound.getVolume(),
                sound.getPitch()
        );
    }

}