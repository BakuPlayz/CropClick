package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.runnables.particles.ParticleRunnable;
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
public abstract class Crop implements BaseCrop {

    protected final CropsConfig cropsConfig;

    protected final CropConfigSection cropSection;
    protected final SoundConfigSection soundSection;
    protected final ParticleConfigSection particleSection;


    public Crop(@NotNull CropsConfig cropsConfig) {
        this.particleSection = cropsConfig.getParticleSection();
        this.soundSection = cropsConfig.getSoundSection();
        this.cropSection = cropsConfig.getCropSection();
        this.cropsConfig = cropsConfig;
    }


    /**
     * Gets the current age of the crop.
     *
     * @param block the crop block.
     *
     * @return the current age.
     */
    @Override
    public int getCurrentAge(@NotNull Block block) {
        //TODO: Check if this works.
        return block.getData();
    }


    /**
     * Checks whether the crop has a drop.
     *
     * @return true if it has a drop, otherwise false.
     */
    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    /**
     * Checks whether the crop should drop at least one drop.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName());
    }


    /**
     * Checks whether a crop has a seed.
     *
     * @return true if it has a seed, otherwise false.
     */
    @Override
    public boolean hasSeed() {
        return getSeed() != null;
    }


    /**
     * Checks whether the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param player The player to add the drops to.
     *
     * @return The harvest state.
     */
    @Override
    public boolean harvest(@NotNull Player player) {
        return harvest(player.getInventory());
    }


    /**
     * Checks whether the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param container The container to add the drops to.
     *
     * @return The harvest state.
     */
    @Override
    public boolean harvest(@NotNull Container container) {
        return harvest(container.getInventory());
    }


    /**
     * Checks whether the crop can be harvested, returning
     * true if it successfully harvested it.
     *
     * @param inventory The inventory to add the drops to.
     *
     * @return The harvest state.
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

        if (!InventoryUtils.canFit(inventory, dropItem)) {
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

        BaseSeed seed = getSeed();
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
     * Checks whether the crop is its harvest age.
     *
     * @param block the crop block.
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
     * Checks whether the crop can be harvested by the given player.
     *
     * @param player the player to be checked.
     *
     * @return true if the player can harvest, otherwise false.
     */
    @Override
    public boolean canHarvest(@NotNull Player player) {
        return PermissionUtils.canHarvestCrop(player, getName());
    }


    /**
     * Replants the crop.
     *
     * @param block the crop block.
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
     * Checks whether the crop should be replanted.
     *
     * @return true if it should, otherwise false.
     */
    @Override
    public boolean shouldReplant() {
        return cropSection.shouldReplant(getName());
    }


    /**
     * Checks whether the crop is harvestable at all.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isHarvestable() {
        return cropSection.isHarvestable(getName());
    }


    /**
     * Plays the sounds assigned to the crop.
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
     * Plays the effects assigned to the crop.
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
     * Checks whether the crop is linkable to an {@link Autofarm}.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName());
    }


    /**
     * Checks whether the name of the crop has changed.
     *
     * @return true if changed, otherwise false.
     */
    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}