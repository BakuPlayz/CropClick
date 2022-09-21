package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.particles.ParticleRunnable;
import com.github.bakuplayz.cropclick.sounds.SoundRunnable;
import com.github.bakuplayz.cropclick.utils.InventoryUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
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


    public BaseCrop(@NotNull CropsConfig cropsConfig) {
        this.particleSection = cropsConfig.getParticleSection();
        this.soundSection = cropsConfig.getSoundSection();
        this.cropSection = cropsConfig.getCropSection();
        this.cropsConfig = cropsConfig;
    }


    @Override
    public int getCurrentAge(@NotNull Block block) {
        return ((Ageable) block.getBlockData()).getAge();
    }


    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    @Override
    public boolean dropAtLeastOne() {
        return cropSection.shouldDropAtLeastOne(getName());
    }


    @Override
    public boolean hasSeed() {
        return getSeed() != null;
    }


    /**
     * Checks wheaten or not the crop can be harvested, returning
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
     * Checks wheaten or not the crop can be harvested, returning
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
     * Checks wheaten or not the crop can be harvested, returning
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

        boolean isNotZero = dropItem.getAmount() != 0;

        if (drop.willDrop()) {
            if (isNotZero) {
                inventory.addItem(dropItem);
            }
        }

        if (dropAtLeastOne()) {
            if (!isNotZero) {
                dropItem.setAmount(1);
                inventory.addItem(dropItem);
            }
        }

        if (!hasSeed()) {
            return true;
        }

        Seed seed = getSeed();
        if (!seed.isEnabled()) {
            return false;
        }
        if (!seed.hasDrop()) {
            return false;
        }

        return seed.harvest(inventory);
    }


    @Override
    public boolean isHarvestAge(@NotNull Block block) {
        if (!isHarvestable()) {
            return false;
        }
        return getHarvestAge() <= getCurrentAge(block);
    }


    @Override
    public boolean canHarvest(@NotNull Player player) {
        return PermissionUtils.canHarvestCrop(player, getName());
    }


    @Override
    public void replant(@NotNull Block block) {
        if (!shouldReplant()) {
            block.setType(Material.AIR);
        }

        Ageable crop = (Ageable) block.getBlockData();
        crop.setAge(0);
        block.setBlockData(crop);
    }


    @Override
    public boolean shouldReplant() {
        return cropSection.shouldReplant(getName());
    }


    @Override
    public boolean isHarvestable() {
        return cropSection.isHarvestable(getName());
    }


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


    @Override
    public boolean isLinkable() {
        return cropSection.isLinkable(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}