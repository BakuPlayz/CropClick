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

package com.github.bakuplayz.cropclick.crops.abstracts;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.InventoryUtils;
import com.github.bakuplayz.cropclick.common.PermissionUtils;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropAgeComponent;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.cropclick.mappers.ComponentMapper;
import com.github.bakuplayz.cropclick.runnables.particles.Particle;
import com.github.bakuplayz.cropclick.runnables.particles.ParticlePlayQueue;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.runnables.sounds.SoundPlayQueue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


/**
 * A class that represents the base of a crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class AbstractCrop implements Crop {

    protected final CropsConfig cropsConfig;

    private final CropAgeComponent ageComponent;


    public AbstractCrop(@NotNull CropsConfig config) {
        this.ageComponent = ComponentMapper.getAge();
        this.cropsConfig = config;
    }


    /**
     * Gets the current age of the {@link Crop extending crop}.
     *
     * @param block the crop block.
     *
     * @return the age of the crop.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        return ageComponent.get(block);
    }


    /**
     * Checks whether the {@link Crop extending crop} should drop at least one drop.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropsConfig.get(ConfigurationKey.CROP_DROP_AT_LEAST_ONE, getName());
    }


    /**
     * Checks whether the {@link Crop extending crop} has a seed.
     *
     * @return true if it has, otherwise false.
     */
    @Override
    public boolean hasSeed() {
        return getSeed() != null;
    }


    /**
     * Harvests the {@link Crop extending crop}.
     *
     * @param container the container to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    @Override
    public boolean harvest(@NotNull ContainerComponent container) {
        if (!isHarvestable()) {
            return false;
        }

        Drop drop = getDrop();
        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

        Inventory inventory = container.getInventory();
        if (!InventoryUtils.canContain(inventory, dropItem)) {
            return false;
        }

        if (drop.willDrop()) {
            if (dropItem.getAmount() != 0) {
                inventory.addItem(dropItem);
            }
        }

        if (dropAtLeastOne()) {
            if (dropItem.getAmount() == 0) {
                dropItem.setAmount(1);
                inventory.addItem(dropItem);
            }
        }

        if (!hasSeed()) {
            return true;
        }

        Seed seed = getSeed();
        if (seed == null) {
            return false;
        }
        if (!seed.isEnabled()) {
            return false;
        }
        if (!seed.hasDrop()) {
            return false;
        }

        return seed.harvest(inventory);
    }


    /**
     * Checks whether the {@link Crop extending crop} is at its harvest age.
     *
     * @param block the crop block to check.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isHarvestAge(@NotNull Block block) {
        return isHarvestable() && getHarvestAge() <= getCurrentAge(block);
    }


    /**
     * Checks whether the {@link Player provided player} can harvest the {@link Crop extending crop}.
     *
     * @param player the player to be checked.
     *
     * @return true if it can, otherwise false.
     */
    @Override
    public boolean canHarvest(@NotNull Player player) {
        return PermissionUtils.canHarvestCrop(player, getName());
    }


    /**
     * Checks whether the {@link Crop extending crop} is harvestable at all.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isHarvestable() {
        return cropsConfig.get(ConfigurationKey.CROP_HARVESTABLE, getName());
    }


    /**
     * Checks whether the {@link Crop extending crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isLinkable() {
        return cropsConfig.get(ConfigurationKey.CROP_LINKABLE, getName());
    }


    /**
     * Replants the {@link Crop extending crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        if (!shouldReplant()) {
            block.setType(Material.AIR);
            return;
        }

        ageComponent.set(block, 0);
    }


    /**
     * Checks whether the {@link Crop extending crop} should be replanted.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean shouldReplant() {
        return cropsConfig.get(ConfigurationKey.CROP_SHOULD_REPLANT, getName());
    }


    /**
     * Plays the {@link Sound sounds} assigned to the {@link Crop extending crop}.
     *
     * @param block the crop block.
     */
    @Override
    public void playSounds(@NotNull Block block) {
        Set<String> sounds = cropsConfig.getKeys(ConfigurationKey.SOUNDS, getName());
        SoundPlayQueue queue = new SoundPlayQueue(block);

        for (String sound : sounds) {
            long delay = cropsConfig.get(ConfigurationKey.SOUND_DELAY, getName(), sound);
            double pitch = cropsConfig.get(ConfigurationKey.SOUND_PITCH, getName(), sound);
            double volume = cropsConfig.get(ConfigurationKey.SOUND_VOLUME, getName(), sound);

            queue.queueSound(
                    sound,
                    volume,
                    pitch,
                    delay
            );
        }

        queue.run();
    }


    /**
     * Plays the {@link Particle particles} assigned to the {@link Crop extending crop}.
     *
     * @param block the crop block.
     */
    @Override
    public void playParticles(@NotNull Block block) {
        Set<String> particles = cropsConfig.getKeys(ConfigurationKey.PARTICLES, getName());
        ParticlePlayQueue queue = new ParticlePlayQueue(block);

        for (String particle : particles) {
            long delay = cropsConfig.get(ConfigurationKey.PARTICLE_DELAY, getName(), particle);
            int amount = cropsConfig.get(ConfigurationKey.PARTICLE_AMOUNT, getName(), particle);
            double speed = cropsConfig.get(ConfigurationKey.PARTICLE_SPEED, getName(), particle);

            queue.queueParticle(
                    particle,
                    amount,
                    speed,
                    delay
            );
        }

        queue.run();
    }


    /**
     * Checks whether the name of the {@link Crop extending crop} has changed.
     *
     * @return true if it has, otherwise false.
     */
    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}