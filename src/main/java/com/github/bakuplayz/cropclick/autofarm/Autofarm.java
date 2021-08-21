package com.github.bakuplayz.cropclick.autofarm;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class Autofarm {

    private final @NotNull @Getter UUID ownerUUID;
    private final @NotNull @Getter String farmerID;

    private @Setter @Getter boolean isEnabled;
    private @Setter @Getter Location cropLocation;
    private @Setter @Getter Location containerLocation;
    private @Setter @Getter Location dispenserLocation;

    public Autofarm(final @NotNull Player player,
                    final @NotNull Location cropLocation,
                    final @NotNull Location containerLocation,
                    final @NotNull Location dispenserLocation) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.ownerUUID = player.getUniqueId();
        this.farmerID = UUID.randomUUID().toString();
    }


    public @Nullable Inventory getContainer() {
        Block block = containerLocation.getBlock();
        if (block.getState() instanceof Chest) return (((Chest) block).getInventory());
        if (block.getState() instanceof ShulkerBox) return (((ShulkerBox) block).getInventory());
        return null;
    }

    public boolean isLinked() {
        if (containerLocation == null) return false;
        if (cropLocation == null) return false;
        return dispenserLocation != null;
    }

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return farmerID + "{" +
                "ownerUUID=" + ownerUUID +
                ", isEnabled=" + isEnabled +
                ", cropLocation=" + cropLocation +
                ", containerLocation=" + containerLocation +
                ", dispenserLocation=" + dispenserLocation + "}";
    }
}
