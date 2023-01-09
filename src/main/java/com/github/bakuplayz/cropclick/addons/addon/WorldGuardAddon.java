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

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://dev.bukkit.org/projects/worldguard">WorldGuard</a> addon.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldGuardAddon extends Addon {

    private final StateFlag cropFlag;
    private final WorldGuard worldGuard;


    public WorldGuardAddon(@NotNull CropClick plugin) {
        super(plugin, "WorldGuard");
        this.worldGuard = WorldGuard.getInstance();
        this.cropFlag = new StateFlag("cropclick", true);

        registerFlag();
    }


    /**
     * Registers the {@link #cropFlag CropClick flag}.
     */
    private void registerFlag() {
        FlagRegistry registry = worldGuard.getFlagRegistry();
        registry.register(cropFlag);
    }


    /**
     * Checks whether a region allows the {@link Player provided player}.
     *
     * @param player the player to check.
     *
     * @return true if allowed, otherwise false.
     */
    public boolean regionAllowsPlayer(@NotNull Player player) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager manager = container.get((World) player.getWorld());
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
            if (region.getFlag(cropFlag) == null) return false;
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
    private boolean isMemberOfRegion(@NotNull ProtectedRegion region, @NotNull Player player) {
        DefaultDomain members = region.getMembers();
        DefaultDomain owners = region.getOwners();
        return members.contains(player.getUniqueId()) || owners.contains(player.getUniqueId());
    }

}