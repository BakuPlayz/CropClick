/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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

package com.github.bakuplayz.cropclick.configurations.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import com.github.bakuplayz.cropclick.crops.Crop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'crops.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
public final class CropsConfig extends AbstractConfiguration {

    public CropsConfig(@NotNull CropClick plugin) {
        super(plugin, "crops.yml");
    }


    public void addSettings(@NotNull Crop crop) {
        if (get(CropsConfig.ConfigurationKey.CROP)) return;

        for (ConfigurationKey key : CropsConfig.ConfigurationKey.values()) {
            if (key == CropsConfig.ConfigurationKey.CROP) continue;
            if (key == CropsConfig.ConfigurationKey.SEED) continue;
            setWithoutSave(key, key.getDefaultValue(), crop.getName());
        }

        save();
    }


    public void removeSettings(@NotNull Crop crop) {
        if (get(CropsConfig.ConfigurationKey.CROP, crop.getName()) != null) {
            set(CropsConfig.ConfigurationKey.CROP, null, crop.getName());
        }

        if (crop.getSeed() != null && get(CropsConfig.ConfigurationKey.SEED, crop.getSeed().getName()) != null) {
            set(CropsConfig.ConfigurationKey.SEED, null, crop.getSeed().getName());
        }
    }


    @Getter
    @AllArgsConstructor
    public enum ConfigurationKey implements com.github.bakuplayz.cropclick.configurations.ConfigurationKey {

        // Addon section
        MCMMO_REASON("crops.%s.addons.mcMMO.reason", ""),
        MCMMO_EXPERIENCE("crops.%s.addons.mcMMO.experience", 0),
        JOBS_POINTS("crops.%s.addons.jobsReborn.points", 0),
        JOBS_MONEY("crops.%s.addons.jobsReborn.money", 0),
        JOBS_EXPERIENCE("crops.%s.addons.jobsReborn.experience", 0),
        SKILLS_EXPERIENCE("crops.%s.addons.auraSkills.experience", 0),

        // Particle section
        PARTICLE_DELAY("crops.%s.particles.%s.delay", 0),
        PARTICLE_SPEED("crops.%s.particles.%s.speed", 0),
        PARTICLE_AMOUNT("crops.%s.particles.%s.amount", 0),
        PARTICLES("crops.%s.particles", 0),

        // Sound section
        SOUND_DELAY("crops.%s.sounds.%s.delay", 0),
        SOUND_PITCH("crops.%s.sounds.%s.pitch", 0),
        SOUND_VOLUME("crops.%s.sounds.%s.volume", 0),
        SOUNDS("crops.%s.sounds", 0),

        // Seed section
        SEED("seeds.%s", null),
        SEED_DROP_NAME("seeds.%s.drop.name", ""),
        SEED_DROP_AMOUNT("seeds.%s.drop.amount", 0),
        SEED_DROP_CHANCE("seeds.%s.drop.chance", 0),
        SEED_ENABLED("seeds.%s.isEnabled", true),

        // Crop section
        CROP("crops.%s", null),
        CROP_DROP_NAME("crops.%s.drop.name", ""),
        CROP_DROP_AMOUNT("crops.%s.drop.amount", 0),
        CROP_DROP_CHANCE("crops.%s.drop.chance", 0),
        CROP_DROP_AT_LEAST_ONE("crops.%s.drop.atLeastOne", true),
        CROP_HARVESTABLE("crops.%s.isHarvestable", true),
        CROP_LINKABLE("crops.%s.isLinkable", true),
        CROP_SHOULD_REPLANT("crops.%s.shouldReplant", true);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}