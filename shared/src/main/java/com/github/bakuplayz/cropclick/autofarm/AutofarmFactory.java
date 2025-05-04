/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

// TODO: Document.
public final class AutofarmFactory {


    @NotNull
    public static Autofarm createDefault(
            @NotNull Player player,
            @NotNull Location crop,
            @NotNull Location container,
            @NotNull Location dispenser
    ) {
        return createPlain(UUID.randomUUID(), player.getUniqueId(), true, crop, container, dispenser);
    }


    @NotNull
    public static Autofarm createWithFarmer(
            @NotNull UUID farmer,
            @NotNull Player player,
            @NotNull Location crop,
            @NotNull Location container,
            @NotNull Location dispenser
    ) {
        return createPlain(farmer, player.getUniqueId(), true, crop, container, dispenser);
    }


    @NotNull
    public static Autofarm createPlain(
            @NotNull UUID farmer,
            @NotNull UUID owner,
            boolean isEnabled,
            @NotNull Location crop,
            @NotNull Location container,
            @NotNull Location dispenser
    ) {
        return new Autofarm(farmer, owner, isEnabled, crop, container, dispenser);
    }

}
