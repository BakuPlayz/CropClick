package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.autofarm.container.ContainerType;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@ToString
@EqualsAndHashCode
public final class Autofarm {

    @SerializedName("owner")
    private final @NotNull @Getter UUID ownerID;

    @SerializedName("farmer")
    private final @NotNull @Getter UUID farmerID;

    private @Setter @Getter boolean isEnabled;

    @SerializedName("crop")
    @JsonAdapter(LocationTypeAdapter.class)
    private @Setter @Getter Location cropLocation;

    @SerializedName("container")
    @JsonAdapter(LocationTypeAdapter.class)
    private @Setter @Getter Location containerLocation;

    @SerializedName("dispenser")
    @JsonAdapter(LocationTypeAdapter.class)
    private @Setter @Getter Location dispenserLocation;

    private transient Container container;


    public Autofarm(@NotNull Player player,
                    @NotNull Location cropLocation,
                    @NotNull Location containerLocation,
                    @NotNull Location dispenserLocation) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.ownerID = player.getUniqueId();
        this.farmerID = UUID.randomUUID();
        this.isEnabled = true;
    }


    public Autofarm(@NotNull UUID farmerID,
                    @NotNull UUID ownerID,
                    boolean isEnabled,
                    @NotNull Location cropLocation,
                    @NotNull Location containerLocation,
                    @NotNull Location dispenserLocation) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.isEnabled = isEnabled;
        this.farmerID = farmerID;
        this.ownerID = ownerID;
    }


    /**
     * If the container is not null, return it. If the container is null, check if the container is enabled, linked, and if
     * the block is a chest, double chest, or shulker box. If it is, return the container. If it is not, return null.
     *
     * @return A container object.
     */
    public @Nullable Container getContainer() {
        if (container != null) return container;
        if (!isEnabled()) return null;
        if (!isLinked()) return null;

        Block block = containerLocation.getBlock();
        BlockState state = block.getState();

        if (state instanceof DoubleChest) {
            return container = new Container(((DoubleChest) state).getInventory(), ContainerType.DOUBLE_CHEST);
        }

        if (state instanceof Chest) {
            return container = new Container(((Chest) state).getInventory(), ContainerType.CHEST);
        }

        if (!VersionUtil.supportsShulkers()) return null;

        if (state instanceof ShulkerBox) {
            return container = new Container(((ShulkerBox) state).getInventory(), ContainerType.SHULKER);
        }

        return null;
    }


    /**
     * Returns true if the container is not null.
     *
     * @return A boolean value.
     */
    public boolean hasContainer() {
        return getContainer() != null;
    }


    /**
     * If the crop, container, and dispenser locations are not null, then the farm is linked.
     *
     * @return A boolean value.
     */
    public boolean isLinked() {
        return cropLocation != null
                && containerLocation != null
                && dispenserLocation != null;
    }

}