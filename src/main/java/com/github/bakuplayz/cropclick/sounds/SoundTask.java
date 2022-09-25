package com.github.bakuplayz.cropclick.sounds;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundTask extends TimerTask {

    private final Sound sound;
    private final Location location;


    public SoundTask(@NotNull Sound sound, @NotNull Location location) {
        this.location = location;
        this.sound = sound;
    }


    @Override
    public void run() {
        assert location.getWorld() != null; // Only here for the compiler, since the location cannot be null and therefore not the world either.

        location.getWorld().playSound(
                location,
                org.bukkit.Sound.valueOf(sound.getName()),
                (float) sound.getVolume(),
                (float) sound.getPitch()
        );
    }

}