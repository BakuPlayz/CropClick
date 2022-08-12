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


    //TODO: Make it readable & better
    public void loadSounds() {
        for (String cropName : getCropsNames()) {
            ConfigurationSection soundSection = config.getConfigurationSection(
                    "crops." + cropName + ".sounds"
            );

            IndexedYamlMap<SoundYaml> indexedMap = new IndexedYamlMap<>();

            if (soundSection == null) {
                sounds.put(cropName, indexedMap);
                continue;
            }

            Set<String> soundNames = soundSection.getKeys(false);

            for (String soundName : soundNames) {
                indexedMap.put(
                        soundName,
                        new SoundYaml(
                                config.getDouble("crops." + cropName + ".sounds." + soundName + ".delay"),
                                config.getDouble("crops." + cropName + ".sounds." + soundName + ".pitch"),
                                config.getDouble("crops." + cropName + ".sounds." + soundName + ".volume")
                        )
                );
            }

            sounds.put(cropName, indexedMap);
        }

    }


    /**
     * Get the names of all the crops in the config file.
     *
     * @return A set of strings.
     */
    private @NotNull @Unmodifiable Set<String> getCropsNames() {
        ConfigurationSection cropSection = config.getConfigurationSection(
                "crops"
        );

        if (cropSection == null) {
            return Collections.emptySet();
        }

        return cropSection.getKeys(false);
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
        double volume = getVolume(cropName, soundName);
        double pitch = getPitch(cropName, soundName);
        return volume != 0.0 & pitch != 0.0;
    }


    /**
     * Return the amount of enabled sounds for a given crop.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of enabled sounds for a crop.
     */
    public int getAmountOfEnabledSounds(@NotNull String cropName) {
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
     * Get the delay between the sound being played and the next sound being played.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The delay of the sound.
     */
    public double getDelay(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> map = sounds.get(cropName);

        if (!map.hasKey(soundName)) {
            return 0;
        }

        return sounds.get(cropName).get(soundName).getDelay();
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
        getOrInit(cropName, soundName).setDelay(delay);
        cropsConfig.saveConfig();
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
        IndexedYamlMap<SoundYaml> map = sounds.get(cropName);

        if (!map.hasKey(soundName)) {
            return 0;
        }

        return sounds.get(cropName).get(soundName).getVolume();
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
        getOrInit(cropName, soundName).setVolume(volume);
        cropsConfig.saveConfig();
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
        IndexedYamlMap<SoundYaml> map = sounds.get(cropName);

        if (!map.hasKey(soundName)) {
            return 0;
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
        getOrInit(cropName, soundName).setPitch(pitch);
        cropsConfig.saveConfig();
    }


    /**
     * Get the sound with the given name from the given crop, or create a new sound with the given name if it doesn't
     * exist.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return A SoundYaml object.
     */
    public SoundYaml getOrInit(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> map = sounds.get(cropName);

        if (map.indexOf(soundName) > -1) {
            return map.get(soundName);
        }

        return map.getOrInit(
                soundName,
                new SoundYaml(0, 0, 0)
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
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return -1;
        }

        return indexedSounds.indexOf(soundName);
    }


    /**
     * Swap the order of two sounds in the config file.
     *
     * @param cropName The name of the crop to swap the sound order of.
     * @param oldOrder The old order of the sound.
     * @param newOrder The new order of the sound.
     */
    public void swapOrder(@NotNull String cropName, int oldOrder, int newOrder) {
        long start = System.nanoTime();

        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return;
        }

        indexedSounds.swap(oldOrder, newOrder);

        config.set(
                "crops." + cropName + ".sounds",
                indexedSounds.toYaml()
        );

        System.out.println(System.nanoTime() - start);

        cropsConfig.saveConfig();
    }

}