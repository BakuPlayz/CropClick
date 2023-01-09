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

package com.github.bakuplayz.cropclick.runnables.particles;

import com.github.bakuplayz.cropclick.runnables.RunnableTask;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Random;
import java.util.TimerTask;


/**
 * A class representing a Particle as a {@link RunnableTask}.
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
     * Displays the {@link #particle} at the {@link #location} as a {@link RunnableTask}.
     */
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