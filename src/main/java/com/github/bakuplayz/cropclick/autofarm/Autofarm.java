package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.location.LocationTypeAdapter;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class Autofarm {

    @SerializedName("owner")
    private final @NotNull @Getter UUID ownerID;

    @SerializedName("farmer")
    private final @NotNull @Getter UUID farmerID;

    private @Setter @Getter @Accessors(fluent = true) boolean isEnabled;

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


    public Autofarm(@NotNull Player player,
                    @NotNull Location cropLocation,
                    @NotNull DoublyLocation containerLocation,
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


    public Autofarm(@NotNull UUID farmerID,
                    @NotNull UUID ownerID,
                    boolean isEnabled,
                    @NotNull Location cropLocation,
                    @NotNull DoublyLocation containerLocation,
                    @NotNull Location dispenserLocation) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.isEnabled = isEnabled;
        this.farmerID = farmerID;
        this.ownerID = ownerID;
    }


    /**
     * This function returns the first 7 characters of the farmerID, aka shortenedID.
     *
     * @return The first 7 characters of the farmerID.
     */
    public @NotNull String getShortenedID() {
        return farmerID.toString().substring(0, 7);
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

        return this.container = AutofarmUtils.getContainer(
                containerLocation.getBlock()
        );
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


    /**
     * If the farm is linked, check if the dispenser, container, and crop are present.
     *
     * @param manager The AutofarmManager instance that is calling this method.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isComponentsPresent(@NotNull AutofarmManager manager) {
        if (!isLinked()) {
            return false;
        }

        boolean isDispenserPresent = manager.isComponent(dispenserLocation.getBlock());
        if (!isDispenserPresent) {
            return false;
        }

        boolean isContainerPresent = manager.isComponent(containerLocation.getBlock());
        if (!isContainerPresent) {
            return false;
        }

        return manager.isComponent(cropLocation.getBlock());
    }

}