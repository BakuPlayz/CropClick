package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.collections.IndexedYamlMap;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.yaml.ParticleYaml;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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

    private final Map<String, IndexedYamlMap<ParticleYaml>> particles;


    public ParticleConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
        this.particles = new LinkedHashMap<>();
    }


    /**
     * It loads the particles for each crop.
     */
    public void loadParticles() {
        cropsConfig.getCropsNames().forEach(cropName -> {
            Set<String> particleNames = getParticleNames(cropName);

            if (particleNames.isEmpty()) {
                particles.put(cropName, new IndexedYamlMap<>());
                return;
            }

            particles.put(cropName, getExistingParticles(cropName, particleNames));
        });
    }


    /**
     * If the particle is enabled, add it to the particles map.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
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
     * If the particle is disabled, remove it from the map. Otherwise, add it to the map.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param particle     The sound to update.
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
     * If the particle is null, initialize it, otherwise update it.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param particle     The particle to update.
     */
    public void initOrUpdateParticle(@NotNull String cropName, @NotNull String particleName, ParticleYaml particle) {
        if (particle == null) {
            initParticle(cropName, particleName);
            return;
        }

        updateParticle(cropName, particleName, particle);
    }


    /**
     * Get the delay between particle spawns for a specific particle type for a specific crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The delay of the particle.
     */
    public double getDelay(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndThere(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".delay",
                    0
            );
        }

        return indexedParticles.get(particleName).getDelay();
    }


    /**
     * Sets the delay of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param delay        The delay between each particle spawn.
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
     * Get the speed of a particle.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The speed of the particle.
     */
    public double getSpeed(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndThere(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".speed",
                    0
            );
        }

        return indexedParticles.get(particleName).getSpeed();
    }


    /**
     * Sets the speed of a particle for a crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param speed        The speed of the particle.
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
     * Get the amount of particles to spawn for a specific particle type.
     *
     * @param cropName     The cropName of the crop.
     * @param particleName The name of the particle.
     *
     * @return The amount of particles.
     */
    public int getAmount(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndThere(particleName, indexedParticles)) {
            return config.getInt(
                    "crops." + cropName + ".particles." + particleName + ".amount",
                    0
            );
        }

        return indexedParticles.get(particleName).getAmount();
    }


    /**
     * It sets the amount of particles that will be spawned when a crop is harvested
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param amount       The amount of particles to spawn.
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
     * It returns the index of the particle in the list of particles for the crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle you want to get the order of.
     *
     * @return The index of the particleName in the list of particles for the cropName.
     */
    public int getOrder(@NotNull String cropName, @NotNull String particleName) {
        return particles.get(cropName).indexOf(particleName);
    }


    /**
     * It swaps the order of two particles in a crop's particle list.
     *
     * @param cropName The name of the crop to swap the particles of.
     * @param oldOrder The old order of the particle.
     * @param newOrder The new order of the particle.
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
     * Returns true if the particle is enabled, false otherwise.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String particleName) {
        return particles.get(cropName).hasKey(particleName);
    }


    /**
     * If the indexed particles map is null, return false, otherwise return whether the indexed particles map has the given
     * particle name.
     *
     * @param particleName     The name of the particle you want to check.
     * @param indexedParticles The IndexedYamlMap<ParticleYaml> that contains all the particles.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEnabledAndThere(@NotNull String particleName, IndexedYamlMap<ParticleYaml> indexedParticles) {
        if (indexedParticles == null) {
            return false;
        }

        return indexedParticles.hasKey(particleName);
    }


    /**
     * Returns the amount of particles a crop has.
     *
     * @param cropName The name of the crop you want to get the particles for.
     *
     * @return The amount of particles for a crop.
     */
    public int getAmountOfSounds(@NotNull String cropName) {
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
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return Collections.emptyList();
        }

        return indexedParticles.toList();
    }


    /**
     * Get the names of all the particles for a given crop.
     *
     * @param cropName The name of the crop to get the particle names for.
     *
     * @return A set of particle names.
     */
    private @NotNull @Unmodifiable Set<String> getParticleNames(@NotNull String cropName) {
        ConfigurationSection particleSection = config.getConfigurationSection(
                "crops." + cropName + ".particles"
        );

        if (particleSection == null) {
            return Collections.emptySet();
        }

        return particleSection.getKeys(false);
    }


    /**
     * It takes a crop name and a set of particle names, and returns an IndexedYamlMap of ParticleYaml objects.
     *
     * @param cropName      The name of the crop.
     * @param particleNames The names of the particles to be loaded.
     *
     * @return A map of particle names to particle data.
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