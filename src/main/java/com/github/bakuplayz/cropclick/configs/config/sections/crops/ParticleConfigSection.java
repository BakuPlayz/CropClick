package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.particles.ParticleYaml;
import com.github.bakuplayz.cropclick.utils.IndexedMap;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleConfigSection extends ConfigSection {

    // TODO: Check through all the comments... I cannot be bothered right now.

    private final CropsConfig cropsConfig;

    private final Map<String, IndexedMap<ParticleYaml>> particles;


    public ParticleConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
        this.particles = new LinkedHashMap<>();
    }


    public void setup() {
        loadParticles();
    }


    private void loadParticles() {
        ConfigurationSection cropSection = config.getConfigurationSection(
                "crops"
        );

        if (cropSection == null) {
            return;
        }

        Set<String> cropNames = cropSection.getKeys(false);

        for (String cropName : cropNames) {
            ConfigurationSection particleSection = config.getConfigurationSection(
                    "crops." + cropName + ".particles"
            );

            IndexedMap<ParticleYaml> indexedMap = new IndexedMap<>();

            if (particleSection == null) {
                particles.put(cropName, indexedMap);
                continue;
            }


            Set<String> particleNames = particleSection.getKeys(false);


            for (String particleName : particleNames) {
                indexedMap.put(particleName, new ParticleYaml(
                        config.getDouble("crops." + cropName + ".particles." + particleName + ".delay"),
                        config.getDouble("crops." + cropName + ".particles." + particleName + ".speed"),
                        config.getDouble("crops." + cropName + ".particles." + particleName + ".volume"),
                        config.getInt("crops." + cropName + ".particles." + particleName + ".amount")
                ));
            }

            particles.put(cropName, indexedMap);
        }

    }


    /**
     * Returns true if the particle is enabled for the crop (aka has one or more amount).
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return A boolean value.
     */
    public boolean isParticleEnabled(@NotNull String cropName, @NotNull String particleName) {
        return getParticleAmount(cropName, particleName) != 0;
    }


    /**
     * Return the amount of enabled particles for a crop.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of enabled particles for a crop.
     */
    public int getAmountOfEnabledParticles(@NotNull String cropName) {
        return getParticles(cropName).size();
    }


    /**
     * Get the particles for a crop.
     *
     * @param cropName The name of the crop.
     *
     * @return A list of strings
     */
    public @NotNull List<String> getParticles(@NotNull String cropName) {
        IndexedMap<ParticleYaml> indexedParticles = particles.get(cropName);
        return indexedParticles == null
               ? Collections.emptyList()
               : indexedParticles.toList();
    }


    /**
     * Get the delay between particle spawns for a specific particle type for a specific crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The delay of the particle.
     */
    public double getParticleDelay(@NotNull String cropName, @NotNull String particleName) {
        IndexedMap<ParticleYaml> map = particles.get(cropName);

        if (!map.hasKey(particleName)) {
            return 0;
        }

        return particles.get(cropName).get(particleName).getDelay();
    }


    /**
     * Sets the delay of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param delay        The delay between each particle spawn.
     */
    public void setParticleDelay(@NotNull String cropName, @NotNull String particleName, double delay) {
        config.set("crops." + cropName + ".particles." + particleName + ".delay", delay);
        getOrInitParticle(cropName, particleName).setDelay(delay);
        cropsConfig.saveConfig();
    }


    /**
     * Get the speed of a particle.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The speed of the particle.
     */
    public double getParticleSpeed(@NotNull String cropName, @NotNull String particleName) {
        IndexedMap<ParticleYaml> map = particles.get(cropName);

        if (!map.hasKey(particleName)) {
            return 0;
        }

        return particles.get(cropName).get(particleName).getSpeed();
    }


    /**
     * Sets the speed of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param speed        The speed of the particle.
     */
    public void setParticleSpeed(@NotNull String cropName, @NotNull String particleName, double speed) {
        config.set("crops." + cropName + ".particles." + particleName + ".speed", speed);
        getOrInitParticle(cropName, particleName).setSpeed(speed);
        cropsConfig.saveConfig();
    }


    /**
     * Get the amount of particles to spawn for a specific particle type.
     *
     * @param cropName     The cropName of the crop.
     * @param particleName The name of the particle.
     *
     * @return The amount of particles.
     */
    public int getParticleAmount(@NotNull String cropName, @NotNull String particleName) {
        IndexedMap<ParticleYaml> map = particles.get(cropName);

        if (!map.hasKey(particleName)) {
            return 0;
        }

        return particles.get(cropName).get(particleName).getAmount();
    }


    /**
     * It sets the amount of particles that will be spawned when a crop is harvested
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param amount       The amount of particles to spawn.
     */
    public void setParticleAmount(@NotNull String cropName, @NotNull String particleName, int amount) {
        config.set("crops." + cropName + ".particles." + particleName + ".amount", amount);
        getOrInitParticle(cropName, particleName).setAmount(amount);
        cropsConfig.saveConfig();
    }


    /**
     * Get the particle with the given name, or initialize it with default values if it doesn't exist.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return A ParticleYaml object.
     */
    public ParticleYaml getOrInitParticle(@NotNull String cropName, @NotNull String particleName) {
        IndexedMap<ParticleYaml> map = particles.get(cropName);
        return map.getOrInit(
                particleName,
                new ParticleYaml(0, 0, 0, 0)
        );
    }


    /**
     * It returns the index of the particle in the list of particles for the crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle you want to get the order of.
     *
     * @return The index of the particleName in the list of particles for the cropName.
     */
    public int getParticleOrder(@NotNull String cropName, @NotNull String particleName) {
        IndexedMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return -1;
        }

        return indexedParticles.indexOf(particleName);
    }


    /**
     * It swaps the order of two particles in a crop's particle list.
     *
     * @param cropName The name of the crop to swap the particles of.
     * @param oldOrder The old order of the particle.
     * @param newOrder The new order of the particle.
     */
    public void swapParticleOrder(@NotNull String cropName, int oldOrder, int newOrder) {
        IndexedMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return;
        }

        indexedParticles.swap(oldOrder, newOrder);

        HashMap<String, Object> objectHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, ParticleYaml> entry : indexedParticles.toMap().entrySet()) {
            objectHashMap.put(entry.getKey(), new Object[]{entry.getValue()});
        }

        config.set(
                "crops." + cropName + ".particles",
                objectHashMap
        );

        cropsConfig.saveConfig();
    }

}