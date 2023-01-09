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
import com.github.bakuplayz.cropclick.yaml.SoundYaml;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;


/**
 * A class representing the sound {@link ConfigSection configuration section}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundConfigSection extends ConfigSection {

    private final CropsConfig cropsConfig;

    /**
     * A variable containing every crop's sounds.
     */
    private final Map<String, IndexedYamlMap<SoundYaml>> sounds;


    public SoundConfigSection(@NotNull CropsConfig config) {
        super(config.getConfig());
        this.cropsConfig = config;
        this.sounds = new LinkedHashMap<>();
    }


    /**
     * Loads all the {@link SoundYaml sounds}.
     */
    public void loadSounds() {
        for (String cropName : cropsConfig.getCropsNames()) {
            Set<String> soundNames = getSoundNames(cropName);

            if (soundNames.isEmpty()) {
                sounds.put(cropName, new IndexedYamlMap<>());
                continue;
            }

            sounds.put(cropName, getExistingSounds(cropName, soundNames));
        }
    }


    /**
     * Initializes the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     */
    private void initSound(@NotNull String cropName, @NotNull String soundName) {
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
     * Updates the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     * @param sound     the updated sound.
     */
    private void updateSound(@NotNull String cropName, @NotNull String soundName, @NotNull SoundYaml sound) {
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
     * Initializes or updates the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     * @param sound     the updated sound.
     */
    private void initOrUpdateSound(@NotNull String cropName, @NotNull String soundName, SoundYaml sound) {
        if (sound == null) {
            initSound(cropName, soundName);
            return;
        }

        updateSound(cropName, soundName, sound);
    }


    /**
     * Gets the delay of the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     *
     * @return the delay of the sound.
     */
    public double getDelay(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isPresentAndEnabled(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".delay",
                    0
            );
        }

        return indexedSounds.get(soundName).getDelay();
    }


    /**
     * Sets the delay of the {@link SoundYaml provided sound} for the {@link Crop provided crop} to the provided delay.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     * @param delay     the delay to set.
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
     * Gets the pitch of the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     *
     * @return the pitch of the sound.
     */
    public double getPitch(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isPresentAndEnabled(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".pitch",
                    0
            );
        }

        return sounds.get(cropName).get(soundName).getPitch();
    }


    /**
     * Sets the pitch of the {@link SoundYaml provided sound} for the {@link Crop provided crop} to the provided pitch.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     * @param pitch     the pitch to set.
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
     * Gets the volume of the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     *
     * @return the volume of the sound.
     */
    public double getVolume(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (!isPresentAndEnabled(soundName, indexedSounds)) {
            return config.getDouble(
                    "crops." + cropName + ".sounds." + soundName + ".volume",
                    0
            );
        }

        return indexedSounds.get(soundName).getVolume();
    }


    /**
     * Sets the volume of the {@link SoundYaml provided sound} for the {@link Crop provided crop} to the provided volume.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     * @param volume    the volume to set.
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
     * Gets the order of the {@link SoundYaml provided sound} for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     *
     * @return the order of the sound.
     */
    public int getOrder(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> soundsOfCrop = sounds.get(cropName);
        return soundsOfCrop == null ? -1 : soundsOfCrop.indexOf(soundName);
    }


    /**
     * Swaps the order of {@link SoundYaml two sounds} with the provided orders for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop to swap the sound for.
     * @param oldOrder the old (previous) order of the sound.
     * @param newOrder the new order of the sound.
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
     * Checks whether the {@link SoundYaml provided sound} is enabled for the {@link Crop provided crop}.
     *
     * @param cropName  the name of the crop.
     * @param soundName the name of the sound.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String cropName, @NotNull String soundName) {
        IndexedYamlMap<SoundYaml> soundsOfCrop = sounds.get(cropName);
        return soundsOfCrop != null && soundsOfCrop.hasKey(soundName);
    }


    /**
     * Checks whether the {@link SoundYaml provided sound} is present and enabled for the {@link Crop provided crop}.
     *
     * @param soundName     the name of the sound.
     * @param presentSounds the sounds that are present for the crop.
     *
     * @return true if enabled, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isPresentAndEnabled(@NotNull String soundName, IndexedYamlMap<SoundYaml> presentSounds) {
        if (presentSounds == null) {
            return false;
        }

        return presentSounds.hasKey(soundName);
    }


    /**
     * Gets the amount of {@link SoundYaml sounds} for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the amount of sounds for the crop.
     */
    public int getAmountOfSounds(@NotNull String cropName) {
        return getSounds(cropName).size();
    }


    /**
     * Gets all the {@link SoundYaml sounds} for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the sounds for the crop.
     */
    public @NotNull List<String> getSounds(@NotNull String cropName) {
        IndexedYamlMap<SoundYaml> indexedSounds = sounds.get(cropName);

        if (indexedSounds == null) {
            return Collections.emptyList();
        }

        return indexedSounds.toStringList();
    }


    /**
     * Gets all the {@link SoundYaml sound} names for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the sound names for the crop.
     */
    private @NotNull
    @Unmodifiable Set<String> getSoundNames(@NotNull String cropName) {
        ConfigurationSection soundSection = config.getConfigurationSection(
                "crops." + cropName + ".sounds"
        );

        if (soundSection == null) {
            return Collections.emptySet();
        }

        return soundSection.getKeys(false);
    }


    /**
     * Gets all the {@link SoundYaml existing sounds} for the {@link Crop provided crop}.
     *
     * @param cropName   the name of the crop.
     * @param soundNames the sound names to filter with.
     *
     * @return the existing sounds for the crop.
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