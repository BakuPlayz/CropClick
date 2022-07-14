package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCrop implements Crop {

    @Override
    public boolean hasDrops() {
        return getDrops() != null && !getDrops().isEmpty();
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
        if (!hasDrops()) return;

        for (Drop drop : getDrops()) {
            if (!drop.willDrop()) continue;
            inventory.addItem(drop.toItemStack());
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
        return getHarvestAge() == getCurrentAge(block);
    }

    @Override
    public void replant(@NotNull Block block) {
        block.setType(shouldReplant() ? getClickableType() : Material.AIR);
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
