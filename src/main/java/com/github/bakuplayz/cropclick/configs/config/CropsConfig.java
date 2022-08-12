package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
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


    public void setupSections() {
        this.cropSection = new CropConfigSection(this);
        this.seedSection = new SeedConfigSection(this);
        this.addonSection = new AddonConfigSection(this);
        this.soundSection = new SoundConfigSection(this);
        this.particleSection = new ParticleConfigSection(this);
    }


    public void loadSections() {
        particleSection.loadParticles();
        soundSection.loadSounds();
    }

}