package com.github.bakuplayz.cropclick.autofarm;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class Autofarm {

    private final @NotNull @Getter String owner;
    private final @NotNull @Getter String farmer;

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
        this.farmer = UUID.randomUUID().toString();
        this.owner = player.getUniqueId().toString();
    }

    public @Nullable Container getContainer() {
        if (!isEnabled()) return null;
        if (!isLinked()) return null;

        Block block = containerLocation.getBlock();

        if (block.getState() instanceof Chest)
            return new Container(((Chest) block).getInventory(), ContainerType.CHEST);

        if (block.getState() instanceof ShulkerBox)
            return new Container(((ShulkerBox) block).getInventory(), ContainerType.SHULKER);

        return null;
    }

    public boolean isLinked() {
        return cropLocation != null
                && containerLocation != null
                && dispenserLocation != null;
    }

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return farmer + "{" +
                "owner=" + owner +
                ", isEnabled=" + isEnabled +
                ", cropLocation=" + cropLocation +
                ", containerLocation=" + containerLocation +
                ", dispenserLocation=" + dispenserLocation +
                "}";
    }

}