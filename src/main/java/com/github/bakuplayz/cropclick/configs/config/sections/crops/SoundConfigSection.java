package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.collections.IndexedYamlMap;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.yaml.SoundYaml;
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
public final class SoundConfigSection extends ConfigSection {


    // TODO: Check through all the comments... I cannot be bothered right now.

    private final CropsConfig cropsConfig;

    private final Map<String, IndexedYamlMap<SoundYaml>> sounds;


    public SoundConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
        this.sounds = new LinkedHashMap<>();
    }


    /**
     * It loads the sounds for each crop.
     */
    public void loadSounds() {
        cropsConfig.getCropsNames().forEach(cropName -> {
            Set<String> soundNames = getSoundNames(cropName);

            if (soundNames.isEmpty()) {
                sounds.put(cropName, new IndexedYamlMap<>());
                return;
            }

            sounds.put(cropName, getExistingSounds(cropName, soundNames));
        });
    }


    /**
     * If the sound is enabled, add it to the sounds map.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     */
    public void initSound(@NotNull String cropName, @NotNull String soundName) {
        SoundYaml sound = new SoundYaml(
                getDelay(cropName, soundName),
                getPitch(cropName, soundName),
                getVolume(cropName, soundName)
        );

        if (!sound.isEnabled()) {
            return;
        }

        sounds.get(cropName).put(
                soundName,
                sound
        );
    }


    /**
     * If the sound is disabled, remove it from the map. Otherwise, add it to the map.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param sound     The sound to update.
     */
    public void updateSound(@NotNull String cropName, @NotNull String soundName, @NotNull SoundYaml sound) {
        if (!sound.isEnabled()) {
            sounds.get(cropName).remove(soundName);
            return;
        }

        sounds.get(cropName).put(
                soundName,
                sound
        );
    }


    /**
     * If the sound is null, initialize it, otherwise update it.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param sound     The sound to update.
     */
    public void initOrUpdateSound(@NotNull String cropName, @NotNull String soundName, SoundYaml sound) {
        if (sound == null) {
            initSound(cropName, soundName);
            return;
        }

        updateSound(cropName, soundName, sound);
    }


    /**
     * Get the delay between the sound being played and the next sound being played.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The delay of the sound.
     */
    public double getDelay(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndThere(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".delay",
                    0
            );
        }

        return indexedSounds.get(soundName).getDelay();
    }


    /**
     * Sets the delay of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound you want to set the delay for.
     * @param delay     The delay between each sound.
     */
    public void setDelay(@NotNull String cropName, @NotNull String soundName, double delay) {
        config.set("crops." + cropName + ".sounds." + soundName + ".delay", delay);
        cropsConfig.saveConfig();

        SoundYaml sound = sounds.get(cropName).get(soundName);

        initOrUpdateSound(
                cropName,
                soundName,
                sound == null ? null : sound.setDelay(delay)
        );
    }


    /**
     * Get the pitch of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The pitch of the sound.
     */
    public double getPitch(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndThere(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".pitch",
                    0
            );
        }

        return sounds.get(cropName).get(soundName).getPitch();
    }


    /**
     * Sets the pitch of the sound for the specified crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param pitch     The pitch of the sound.
     */
    public void setPitch(@NotNull String cropName, @NotNull String soundName, double pitch) {
        config.set("crops." + cropName + ".sounds." + soundName + ".pitch", pitch);
        cropsConfig.saveConfig();

        SoundYaml sound = sounds.get(cropName).get(soundName);

        initOrUpdateSound(
                cropName,
                soundName,
                sound == null ? null : sound.setPitch(pitch)
        );
    }


    /**
     * Get the volume of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The volume of the sound.
     */
    public double getVolume(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndThere(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".volume",
                    0
            );
        }

        return indexedSounds.get(soundName).getVolume();
    }


    /**
     * Sets the volume of a sound for a crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     * @param volume    The volume of the sound.
     */
    public void setVolume(@NotNull String cropName, @NotNull String soundName, double volume) {
        config.set("crops." + cropName + ".sounds." + soundName + ".volume", volume);
        cropsConfig.saveConfig();

        SoundYaml sound = sounds.get(cropName).get(soundName);

        initOrUpdateSound(
                cropName,
                soundName,
                sound == null ? null : sound.setVolume(volume)
        );
    }


    /**
     * Returns the order of the sound in the list of sounds for the crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound you want to get the order of.
     *
     * @return The index of the soundName in the list of sounds for the cropName.
     */
    public int getOrder(@NotNull String cropName, @NotNull String soundName) {
        return sounds.get(cropName).indexOf(soundName);
    }


    /**
     * Swap the order of two sounds in the config file.
     *
     * @param cropName The name of the crop to swap the sound order of.
     * @param oldOrder The old order of the sound.
     * @param newOrder The new order of the sound.
     */
    public void swapOrder(@NotNull String cropName, int oldOrder, int newOrder) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return;
        }

        indexedSounds.swap(oldOrder, newOrder);

        config.set(
                "crops." + cropName + ".sounds",
                indexedSounds.toYaml()
        );

        cropsConfig.saveConfig();
    }


    /**
     * Returns true if the sound is enabled, false otherwise.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String soundName) {
        return sounds.get(cropName).hasKey(soundName);
    }


    /**
     * If the indexedSounds map is null, return false. Otherwise, return true if the soundName key is not in the
     * indexedSounds map.
     *
     * @param soundName     The name of the sound to check.
     * @param indexedSounds The sounds that are currently enabled.
     *
     * @return The method is returning a boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEnabledAndThere(@NotNull String soundName, IndexedYamlMap<SoundYaml> indexedSounds) {
        if (indexedSounds == null) {
            return false;
        }

        return indexedSounds.hasKey(soundName);
    }


    /**
     * Returns the amount of sounds a crop has.
     *
     * @param cropName The name of the crop you want to get the sounds for.
     *
     * @return The amount of sounds for a crop.
     */
    public int getAmountOfSounds(@NotNull String cropName) {
        return getSounds(cropName).size();
    }


    /**
     * Get the sounds for a crop, or null if the crop doesn't exist.
     *
     * @param cropName The name of the crop to get the sounds for.
     *
     * @return A list of strings.
     */
    public @NotNull List<String> getSounds(@NotNull String cropName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return Collections.emptyList();
        }

        return indexedSounds.toList();
    }


    /**
     * Get the names of all the sounds for a given crop.
     *
     * @param cropName The name of the crop to get the particle names for.
     *
     * @return A set of particle names.
     */
    private @NotNull @Unmodifiable Set<String> getSoundNames(@NotNull String cropName) {
        ConfigurationSection soundSection = config.getConfigurationSection(
                "crops." + cropName + ".sounds"
        );

        if (soundSection == null) {
            return Collections.emptySet();
        }

        return soundSection.getKeys(false);
    }


    /**
     * This function gets all the sounds for a crop from the config file and returns them in an IndexedYamlMap.
     *
     * @param cropName   The name of the crop.
     * @param soundNames The names of the sounds that are already in the config.
     *
     * @return A map of sound names to sound objects.
     */
    private @NotNull IndexedYamlMap<SoundYaml> getExistingSounds(@NotNull String cropName,
                                                                 @NotNull Set<String> soundNames) {
        IndexedYamlMap<SoundYaml> existingSounds = new IndexedYamlMap<>();

        for (String soundName : soundNames) {
            SoundYaml sound = new SoundYaml(
                    getDelay(cropName, soundName),
                    getPitch(cropName, soundName),
                    getVolume(cropName, soundName)
            );

            if (sound.isEnabled()) {
                existingSounds.put(soundName, sound);
            }
        }

        return existingSounds;
    }

}