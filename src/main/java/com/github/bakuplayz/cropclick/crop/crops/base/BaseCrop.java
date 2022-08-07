package com.github.bakuplayz.cropclick.crop.crops.base;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
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
        ItemStack dropItem = drop.toItemStack(hasNameChanged());

        boolean willDrop = drop.willDrop();

        if (willDrop) {
            if (dropItem.getAmount() != 0) {
                inventory.addItem(dropItem);
            }
        }

        if (dropAtLeastOne()) {
            if (!willDrop) {
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
        return cropsConfig.shouldReplantCrop(getName());
    }


    @Override
    public boolean isHarvestable() {
        return cropsConfig.isCropHarvestable(getName());
    }


    @Override
    public void playSounds(@NotNull Block block) {
        getCropsConfig().getCropSounds(getName()).stream()
                        .map(Sound::valueOf)
                        .forEach(sound -> block.getWorld().playSound(block.getLocation(), sound, 1f, 1f));
    }


    @Override
    public void playParticles(@NotNull Block block) {
        getCropsConfig().getCropParticles(getName()).stream()
                        .map(ParticleEffect::valueOf)
                        .forEach(particle -> particle.display(block.getLocation()));
    }


    @Override
    public boolean isLinkable() {
        return cropsConfig.isCropLinkable(getName());
    }


    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}