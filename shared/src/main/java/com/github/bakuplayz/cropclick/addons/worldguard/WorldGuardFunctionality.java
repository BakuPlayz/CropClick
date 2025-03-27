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
package com.github.bakuplayz.cropclick.addons.worldguard;

import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.addons.AddonFunctionality;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class WorldGuardFunctionality implements AddonFunctionality, LoggerContext {

    public final static String FLAG_NAME = "cropclick";

    private final static StateFlag CROP_FLAG = new StateFlag(FLAG_NAME, true);

    private final WorldGuard worldGuard;


    public WorldGuardFunctionality() {
        this.worldGuard = WorldGuard.getInstance();
    }


    /**
     * Registers the {@link #CROP_FLAG CropClick flag}.
     */
    void registerFlag() {
        try {
            if (worldGuard.getFlagRegistry().get(FLAG_NAME) == null) {
                worldGuard.getFlagRegistry().register(CROP_FLAG);
            }
        } catch (FlagConflictException e) {
            getLogger().log(Level.WARNING, "WorldGuard failed to register the CropClick flag, expect potential issues.", e);
        }
    }


    /**
     * Checks whether a region allows the {@link Player provided player}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     */
    public boolean regionAllowsPlayer(@NotNull OfflinePlayer player) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager manager = container.get((World) player.getPlayer().getWorld());
        if (manager == null) {
            return false;
        }

        Location location = player.getLocation();
        BlockVector3 position = BlockVector3.at(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
        if (position == null) {
            return false;
        }

        ApplicableRegionSet foundRegions = manager.getApplicableRegions(position);
        for (ProtectedRegion region : foundRegions) {
            if (region == null) continue;
            if (region.getFlag(CROP_FLAG) == null) return false;
            if (isMemberOfRegion(region, player)) return true;
        }
        return false;
    }


    /**
     * Checks whether the {@link Player provided player} is a member of the {@link ProtectedRegion provided region}.
     *
     * @param region the region to check.
     * @param player the player to check.
     *
     * @return true if member, otherwise false.
     */
    private boolean isMemberOfRegion(@NotNull ProtectedRegion region, @NotNull OfflinePlayer player) {
        DefaultDomain owners = region.getOwners();
        DefaultDomain members = region.getMembers();
        return members.contains(player.getUniqueId()) || owners.contains(player.getUniqueId());
    }

}
