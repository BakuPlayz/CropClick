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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
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
     * It registers the flag we created earlier.
     */
    private void registerFlag() {
        FlagRegistry registry = worldGuard.getFlagRegistry();
        registry.register(cropFlag);
    }


    /**
     * If the player is in a region that has the flag set to allow, and the player is a member of that region, then return
     * true.
     *
     * @param player The player to check.
     *
     * @return A boolean value.
     */
    public boolean regionAllowsPlayer(@NotNull Player player) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager manager = container.get((World) player.getWorld());
        if (manager == null) return false;

        Location location = player.getLocation();
        BlockVector3 position = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (position == null) return false;

        ApplicableRegionSet foundRegions = manager.getApplicableRegions(position);
        for (ProtectedRegion region : foundRegions) {
            if (region == null) continue;
            if (region.getFlag(cropFlag) == null) return false;
            if (regionHasMember(region, player)) return true;
        }
        return false;
    }


    /**
     * Returns true if the player is a member of the region, false otherwise.
     *
     * @param region The region you want to check.
     * @param player The player to check if they are a member of the region.
     *
     * @return A boolean value.
     */
    private boolean regionHasMember(@NotNull ProtectedRegion region, @NotNull Player player) {
        DefaultDomain members = region.getMembers();
        DefaultDomain owners = region.getOwners();
        return members.contains(player.getUniqueId()) || owners.contains(player.getUniqueId());
    }

}