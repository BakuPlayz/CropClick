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

import com.github.bakuplayz.cropclick.crops.Crop;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;


/**
 * A class representing the YAML file: 'crops.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
public final class CropsConfig extends AbstractPersistentYaml {

    public CropsConfig() {
        super("crops.yml");
    }


    public void addSettings(@NotNull Crop crop) {
        if (!isNull(ConfigurationKey.CROP, crop.getName())) return;

        for (ConfigurationKey key : ConfigurationKey.values()) {
            if (!key.isInitializable()) continue;
            setWithoutSave(key, key.getDefaultValue(), crop.getName());
        }

        save();
    }


    public void removeSettings(@NotNull Crop crop) {
        if (!isNull(ConfigurationKey.CROP, crop.getName())) {
            set(ConfigurationKey.CROP, null, crop.getName());
        }

        if (crop.getSeed() != null && !isNull(ConfigurationKey.SEED, crop.getSeed().getName())) {
            set(ConfigurationKey.SEED, null, crop.getSeed().getName());
        }
    }


    public void swapSoundOrder(@NotNull Crop crop, int before, int after) {
        swapEntries(before, after, ConfigurationKey.SOUND, crop.getName());
    }


    public int getSoundOrder(@NotNull Crop crop, @NotNull String soundName) {
        List<String> sounds = new ArrayList<>(getKeys(ConfigurationKey.SOUNDS, crop.getName()));
        return IntStream.range(0, sounds.size())
                       .filter(i -> sounds.get(i).equals(soundName))
                       .findFirst().orElse(-1);
    }


    public void swapParticleOrder(@NotNull Crop crop, int before, int after) {
        swapEntries(before, after, ConfigurationKey.PARTICLE, crop.getName());
    }


    public int getParticleOrder(@NotNull Crop crop, @NotNull String particleName) {
        List<String> particles = new ArrayList<>(getKeys(ConfigurationKey.PARTICLES, crop.getName()));
        return IntStream.range(0, particles.size())
                       .filter(i -> particles.get(i).equals(particleName))
                       .findFirst().orElse(-1);
    }


    private void swapEntries(int from, int to, @NotNull ConfigurationKey key, String @NotNull ... args) {
        List<String> keys = new ArrayList<>(getKeys(key.getParent(), args));
        String fromKey = keys.get(from);
        String toKey = keys.get(to);

        LinkedHashMap<String, Object> reordered = new LinkedHashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            String current = keys.get(i);
            if (i == from) {
                reordered.put(toKey, getObject(key, (String[]) ArrayUtils.add(args, toKey)));
            } else if (i == to) {
                reordered.put(fromKey, getObject(key, (String[]) ArrayUtils.add(args, fromKey)));
            } else {
                reordered.put(current, getObject(key, (String[]) ArrayUtils.add(args, current)));
            }
        }

        setWithReload(key.getParent(), reordered, "");
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        // Addon section
        MCMMO_REASON("crops.%s.addons.mcMMO.reason", "", true),
        MCMMO_EXPERIENCE("crops.%s.addons.mcMMO.experience", 0.0d, true),
        JOBS_POINTS("crops.%s.addons.jobsReborn.points", 0.0d, true),
        JOBS_MONEY("crops.%s.addons.jobsReborn.money", 0.0d, true),
        JOBS_EXPERIENCE("crops.%s.addons.jobsReborn.experience", 0.0d, true),
        SKILLS_EXPERIENCE("crops.%s.addons.auraSkills.experience", 0.0d, true),

        // Particle section
        PARTICLES("crops.%s.particles", Collections.emptyList(), true),
        PARTICLE("crops.%s.particles.%s", null, false, PARTICLES),
        PARTICLE_DELAY("crops.%s.particles.%s.delay", 0L, false),
        PARTICLE_SPEED("crops.%s.particles.%s.speed", 0.0d, false),
        PARTICLE_AMOUNT("crops.%s.particles.%s.amount", 0, false),

        // Sound section
        SOUNDS("crops.%s.sounds", Collections.emptyList(), true),
        SOUND("crops.%s.sounds.%s", null, false, SOUNDS),
        SOUND_DELAY("crops.%s.sounds.%s.delay", 0L, false),
        SOUND_PITCH("crops.%s.sounds.%s.pitch", 0.0d, false),
        SOUND_VOLUME("crops.%s.sounds.%s.volume", 0.0d, false),

        // Seed section
        SEED("seeds.%s", null, false),
        SEED_DROP_NAME("seeds.%s.drop.name", "", true),
        SEED_DROP_AMOUNT("seeds.%s.drop.amount", 0, true),
        SEED_DROP_CHANCE("seeds.%s.drop.chance", 0.0d, true),
        SEED_ENABLED("seeds.%s.isEnabled", true, true),

        // Crop section
        CROP("crops.%s", null, false),
        CROP_DROP_NAME("crops.%s.drop.name", "", true),
        CROP_DROP_AMOUNT("crops.%s.drop.amount", 0, true),
        CROP_DROP_CHANCE("crops.%s.drop.chance", 0.0d, true),
        CROP_DROP_AT_LEAST_ONE("crops.%s.drop.atLeastOne", true, true),
        CROP_HARVESTABLE("crops.%s.isHarvestable", true, true),
        CROP_LINKABLE("crops.%s.isLinkable", true, true),
        CROP_SHOULD_REPLANT("crops.%s.shouldReplant", true, true);

        @NotNull
        private final String path;

        private final Object defaultValue;

        private final boolean isInitializable;

        private ConfigurationKey parent;

    }

}