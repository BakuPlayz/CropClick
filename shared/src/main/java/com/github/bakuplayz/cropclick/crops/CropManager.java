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

package com.github.bakuplayz.cropclick.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Sets;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.common.types.Particle;
import com.github.bakuplayz.cropclick.common.types.Sound;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.ground.*;
import com.github.bakuplayz.cropclick.crops.roof.GlowBerries;
import com.github.bakuplayz.cropclick.crops.tall.*;
import com.github.bakuplayz.cropclick.crops.wall.CocoaBean;
import com.github.bakuplayz.cropclick.tasks.ParticleTask;
import com.github.bakuplayz.cropclick.tasks.SoundTask;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;


/**
 * A manager controlling all the {@link Crop crops}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropManager {

    private final CropsConfig config;


    @Getter
    private final List<Crop> registeredCrops;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    @Getter
    private final HashMap<Crop, Long> harvestedCrops;

    @Getter
    private final CropArguments cropArguments;

    @Getter
    private final CropFinder finder;

    @Getter
    private final AudioVisualFeatures audioVisualFeatures;


    public CropManager(@NotNull CropClick plugin) {
        this.config = plugin.getConfigManager().getCropsConfig();
        this.registeredCrops = new ArrayList<>();
        this.harvestedCrops = new HashMap<>();
        this.finder = new CropFinder(registeredCrops);
        this.cropArguments = new CropArguments(config, this);
        this.audioVisualFeatures = new AudioVisualFeatures(plugin.getTaskScheduler());

        registerVanillaCrops();
    }


    /**
     * Registers all the {@link Crop vanilla crops}.
     */
    private void registerVanillaCrops() {
        if (Versions.supportsBamboos()) {
            registerCrop(new Bamboo(cropArguments));
        }

        if (Versions.supportsBeetroots()) {
            registerCrop(new Beetroot(cropArguments));
        }

        registerCrop(new BrownMushroom(cropArguments));
        registerCrop(new Cactus(cropArguments));
        registerCrop(new Carrot(cropArguments));
        registerCrop(new CocoaBean(cropArguments));

        if (Versions.supportsChorus()) {
            registerCrop(new Chorus(cropArguments));
        }

        if (Versions.supportsDripleaves()) {
            registerCrop(new Dripleaf(cropArguments));
        }

        if (Versions.supportsGlowBerries()) {
            registerCrop(new GlowBerries(cropArguments));
        }

        if (Versions.supportsKelp()) {
            registerCrop(new Kelp(cropArguments));
        }

        registerCrop(new Melon(cropArguments));
        registerCrop(new NetherWart(cropArguments));

        if (Versions.supportsPitcherPlants()) {
            registerCrop(new PitcherPlant(cropArguments));
        }

        registerCrop(new Potato(cropArguments));
        registerCrop(new Pumpkin(cropArguments));
        registerCrop(new RedMushroom(cropArguments));

        if (Versions.supportsSeaPickle()) {
            registerCrop(new SeaPickle(cropArguments));
        }

        registerCrop(new SugarCane(cropArguments));

        if (Versions.supportsSweetBerries()) {
            registerCrop(new SweetBerries(cropArguments));
        }

        if (Versions.supportsTorchFlowers()) {
            registerCrop(new Torchflower(cropArguments));
        }

        if (Versions.supportsTwistingVines()) {
            registerCrop(new TwistingVines(cropArguments));
        }

        registerCrop(new Wheat(cropArguments));
    }


    /**
     * Registers the {@link Crop provided crop}.
     *
     * @param crop the crop to register.
     *
     * @return true iff successful, otherwise false.
     */
    public boolean registerCrop(@NotNull Crop crop) {
        if (finder.isAlreadyRegistered(crop)) {
            return false;
        }

        registeredCrops.add(crop);
        config.addSettings(crop);
        return true;
    }


    /**
     * Unregister the {@link Crop provided crop}.
     *
     * @param crop the crop to unregister.
     *
     * @return true iff successful, otherwise false.
     */
    public boolean unregisterCrop(@NotNull Crop crop) {
        config.removeSettings(crop);
        return registeredCrops.remove(crop);
    }


    /**
     * Gets the amount of {@link #registeredCrops registred crops}.
     *
     * @return the amount of crops.
     */
    public int getAmountOfCrops() {
        return registeredCrops.size();
    }


    @RequiredArgsConstructor
    public final class AudioVisualFeatures {


        @NotNull
        private final TaskScheduler scheduler;


        public void playSoundsAt(@NotNull Crop crop, @NotNull Block block) {
            Sets.forEachWithIndex(config.getKeys(ConfigurationKey.SOUNDS, crop.getName()), (i, sound) -> {
                long delay = config.getLong(ConfigurationKey.SOUND_DELAY, crop.getName(), sound);
                double pitch = config.getDouble(ConfigurationKey.SOUND_PITCH, crop.getName(), sound);
                double volume = config.getDouble(ConfigurationKey.SOUND_VOLUME, crop.getName(), sound);

                scheduler.scheduleLater(new SoundTask(
                        new Sound(sound, pitch, volume), block.getLocation()
                ), TaskContext.BUKKIT, delay * i);
            });
        }


        public void playParticlesAt(@NotNull Crop crop, @NotNull Block block) {
            Sets.forEachWithIndex(config.getKeys(ConfigurationKey.PARTICLES, crop.getName()), (i, particle) -> {
                long delay = config.getLong(ConfigurationKey.PARTICLE_DELAY, crop.getName(), particle);
                int amount = config.getInt(ConfigurationKey.PARTICLE_AMOUNT, crop.getName(), particle);
                double speed = config.getDouble(ConfigurationKey.PARTICLE_SPEED, crop.getName(), particle);

                scheduler.scheduleLater(new ParticleTask(
                        new Particle(particle, amount, speed), block.getLocation()
                ), TaskContext.BUKKIT, delay * i);
            });
        }

    }

}