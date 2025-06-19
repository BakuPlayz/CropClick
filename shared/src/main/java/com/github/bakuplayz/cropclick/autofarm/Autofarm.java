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

import com.github.bakuplayz.cropclick.CropPlayer;
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
@Getter
@ToString
public final class Autofarm {

    public final static UUID UNKNOWN_OWNER = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @NotNull
    private final UUID farmerId;

    @NotNull
    private final Location cropLocation;

    @NotNull
    private final Location containerLocation;

    @NotNull
    private final Location dispenserLocation;

    @Nullable
    private final Container container;

    @Setter
    @NotNull
    private UUID ownerId;

    @Setter
    @Accessors(fluent = true)
    private boolean isEnabled;


    private Autofarm(
            @NotNull UUID farmerId,
            @NotNull UUID ownerId,
            boolean isEnabled,
            @NotNull Location cropLocation,
            @NotNull Location containerLocation,
            @NotNull Location dispenserLocation
    ) {
        this.container = Container.fromBlock(containerLocation.getBlock());
        this.dispenserLocation = dispenserLocation;
        this.containerLocation = containerLocation;
        this.cropLocation = cropLocation;
        this.isEnabled = isEnabled;
        this.farmerId = farmerId;
        this.ownerId = ownerId;
    }


    @NotNull
    public static Autofarm fromPlayer(
            @NotNull CropPlayer player,
            @NotNull Location crop,
            @NotNull Location container,
            @NotNull Location dispenser
    ) {
        return createBasic(UUID.randomUUID(), player.getPlayerUUID(), true, crop, container, dispenser);
    }


    @NotNull
    public static Autofarm createBasic(
            @NotNull UUID farmer,
            @NotNull UUID owner,
            boolean isEnabled,
            @NotNull Location crop,
            @NotNull Location container,
            @NotNull Location dispenser
    ) {
        return new Autofarm(farmer, owner, isEnabled, crop, container, dispenser);
    }


    /**
     * Gets the shortened {@link #farmerId autofarm identification}.
     *
     * @return the shortened autofarm identification.
     */
    @NotNull
    public String getShortenedId() {
        return farmerId.toString().substring(0, 8);
    }

}