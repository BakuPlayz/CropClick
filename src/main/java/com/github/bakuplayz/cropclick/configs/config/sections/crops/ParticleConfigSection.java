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

package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.collections.IndexedYamlMap;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.yaml.ParticleYaml;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;


/**
 * A class representing the particle configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleConfigSection extends ConfigSection {

    private final CropsConfig cropsConfig;

    /**
     * A variable containing every crop's particles.
     */
    private final Map<String, IndexedYamlMap<ParticleYaml>> particles;


    public ParticleConfigSection(@NotNull CropsConfig config) {
        super(config.getConfig());
        this.cropsConfig = config;
        this.particles = new LinkedHashMap<>();
    }


    /**
     * Loads all the {@link ParticleYaml particles}.
     */
    public void loadParticles() {
        for (String cropName : cropsConfig.getCropsNames()) {
            Set<String> particleNames = getParticleNames(cropName);

            if (particleNames.isEmpty()) {
                particles.put(cropName, new IndexedYamlMap<>());
                continue;
            }

            particles.put(cropName, getExistingParticles(cropName, particleNames));
        }
    }


    /**
     * Initializes the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     */
    public void initParticle(@NotNull String cropName, @NotNull String particleName) {
        ParticleYaml particle = new ParticleYaml(
                getDelay(cropName, particleName),
                getSpeed(cropName, particleName),
                getAmount(cropName, particleName)
        );

        if (!particle.isEnabled()) {
            return;
        }

        particles.get(cropName).put(
                particleName,
                particle
        );
    }


    /**
     * Updates the {@link ParticleYaml particle particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     * @param particle     the updated particle.
     */
    public void updateParticle(@NotNull String cropName, @NotNull String particleName, @NotNull ParticleYaml particle) {
        if (!particle.isEnabled()) {
            particles.get(cropName).remove(particleName);
            return;
        }

        particles.get(cropName).put(
                particleName,
                particle
        );
    }


    /**
     * Initializes or updates the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     * @param particle     the updated particle.
     */
    public void initOrUpdateParticle(@NotNull String cropName, @NotNull String particleName, ParticleYaml particle) {
        if (particle == null) {
            initParticle(cropName, particleName);
            return;
        }

        updateParticle(cropName, particleName, particle);
    }


    /**
     * Gets the delay of the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     *
     * @return the delay of the particle.
     */
    public double getDelay(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isPresentAndEnabled(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".delay",
                    0
            );
        }

        return indexedParticles.get(particleName).getDelay();
    }


    /**
     * Sets the delay of the {@link ParticleYaml provided particle} for the {@link Crop provided crop} to the provided delay.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     * @param delay        the delay to set.
     */
    public void setDelay(@NotNull String cropName, @NotNull String particleName, double delay) {
        config.set("crops." + cropName + ".particles." + particleName + ".delay", delay);
        cropsConfig.saveConfig();

        ParticleYaml particle = particles.get(cropName).get(particleName);

        initOrUpdateParticle(
                cropName,
                particleName,
                particle == null ? null : particle.setDelay(delay)
        );
    }


    /**
     * Gets the speed of the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     *
     * @return the speed of the particle.
     */
    public double getSpeed(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isPresentAndEnabled(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".speed",
                    0
            );
        }

        return indexedParticles.get(particleName).getSpeed();
    }


    /**
     * Sets the speed of the {@link ParticleYaml provided particle} for the {@link Crop provided crop} to the provided speed.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     * @param speed        the speed to set.
     */
    public void setSpeed(@NotNull String cropName, @NotNull String particleName, double speed) {
        config.set("crops." + cropName + ".particles." + particleName + ".speed", speed);
        cropsConfig.saveConfig();

        ParticleYaml particle = particles.get(cropName).get(particleName);

        initOrUpdateParticle(
                cropName,
                particleName,
                particle == null ? null : particle.setSpeed(speed)
        );
    }


    /**
     * Gets the amount of the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     *
     * @return the amount of the particle.
     */
    public int getAmount(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isPresentAndEnabled(particleName, indexedParticles)) {
            return config.getInt(
                    "crops." + cropName + ".particles." + particleName + ".amount",
                    0
            );
        }

        return indexedParticles.get(particleName).getAmount();
    }


    /**
     * Sets the amount of the {@link ParticleYaml provided particle} for the {@link Crop provided crop} to the provided amount.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     * @param amount       the amount to set.
     */
    public void setAmount(@NotNull String cropName, @NotNull String particleName, int amount) {
        config.set("crops." + cropName + ".particles." + particleName + ".amount", amount);
        cropsConfig.saveConfig();

        ParticleYaml particle = particles.get(cropName).get(particleName);

        initOrUpdateParticle(
                cropName,
                particleName,
                particle == null ? null : particle.setAmount(amount)
        );
    }


    /**
     * Gets the order of the {@link ParticleYaml provided particle} for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     *
     * @return the order of the particle.
     */
    public int getOrder(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> particlesOfCrop = particles.get(cropName);
        return particlesOfCrop == null ? -1 : particlesOfCrop.indexOf(particleName);
    }


    /**
     * Swaps the order of {@link ParticleYaml two particles} with the provided orders for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop to swap the particle for.
     * @param oldOrder the old (previous) order of the particle.
     * @param newOrder the new order of the particle.
     */
    public void swapOrder(@NotNull String cropName, int oldOrder, int newOrder) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return;
        }

        indexedParticles.swap(oldOrder, newOrder);

        config.set(
                "crops." + cropName + ".particles",
                indexedParticles.toYaml()
        );

        cropsConfig.saveConfig();
    }


    /**
     * Checks whether the {@link ParticleYaml provided particle} is enabled for the {@link Crop provided crop}.
     *
     * @param cropName     the name of the crop.
     * @param particleName the name of the particle.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> particlesOfCrop = particles.get(cropName);
        return particlesOfCrop != null && particlesOfCrop.hasKey(particleName);
    }


    /**
     * Checks whether the {@link ParticleYaml provided particle} is present and enabled for the {@link Crop provided crop}.
     *
     * @param particleName     the name of the particle.
     * @param presentParticles the particles that are present for the crop.
     *
     * @return true if enabled, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isPresentAndEnabled(@NotNull String particleName, IndexedYamlMap<ParticleYaml> presentParticles) {
        if (presentParticles == null) {
            return false;
        }

        return presentParticles.hasKey(particleName);
    }


    /**
     * Gets the amount of {@link ParticleYaml particles} for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the amount of particles for the crop.
     */
    public int getAmountOfParticles(@NotNull String cropName) {
        return getParticles(cropName).size();
    }


    /**
     * Gets all the {@link ParticleYaml particles} for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the particles for the crop.
     */
    public @NotNull List<String> getParticles(@NotNull String cropName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return Collections.emptyList();
        }

        return indexedParticles.toStringList();
    }


    /**
     * Gets all the {@link ParticleYaml particle} names for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the particle names for the crop.
     */
    private @NotNull
    @Unmodifiable Set<String> getParticleNames(@NotNull String cropName) {
        ConfigurationSection particleSection = config.getConfigurationSection(
                "crops." + cropName + ".particles"
        );

        if (particleSection == null) {
            return Collections.emptySet();
        }

        return particleSection.getKeys(false);
    }


    /**
     * Gets all the {@link ParticleYaml existing particles} for the {@link Crop provided crop}.
     *
     * @param cropName      the name of the crop.
     * @param particleNames the particle names to filter with.
     *
     * @return the existing particles for the crop.
     */
    private @NotNull IndexedYamlMap<ParticleYaml> getExistingParticles(@NotNull String cropName,
                                                                       @NotNull Set<String> particleNames) {
        IndexedYamlMap<ParticleYaml> existingParticles = new IndexedYamlMap<>();

        for (String particleName : particleNames) {
            ParticleYaml particle = new ParticleYaml(
                    getDelay(cropName, particleName),
                    getSpeed(cropName, particleName),
                    getAmount(cropName, particleName)
            );

            if (particle.isEnabled()) {
                existingParticles.put(particleName, particle);
            }
        }

        return existingParticles;
    }

}