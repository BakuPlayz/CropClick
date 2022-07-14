package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class Autofarm {

    private final @NotNull @Getter UUID ownerID;
    private final @NotNull @Getter UUID farmerID;

    private @Setter @Getter boolean isEnabled;
    private @Setter @Getter Location cropLocation;
    private @Setter @Getter Location containerLocation;
    private @Setter @Getter Location dispenserLocation;

    public Autofarm(@NotNull Player player,
                    @NotNull Location cropLocation,
                    @NotNull Location containerLocation,
                    @NotNull Location dispenserLocation) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        ownerID = player.getUniqueId();
        farmerID = UUID.randomUUID();
    }


    public @Nullable Container getContainer() {
        if (!isEnabled()) return null;
        if (!isLinked()) return null;

        Block block = containerLocation.getBlock();

        if (block.getState() instanceof Chest)
            return new Container(((Chest) block).getInventory(), ContainerType.CHEST);

        if (block.getState() instanceof DoubleChest)
            return new Container(((DoubleChest) block).getInventory(), ContainerType.DOUBLE_CHEST);

        if (!VersionUtil.supportsShulkers())
            return null;

        if (block.getState() instanceof ShulkerBox)
            return new Container(((ShulkerBox) block).getInventory(), ContainerType.SHULKER);

        return null;
    }

    public boolean hasContainer() {
        return getContainer() != null;
    }

    public boolean isLinked() {
        return cropLocation != null
                && containerLocation != null
                && dispenserLocation != null;
    }

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return farmerID + "{" +
                "ownerId=" + ownerID +
                ", isEnabled=" + isEnabled +
                ", cropLocation=" + cropLocation +
                ", containerLocation=" + containerLocation +
                ", dispenserLocation=" + dispenserLocation +
                "}";
    }

}