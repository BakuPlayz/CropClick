package com.github.bakuplayz.cropclick.listeners.player.destory;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.player.destroy.PlayerDestroyCropEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the {@link Crop crop} destroy events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerDestroyCropListener implements Listener {

    private final CropManager cropManager;
    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final AutofarmManager autofarmManager;

    private final OfflineGrowthAddon growthAddon;


    public PlayerDestroyCropListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.autofarmManager = plugin.getAutofarmManager();
        this.growthAddon = addonManager.getOfflineGrowthAddon();
    }


    /**
     * Handles all the {@link Player player} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtCrop(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!world.allowsPlayers()) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (crop == null) {
            return;
        }

        if (!PermissionUtils.canDestroyCrop(player, crop.getName())) {
            return;
        }

        if (!addonManager.canModifyRegion(player)) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerDestroyCropEvent(crop, block, player)
        );
    }


    /**
     * Handles all the {@link Player player} destroy {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler
    public void onPlayerDestroyCrop(@NotNull PlayerDestroyCropEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (addonManager.isInstalledAndEnabled(growthAddon)) {
            growthAddon.unregisterCrop(block.getLocation());
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(autofarm)) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerUnlinkAutofarmEvent(player, autofarm)
        );
    }

}