package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.particles.ParticleRunnable;
import com.github.bakuplayz.cropclick.sounds.SoundRunnable;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    protected final @Getter CropsConfig cropsConfig;


    public BaseCrop(@NotNull CropsConfig cropsConfig) {
        this.cropsConfig = cropsConfig;
    }


    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    @Override
    public boolean dropAtLeastOne() {
        return cropsConfig.shouldDropAtLeastOne(getName());
    }


    @Override
    public boolean hasSeed() {
        return getSeed() != null;
    }


    @Override
    public void harvest(@NotNull Player player) {
        harvest(player.getInventory());
        player.updateInventory();
    }


    @Override
    public void harvest(@NotNull Container container) {
        harvest(container.getInventory());
    }


    private void harvest(@NotNull Inventory inventory) {
        if (!isHarvestable()) return;
        if (!hasDrop()) return;

        Drop drop = getDrop();
        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

        boolean hasDropped = dropItem.getAmount() != 0;

        if (drop.willDrop()) {
            if (hasDropped) {
                inventory.addItem(dropItem);
            }
        }

        if (dropAtLeastOne()) {
            if (!hasDropped) {
                dropItem.setAmount(1);
                inventory.addItem(dropItem);
            }
        }

        if (!hasSeed()) return;

        Seed seed = getSeed();
        if (!seed.isEnabled()) return;
        if (!seed.hasDrop()) return;

        seed.harvest(inventory);
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
        if (shouldReplant()) {
            block.setType(getClickableType());
        } else {
            block.setType(Material.AIR);
        }
    }


    @Override
    public boolean shouldReplant() {
        return cropsConfig.shouldCropReplant(getName());
    }


    @Override
    public boolean isHarvestable() {
        return cropsConfig.isCropHarvestable(getName());
    }


    @Override
    public void playSounds(@NotNull Block block) {
        SoundRunnable runnable = new SoundRunnable(block);


        for (String sound : cropsConfig.getSounds(getName())) {
            double delay = cropsConfig.getSoundDelay(getName(), sound);
            double pitch = cropsConfig.getSoundPitch(getName(), sound);
            double volume = cropsConfig.getSoundVolume(getName(), sound);

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

        for (String particle : cropsConfig.getParticles(getName())) {
            double delay = cropsConfig.getParticleDelay(getName(), particle);
            double speed = cropsConfig.getParticleSpeed(getName(), particle);
            int amount = cropsConfig.getParticleAmount(getName(), particle);

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
        return cropsConfig.isCropLinkable(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}