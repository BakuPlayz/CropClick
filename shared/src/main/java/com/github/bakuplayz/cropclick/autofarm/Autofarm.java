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

import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.AutofarmUtils;
import com.github.bakuplayz.cropclick.common.location.DoublyLocation;
import com.github.bakuplayz.cropclick.common.location.LocationTypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bukkit.Location;
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
public final class Autofarm {


    public final static UUID UNKNOWN_OWNER = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Getter
    @NotNull
    @SerializedName("farmer")
    private final UUID farmerId;

    @NotNull
    @Setter
    @Getter
    @SerializedName("owner")
    private UUID ownerId;

    @Setter
    @Getter
    @Accessors(fluent = true)
    private boolean isEnabled;

    @Setter
    @Getter
    @SerializedName("crop")
    @JsonAdapter(LocationTypeAdapter.class)
    private Location cropLocation;

    @Setter
    @Getter
    @SerializedName("container")
    @JsonAdapter(LocationTypeAdapter.class)
    private Location containerLocation;

    @Setter
    @Getter
    @SerializedName("dispenser")
    @JsonAdapter(LocationTypeAdapter.class)
    private Location dispenserLocation;

    private transient ContainerComponent container;


    public Autofarm(@NotNull UUID farmerId,
                    @NotNull UUID ownerId,
                    boolean isEnabled,
                    @NotNull Location cropLocation,
                    @NotNull Location containerLocation,
                    @NotNull Location dispenserLocation
    ) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.isEnabled = isEnabled;
        this.farmerId = farmerId;
        this.ownerId = ownerId;
    }


    public Autofarm(@NotNull UUID farmerId,
                    @NotNull UUID ownerId,
                    boolean isEnabled,
                    @NotNull Location cropLocation,
                    @NotNull DoublyLocation containerLocation,
                    @NotNull Location dispenserLocation
    ) {
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.isEnabled = isEnabled;
        this.farmerId = farmerId;
        this.ownerId = ownerId;
    }


    /**
     * Gets the shortened {@link #farmerId autofarmer ID}.
     *
     * @return the shortened autofarm ID.
     */
    @NotNull
    public String getShortenedId() {
        return farmerId.toString().substring(0, 7);
    }


    /**
     * Gets the {@link #container} iff present, otherwise uses
     * the Autofarm Manager to find it via its block location.
     */
    @Nullable
    public ContainerComponent getContainer() {
        if (container == null) {
            container = AutofarmUtils.findContainer(containerLocation.getBlock());
        }

        return container;
    }


    /**
     * Checks whether the {@link Autofarm autofarm} is linked.
     *
     * @return true if linked, otherwise false.
     */
    public boolean isLinked() {
        return containerLocation != null && dispenserLocation != null && cropLocation != null;
    }


    /**
     * TODO: Remove?
     * * Checks whether the autofarm components are present.
     *
     * @param manager the manager to check with.
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