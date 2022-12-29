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


    public ParticleConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
        this.particles = new LinkedHashMap<>();
    }


    /**
     * It loads the particles for each crop, and saved them to the {@link #particles particles map}
     * with the crop's name as key and particles in a {@link IndexedYamlMap<ParticleYaml> map} as values.
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
     * If the particle is enabled, then add it to the crop's {@link IndexedYamlMap map}.
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
     * If the particle is disabled, remove it from the {@link #particles particles map}. Otherwise, add it to the {@link IndexedYamlMap map}.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     * @param particle     The particle to update.
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
     * It gets the delay, between the previous and this particle being spawned, for the given crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The delay of the particle.
     */
    public double getDelay(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndPresent(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".delay",
                    0
            );
        }

        return indexedParticles.get(particleName).getDelay();
    }


    /**
     * It sets the delay, between the previous and this particle being spawn, of a particle for the given crop.
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
     * It gets the speed of a particle for the given crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The speed of the particle.
     */
    public double getSpeed(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndPresent(particleName, indexedParticles)) {
            return config.getDouble(
                    "crops." + cropName + ".particles." + particleName + ".speed",
                    0
            );
        }

        return indexedParticles.get(particleName).getSpeed();
    }


    /**
     * It sets the speed of a particle for the given crop.
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
     * It gets the amount of particles to spawn, for a specific particle type, of the given crop.
     *
     * @param cropName     The cropName of the crop.
     * @param particleName The name of the particle.
     *
     * @return The amount of particles for the given type.
     */
    public int getAmount(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (!isEnabledAndPresent(particleName, indexedParticles)) {
            return config.getInt(
                    "crops." + cropName + ".particles." + particleName + ".amount",
                    0
            );
        }

        return indexedParticles.get(particleName).getAmount();
    }


    /**
     * It sets the amount of particles, for a specific particle type, that will be spawned when a crop is harvested.
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
     * It returns the order (index) of the particle in the {@link #particles map of particles} for the given crop.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle you want to get the order of.
     *
     * @return The index of the particleName in the {@link #particles map of particles} for the cropName.
     */
    public int getOrder(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> particlesOfCrop = particles.get(cropName);
        return particlesOfCrop == null ? -1 : particlesOfCrop.indexOf(particleName);
    }


    /**
     * It swaps the order of two particles in the given crop's {@link #particles particles map}.
     *
     * @param cropName The name of the crop to swap the particles of.
     * @param oldOrder The old (previous) order of the particle.
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
     * It returns true if the particle is enabled, false otherwise.
     *
     * @param cropName     The name of the crop.
     * @param particleName The name of the particle.
     *
     * @return The particle's enable status.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String particleName) {
        IndexedYamlMap<ParticleYaml> particlesOfCrop = particles.get(cropName);
        return particlesOfCrop != null && particlesOfCrop.hasKey(particleName);
    }


    /**
     * If the {@link IndexedYamlMap<ParticleYaml> particles map} is null, return false. Otherwise, return whether the given map
     * has the given particle name (as a key).
     *
     * @param particleName     The name of the particle you want to check.
     * @param indexedParticles The {@link IndexedYamlMap} that contains all currently enabled particles.
     *
     * @return The enabled and present status of a particle, in the given map.
     */
    private boolean isEnabledAndPresent(@NotNull String particleName, IndexedYamlMap<ParticleYaml> indexedParticles) {
        if (indexedParticles == null) {
            return false;
        }

        return indexedParticles.hasKey(particleName);
    }


    /**
     * It returns the amount of particles a crop has.
     *
     * @param cropName The name of the crop you want to get the particles for.
     *
     * @return The amount of particles for the given crop.
     */
    public int getAmountOfParticles(@NotNull String cropName) {
        return getParticles(cropName).size();
    }


    /**
     * It gets the particles for the given crop.
     *
     * @param cropName The name of the crop to get the particles for.
     *
     * @return Particles associated with the given crop, as a {@link List<String> string list}.
     */
    public @NotNull List<String> getParticles(@NotNull String cropName) {
        IndexedYamlMap<ParticleYaml> indexedParticles = particles.get(cropName);

        if (indexedParticles == null) {
            return Collections.emptyList();
        }

        return indexedParticles.toList();
    }


    /**
     * It gets the names of all the particles for a given crop.
     *
     * @param cropName The name of the crop to get the particle names for.
     *
     * @return Particle names associated with the given crop, as a {@link Set<String> string set}.
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
     * It retrieves the enabled particles for the given crop.
     *
     * @param cropName      The name of the crop.
     * @param particleNames The names of the particles to search for.
     *
     * @return {@link IndexedYamlMap<ParticleYaml> Maps} populated by the enabled {@link ParticleYaml particles}, that was searched for the given crop.
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