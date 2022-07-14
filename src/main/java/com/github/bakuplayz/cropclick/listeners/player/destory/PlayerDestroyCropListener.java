package com.github.bakuplayz.cropclick.listeners.player.destory;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.player.destroy.PlayerDestroyCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
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

    /* Step by step:
     * 1. isAir,
     * 2. hasPermission,
     * 3. hasWorldBanished,
     * 4. canModify,
     * 5. isValid
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtil.canDestroyCrop(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessable(world)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        System.out.println("here!!");
        event.setCancelled(true);

        Bukkit.getPluginManager().callEvent(new PlayerDestroyCropEvent(crop, block, player));
    }

    /* Step by step:
     * 1. isCancelled,
     * 2. unregisterCrop,
     * 3. unlinkFarm
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDestroyCrop(@NotNull PlayerDestroyCropEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (addonManager.isPresent(growthAddon)) {
            growthAddon.removeCrop(block.getLocation());
        }

        Autofarm farm = autofarmManager.findAutofarm(block);
        if (autofarmManager.isUsable(farm)) {
            autofarmManager.unlinkAutofarm(player, farm);
        }

    }

}
