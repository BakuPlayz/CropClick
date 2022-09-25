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
    
    private final CropsConfig cropsConfig;

    private final Map<String, IndexedYamlMap<SoundYaml>> sounds;


    public SoundConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
        this.sounds = new LinkedHashMap<>();
    }


    /**
     * It loads the sounds for each crop, and saved them to the {@link #sounds sounds map}
     * with the crop's name as key and sounds in a {@link IndexedYamlMap<SoundYaml> map} as values.
     */
    public void loadSounds() {
        for (String cropName : cropsConfig.getCropsNames()) {
            Set<String> soundNames = getSoundNames(cropName);

            if (soundNames.isEmpty()) {
                sounds.put(cropName, new IndexedYamlMap<>());
                return;
            }

            sounds.put(cropName, getExistingSounds(cropName, soundNames));
        }
    }


    /**
     * If the sound is enabled, add it to the crop's {@link IndexedYamlMap map}.
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
     * If the sound is disabled, remove it from the {@link #sounds sounds map}. Otherwise, add it to the {@link IndexedYamlMap map}.
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
     * It gets the delay, between the previous sound and this sound being played, for the given crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The delay of the sound.
     */
    public double getDelay(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndPresent(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".delay",
                    0
            );
        }

        return indexedSounds.get(soundName).getDelay();
    }


    /**
     * It sets the delay, between the previous sound and this sound being played, of a sound for the given crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound you want to set the delay for.
     * @param delay     The delay between each sound being played.
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
     * It gets the pitch of a sound for the given crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The pitch of the sound.
     */
    public double getPitch(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndPresent(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".pitch",
                    0
            );
        }

        return sounds.get(cropName).get(soundName).getPitch();
    }


    /**
     * It sets the pitch of the sound for the given crop.
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
     * It gets the volume (or range in blocks) of a sound for the given crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The volume of the sound.
     */
    public double getVolume(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isEnabledAndPresent(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".volume",
                    0
            );
        }

        return indexedSounds.get(soundName).getVolume();
    }


    /**
     * It sets the volume (or range in blocks) of a sound for the given crop.
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
     * It returns the order (index) of the sound in the {@link #sounds map of sounds} for the crop.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound you want to get the order of.
     *
     * @return The index of the soundName in the {@link #sounds map of sounds} for the cropName.
     */
    public int getOrder(@NotNull String cropName, @NotNull String soundName) {
        return sounds.get(cropName).indexOf(soundName);
    }


    /**
     * It swaps the order of two sounds in the given crop's {@link #sounds sounds map}.
     *
     * @param cropName The name of the crop to swap the sound order of.
     * @param oldOrder The old (previous) order of the sound.
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
     * It returns true if the sound is enabled, false otherwise.
     *
     * @param cropName  The name of the crop.
     * @param soundName The name of the sound.
     *
     * @return The sound's enabled status.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String soundName) {
        return sounds.get(cropName).hasKey(soundName);
    }


    /**
     * If the {@link IndexedYamlMap<SoundYaml> sounds map} is null, return false. Otherwise, return whether the given map
     * has the given sound name (as a key).
     *
     * @param soundName     The name of the sound to check.
     * @param indexedSounds The {@link IndexedYamlMap} that contains all currently enabled sounds.
     *
     * @return The enabled and present status of a sound, in the given map.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEnabledAndPresent(@NotNull String soundName, IndexedYamlMap<SoundYaml> indexedSounds) {
        if (indexedSounds == null) {
            return false;
        }

        return indexedSounds.hasKey(soundName);
    }


    /**
     * It returns the amount of sounds a crop has.
     *
     * @param cropName The name of the crop you want to get the sounds for.
     *
     * @return The amount of sounds for the given crop.
     */
    public int getAmountOfSounds(@NotNull String cropName) {
        return getSounds(cropName).size();
    }


    /**
     * It gets the sounds for the given crop.
     *
     * @param cropName The name of the crop to get the sounds for.
     *
     * @return Sounds associated with the given crop, as a {@link List<String> string list}.
     */
    public @NotNull List<String> getSounds(@NotNull String cropName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return Collections.emptyList();
        }

        return indexedSounds.toList();
    }


    /**
     * It gets the names of all the sounds for a given crop.
     *
     * @param cropName The name of the crop to get the sound names for.
     *
     * @return Sound names associated with the given crop, as a {@link Set<String> string set}.
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
     * It retrieves the enabled sounds for the given crop.
     *
     * @param cropName   The name of the crop.
     * @param soundNames The names of the sounds to search for.
     *
     * @return {@link IndexedYamlMap<SoundYaml> Maps} populated by the enabled {@link SoundYaml sounds}, that was searched for the given crop.
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