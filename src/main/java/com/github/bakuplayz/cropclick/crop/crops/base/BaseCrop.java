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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


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

        Set<String> sounds = getSounds();
        if (sounds == null) {
            return;
        }

        for (String sound : sounds) {
            double delay = cropsConfig.getSoundDelay(getName(), sound);
            double pitch = cropsConfig.getSoundPitch(getName(), sound);
            double volume = cropsConfig.getSoundVolume(getName(), sound);

            runnable.addSound(
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

        Set<String> particles = getParticles();
        if (particles == null) {
            return;
        }

        for (String particle : particles) {
            double delay = cropsConfig.getParticleDelay(getName(), particle);
            double speed = cropsConfig.getParticleSpeed(getName(), particle);
            int amount = cropsConfig.getParticleAmount(getName(), particle);

            runnable.addParticle(
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


    /**
     * Get the names of all the particles in the config file.
     *
     * @return A set of strings.
     */
    private @Nullable Set<String> getParticles() {
        ConfigurationSection section = cropsConfig.getConfig().getConfigurationSection(
                "crops." + getName() + ".particles"
        );
        return section == null ? null : section.getKeys(false);
    }


    /**
     * Get the sounds for the crop.
     *
     * @return A set of strings.
     */
    private @Nullable Set<String> getSounds() {
        ConfigurationSection section = cropsConfig.getConfig().getConfigurationSection(
                "crops." + getName() + ".sounds"
        );
        return section == null ? null : section.getKeys(false);
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}