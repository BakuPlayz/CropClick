package com.github.bakuplayz.cropclick.crop.crops.templates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class TallCrop extends VanillaCrop {

    @Override
    public int getCurrentAge(final @NotNull Block block) {
        int tallness = 0;
        Location location = block.getLocation();
        for (int y = location.getBlockY(); y <= 256; y++) {
            if (location.getBlock() == null) break;
            if (location.getBlock().getType() == Material.AIR) break;
            location.setY(y);
            tallness++;
        }
        return tallness;
    }

    @Override
    public int getHarvestAge() {
        return 2;
    }
}
