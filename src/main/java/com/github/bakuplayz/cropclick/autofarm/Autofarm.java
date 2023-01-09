/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.location.LocationTypeAdapter;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.Enableable;
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
 * A class representing an Autofarm.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class Autofarm implements Enableable {

    public final static UUID UNKNOWN_OWNER = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @SerializedName("owner")
    private @NotNull @Setter @Getter UUID ownerID;

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
     * Gets the shortened {@link #farmerID autofarmer ID}.
     *
     * @return the shortened autofarm ID.
     */
    public @NotNull String getShortenedID() {
        return farmerID.toString().substring(0, 7);
    }


    /**
     * Gets the {@link Container container} of the {@link Autofarm autofarm}.
     *
     * @return the autofarm's container.
     */
    public @Nullable Container getContainer() {
        if (container != null) {
            return container;
        }

        if (!isEnabled()) return null;
        if (!isLinked()) return null;

        return this.container = AutofarmUtils.findContainer(
                containerLocation.getBlock()
        );
    }


    /**
     * Checks whether the {@link Autofarm autofarm} has a {@link Container container}.
     *
     * @return true if it has a container, otherwise false.
     */
    public boolean hasContainer() {
        return getContainer() != null;
    }


    /**
     * Checks whether the {@link Autofarm autofarm} is linked.
     *
     * @return true if linked, otherwise false.
     */
    public boolean isLinked() {
        return cropLocation != null
                && containerLocation != null
                && dispenserLocation != null;
    }


    /**
     * Checks whether the {@link Component autofarm components} are present.
     *
     * @param manager the manager to check with.
     *
     * @return true if all are present, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isComponentsPresent(@NotNull AutofarmManager manager) {
        if (!isLinked()) {
            return false;
        }

        if (!manager.isComponent(dispenserLocation.getBlock())) {
            return false;
        }

        if (!manager.isComponent(containerLocation.getBlock())) {
            return false;
        }

        return manager.isComponent(cropLocation.getBlock());
    }

}