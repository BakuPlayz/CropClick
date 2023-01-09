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

package com.github.bakuplayz.cropclick.addons.addon;


import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/residence-1-7-10-up-to-1-19.11480/">Residence</a> addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ResidenceAddon extends Addon {

    private Flags cropFlag;


    public ResidenceAddon(@NotNull CropClick plugin) {
        super(plugin, "Residence");

        registerFlag();
    }


    /**
     * Registers the {@link #cropFlag CropClick flag}.
     */
    private void registerFlag() {
        FlagPermissions.addFlag("cropclick");
        cropFlag = Flags.getFlag("cropclick");
    }


    /**
     * Checks whether the {@link Player provided player} is a member of the region it is in.
     *
     * @param player the player to check.
     *
     * @return true if member, otherwise false.
     */
    public boolean isMemberOfRegion(@NotNull Player player) {
        ResidencePermissions permissions = findPermissionsByLocation(player.getLocation());
        return permissions != null && permissions.playerHas(player, cropFlag, true);
    }


    /**
     * Checks whether the {@link Location provided location} or region has the {@link #cropFlag crop flag}.
     *
     * @param location the location/region to check.
     *
     * @return true if it has, otherwise false.
     */
    public boolean hasRegionFlag(@NotNull Location location) {
        ResidencePermissions permissions = findPermissionsByLocation(location);
        return permissions != null && permissions.has(cropFlag, true);
    }


    /**
     * Finds the {@link ResidencePermissions residence permissions} at the {@link Location provided location}.
     *
     * @param location the location to get the permissions from.
     *
     * @return the found permissions, otherwise null.
     */
    private @Nullable ResidencePermissions findPermissionsByLocation(@NotNull Location location) {
        ResidenceManager manager = Residence.getInstance().getResidenceManager();
        if (manager == null) {
            return null;
        }

        ClaimedResidence claimed = manager.getByLoc(location);
        if (claimed == null) {
            return null;
        }

        ResidencePermissions permissions = claimed.getPermissions();
        if (permissions == null) {
            return null;
        }

        if (permissions.getFlags() == null) {
            return null;
        }

        if (permissions.getFlags().isEmpty()) {
            return null;
        }

        return permissions;
    }

}