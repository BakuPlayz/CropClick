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

package com.github.bakuplayz.cropclick.tasks;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import com.github.bakuplayz.cropclick.common.types.Particle;
import dev.bakuplayz.spigotstore.task.api.Task;
import lombok.AllArgsConstructor;
import org.bukkit.Location;

import java.util.Random;


/**
 * A class representing a Particle as a {@link Task}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@AllArgsConstructor
public final class ParticleTask implements Task {

    private static final Random RANDOM = new Random();

    private final Particle particle;

    private final Location location;


    /**
     * Displays the {@link #particle} at the {@link #location} as a {@link Task}.
     */
    @Override
    public void run() {
        XParticle effect = XParticle.valueOf(particle.getName());
        ParticleDisplay display = ParticleDisplay.of(effect)
                                          .offset(RANDOM.nextFloat() + 0.5f,
                                                  RANDOM.nextFloat() + 2f,
                                                  RANDOM.nextFloat() + 0.5f
                                          ).withLocation(location)
                                          .withCount(particle.getAmount())
                                          .withExtra(particle.getSpeed());

        display.spawn();
    }

}