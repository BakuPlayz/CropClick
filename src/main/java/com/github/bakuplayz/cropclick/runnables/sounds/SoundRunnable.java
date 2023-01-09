/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * A class representing a Sound as a {@link Runnable}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundRunnable implements Runnable {

    private final Timer timer;

    private final Location location;

    private final List<Sound> queuedSounds;


    public SoundRunnable(@NotNull Block block) {
        this.timer = new Timer(true);
        this.queuedSounds = new ArrayList<>();
        this.location = block.getLocation();
    }


    /**
     * Adds a sound to the {@link #queuedSounds queue of sounds}.
     *
     * @param name   the name of the sound.
     * @param pitch  the pitch of the sound.
     * @param volume the volume of the sound.
     * @param delay  the delay in milliseconds before playing the sound.
     */
    public void queueSound(@NotNull String name, double pitch, double volume, double delay) {
        queuedSounds.add(new Sound(name, pitch, volume, delay));
    }


    /**
     * Runs each {@link Sound} one by one till done.
     */
    @Override
    public void run() {
        long delay = 0;
        for (Sound sound : queuedSounds) {
            delay += sound.getDelay();
            timer.schedule(
                    new SoundTask(sound, location),
                    delay
            );
        }
        timer.schedule(clean(), delay + 10);
    }


    /**
     * Clears the {@link Timer timer} and removes the {@link SoundTask} when called.
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