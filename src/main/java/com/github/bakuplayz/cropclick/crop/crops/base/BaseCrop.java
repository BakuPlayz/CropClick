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

package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.runnables.particles.Particle;
import com.github.bakuplayz.cropclick.runnables.particles.ParticleRunnable;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.runnables.sounds.SoundRunnable;
import com.github.bakuplayz.cropclick.utils.InventoryUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents the base of a crop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class BaseCrop implements Crop {

    protected final CropsConfig cropsConfig;

    protected final CropConfigSection cropSection;
    protected final SoundConfigSection soundSection;
    protected final ParticleConfigSection particleSection;


    public BaseCrop(@NotNull CropsConfig config) {
        this.particleSection = config.getParticleSection();
        this.soundSection = config.getSoundSection();
        this.cropSection = config.getCropSection();
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
        //noinspection deprecation
        return block.getData();
    }


    /**
     * Checks whether the {@link Crop extending crop} has a drop.
     *
     * @return true if it has, otherwise false.
     */
    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    /**
     * Checks whether the {@link Crop extending crop} should drop at least one drop.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName());
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
     * @param player the player to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    @Override
    public boolean harvest(@NotNull Player player) {
        return harvest(player.getInventory());
    }


    /**
     * Harvests the {@link Crop extending crop}.
     *
     * @param container the container to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    @Override
    public boolean harvest(@NotNull Container container) {
        return harvest(container.getInventory());
    }


    /**
     * Harvests the {@link Crop extending crop}.
     *
     * @param inventory the inventory to add the drops to.
     *
     * @return true if harvested, otherwise false.
     */
    private boolean harvest(@NotNull Inventory inventory) {
        if (!isHarvestable()) {
            return false;
        }
        if (!hasDrop()) {
            return false;
        }

        Drop drop = getDrop();
        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

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
        if (!isHarvestable()) {
            return false;
        }
        return getHarvestAge() <= getCurrentAge(block);
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
     * Replants the {@link Crop extending crop}.
     *
     * @param block the crop block to replant.
     */
    @Override
    public void replant(@NotNull Block block) {
        if (shouldReplant()) {
            block.setType(getClickableType());
        } else {
            block.setType(Material.AIR);
        }
    }


    /**
     * Checks whether the {@link Crop extending crop} should be replanted.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean shouldReplant() {
        return cropSection.shouldReplant(getName());
    }


    /**
     * Checks whether the {@link Crop extending crop} is harvestable at all.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isHarvestable() {
        return cropSection.isHarvestable(getName());
    }


    /**
     * Plays the {@link Sound sounds} assigned to the {@link Crop extending crop}.
     *
     * @param block the crop block.
     */
    @Override
    public void playSounds(@NotNull Block block) {
        SoundRunnable runnable = new SoundRunnable(block);

        for (String sound : soundSection.getSounds(getName())) {
            double delay = soundSection.getDelay(getName(), sound);
            double pitch = soundSection.getPitch(getName(), sound);
            double volume = soundSection.getVolume(getName(), sound);

            runnable.queueSound(
                    sound,
                    volume,
                    pitch,
                    delay
            );
        }

        runnable.run();
    }


    /**
     * Plays the {@link Particle particles} assigned to the {@link Crop extending crop}.
     *
     * @param block the crop block.
     */
    @Override
    public void playParticles(@NotNull Block block) {
        ParticleRunnable runnable = new ParticleRunnable(block);

        for (String particle : particleSection.getParticles(getName())) {
            double delay = particleSection.getDelay(getName(), particle);
            double speed = particleSection.getSpeed(getName(), particle);
            int amount = particleSection.getAmount(getName(), particle);

            runnable.queueParticle(
                    particle,
                    amount,
                    speed,
                    delay
            );
        }

        runnable.run();
    }


    /**
     * Checks whether the {@link Crop extending crop} is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName());
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