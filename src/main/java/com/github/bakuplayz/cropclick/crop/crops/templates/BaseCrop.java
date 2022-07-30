package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
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
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class BaseCrop implements Crop {

    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    @Override
    public boolean dropAtLeastOne() {
        return true;
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
        if (!isEnabled()) return;
        if (!hasDrop()) return;

        Drop drop = getDrop();
        if (drop.willDrop()) {
            inventory.addItem(drop.toItemStack());
        }

        if (dropAtLeastOne()) {
            ItemStack item = new ItemUtil(drop.toItemStack())
                    .setAmount(1)
                    .toItemStack();
            inventory.addItem(item);
        }

        if (!hasSeed()) return;

        Seed seed = getSeed();
        if (!seed.isEnabled()) return;
        if (!seed.hasDrop()) return;

        seed.harvest(inventory);
    }


    @Override
    public boolean isHarvestable(@NotNull Block block) {
        if (!isEnabled()) return false;
        return getHarvestAge() <= getCurrentAge(block);
    }


    @Override
    public void replant(@NotNull Block block) {
        if (shouldReplant()) block.setType(Material.AIR);
    }


    @Override
    public boolean shouldReplant() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

}