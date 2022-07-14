package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtil;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AutofarmManager {

    private final @NotNull CropClick plugin;
    private final @NotNull CropManager cropManager;
    private final @NotNull AutofarmDataStorage farmStorage;

    private final String cacheName = "farmerID";

    public AutofarmManager(@NotNull CropClick plugin) {
        this.farmStorage = plugin.getFarmData();
        this.cropManager = plugin.getCropManager();
        this.plugin = plugin;
    }

    public void linkAutofarm(@NotNull Player player,
                             @NotNull Location cropLocation,
                             @NotNull Location containerLocation,
                             @NotNull Location dispenserLocation) {
        Autofarm farm = new Autofarm(
                player,
                cropLocation,
                containerLocation,
                dispenserLocation
        );
        Bukkit.getPluginManager().callEvent(new PlayerLinkAutofarmEvent(player, farm));
    }

    public void linkAutofarm(@NotNull Player player,
                             @NotNull Autofarm autofarm) {
        linkAutofarm(
                player,
                autofarm.getCropLocation(),
                autofarm.getContainerLocation(),
                autofarm.getDispenserLocation()
        );
    }

    public void unlinkAutofarm(@NotNull Player player,
                               @NotNull Location cropLocation,
                               @NotNull Location containerLocation,
                               @NotNull Location dispenserLocation) {
        Autofarm farm = new Autofarm(
                player,
                cropLocation,
                containerLocation,
                dispenserLocation
        );
        Bukkit.getPluginManager().callEvent(new PlayerUnlinkAutofarmEvent(player, farm));
    }

    public void unlinkAutofarm(@NotNull Player player,
                               @NotNull Autofarm autofarm) {
        unlinkAutofarm(
                player,
                autofarm.getCropLocation(),
                autofarm.getContainerLocation(),
                autofarm.getDispenserLocation()
        );
    }

    // TODO: Should this be Block -> AutofarmBlock?
    public @Nullable Autofarm findAutofarm(@NotNull Block block) {
        if (BlockUtil.isAir(block)) return null;

        if (hasCachedID(block)) {
            String farmerID = getCachedID(block);
            return farmStorage.findFarmById(farmerID);
        }

        if (AutofarmUtil.isDispenser(block)) {
            return farmStorage.findFarmByDispenser(block);
        }

        if (AutofarmUtil.isContainer(block)) {
            return farmStorage.findFarmByContainer(block);
        }

        // TODO: needs to check the block over in case of crop... (and one around...)
        if (AutofarmUtil.isCrop(cropManager, block)) {
            return farmStorage.findFarmByCrop(block);
        }

        return null;
    }

    private String getCachedID(@NotNull Block block) {
        return block.getMetadata(cacheName).get(0).asString();
    }

    private boolean hasCachedID(@NotNull Block block) {
        return block.hasMetadata(cacheName);
    }

    // TODO: find a better name for this?
    public boolean isUsable(Autofarm autofarm) {
        if (autofarm == null) return false;
        if (!autofarm.isLinked()) return false;
        if (!autofarm.isEnabled()) return false;
        if (!autofarm.hasContainer()) return false;
        return isEnabled();
    }

    public boolean isComponent(Block block) {
        if (AutofarmUtil.isDispenser(block)) return true;
        if (AutofarmUtil.isContainer(block)) return true;
        return AutofarmUtil.isCrop(cropManager, block);
    }

    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("autofarm.isEnabled");
    }

}