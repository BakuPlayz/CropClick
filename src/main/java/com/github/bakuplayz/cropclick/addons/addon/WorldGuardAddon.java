package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.template.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class WorldGuardAddon extends Addon {

    private final WorldGuard worldGuard = WorldGuard.getInstance();
    private final StateFlag FLAG = new StateFlag("cropclick", true);

    public WorldGuardAddon(final @NotNull AddonsConfig addonsConfig) {
        super("WorldGuard", addonsConfig);
        registerFlag();
    }

    public void registerFlag() {
        FlagRegistry registry = worldGuard.getFlagRegistry();
        registry.register(FLAG);
    }

    public boolean regionAllowsPlayer(final @NotNull Player player) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager regionManager = container.get((World) player.getWorld());
        if (regionManager == null) return false;

        Location pLocation = player.getLocation();
        BlockVector3 blockVector = BlockVector3.at(pLocation.getBlockX(), pLocation.getBlockY(), pLocation.getBlockZ());
        if (blockVector == null) return false;

        ApplicableRegionSet foundRegions = regionManager.getApplicableRegions(blockVector);
        for (ProtectedRegion region : foundRegions) {
            if (region == null) continue;
            if (region.getFlag(FLAG) == null) return false;
            if (regionHasMember(region, player)) return true;
        }
        return false;
    }

    private boolean regionHasMember(final @NotNull ProtectedRegion region, final @NotNull Player player) {
        return region.getMembers().contains(player.getUniqueId()) ^ region.getOwners().contains(player.getUniqueId());
    }
}
