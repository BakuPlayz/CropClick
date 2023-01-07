package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.*;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Set;


/**
 * A class representing the YAML file: 'crops.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropsConfig extends Config {

    private @Getter CropConfigSection cropSection;
    private @Getter SeedConfigSection seedSection;
    private @Getter AddonConfigSection addonSection;
    private @Getter SoundConfigSection soundSection;
    private @Getter ParticleConfigSection particleSection;


    public CropsConfig(@NotNull CropClick plugin) {
        super(plugin, "crops.yml");
    }


    /**
     * Sets up the crops specific {@link ConfigSection configuration sections}.
     */
    public void setupSections() {
        this.cropSection = new CropConfigSection(this);
        this.seedSection = new SeedConfigSection(this);
        this.addonSection = new AddonConfigSection(this);
        this.soundSection = new SoundConfigSection(this);
        this.particleSection = new ParticleConfigSection(this);
    }


    /**
     * Loads the crop specific {@link ConfigSection configuration sections}.
     */
    public void loadSections() {
        particleSection.loadParticles();
        soundSection.loadSounds();
    }


    /**
     * Adds the configuration settings based on the {@link Crop provided crop}.
     *
     * @param crop the crop to add settings to.
     */
    public void addSettings(@NotNull Crop crop) {
        String cropName = crop.getName();

        if (!cropSection.exists(cropName)) {
            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = crop.getDrop();
                cropSection.setDropName(cropName, drop.getName());
                cropSection.setDropAmount(cropName, drop.getAmount());
                cropSection.setDropChance(cropName, drop.getChance());
                cropSection.setDropAtLeastOne(cropName, crop.dropAtLeastOne());
            }

            // Action Settings
            cropSection.setReplant(cropName, crop.shouldReplant());
            cropSection.setHarvestable(cropName, crop.isHarvestable());
            cropSection.setLinkable(cropName, crop.isLinkable());

            // mcMMO Settings
            addonSection.setMcMMOExperience(cropName, 0);
            addonSection.setMcMMOExperienceReason(
                    cropName,
                    "You harvested " + MessageUtils.beautify(cropName, false) + "."
            );

            // JobsReborn Settings
            addonSection.setJobsMoney(cropName, 0);
            addonSection.setJobsPoints(cropName, 0);
            addonSection.setJobsExperience(cropName, 0);
        }

        if (!crop.hasSeed()) {
            return;
        }

        Seed seed = crop.getSeed();
        String seedName = seed.getName();
        if (!seedSection.exists(seedName)) {
            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = seed.getDrop();
                seedSection.setDropName(seedName, drop.getName());
                seedSection.setDropAmount(seedName, drop.getAmount());
                seedSection.setDropChance(seedName, drop.getChance());
            }

            seedSection.setEnabled(seedName, seed.isEnabled());
        }
    }


    /**
     * Removes the configuration settings based on the {@link Crop provided crop}.
     *
     * @param crop the crop to remove settings from.
     */
    public void removeSettings(@NotNull Crop crop) {
        if (crop.hasSeed()) {
            Seed seed = crop.getSeed();
            String seedName = seed.getName();
            config.set("seeds." + seedName, null);
        }

        config.set("crops." + crop.getName(), null);
        saveConfig();
    }


    /**
     * Gets all the found {@link Crop crops} names.
     *
     * @return all the found crops names.
     */
    @NotNull
    @Unmodifiable
    public Set<String> getCropsNames() {
        ConfigurationSection cropSection = config.getConfigurationSection("crops");

        if (cropSection == null) {
            return Collections.emptySet();
        }

        return cropSection.getKeys(false);
    }

}