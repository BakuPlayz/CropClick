package com.github.bakuplayz.cropclick.listeners.player.destory;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.OfflineGrowthAddon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
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
 * A listener handling all the {@link BaseCrop crop} destroy events caused by a {@link Player}.
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
     * If a player breaks a block, and the block is a plantable surface, and the player has permission to destroy crops,
     * and the world is accessible, and the player can modify the world, and the block is a crop, then call the
     * PlayerDestroyCropEvent.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithCrop(@NotNull BlockBreakEvent event) {
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

        BaseCrop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        if (!PermissionUtils.canDestroyCrop(player, crop.getName())) {
            return;
        }

        if (!addonManager.canModify(player)) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerDestroyCropEvent(crop, block, player)
        );
    }


    /**
     * Removing the crop from offline growth and unlinks the crop's linked autofarm, if present.
     *
     * @param event The event that was called.
     */
    @EventHandler
    public void onPlayerDestroyCrop(@NotNull PlayerDestroyCropEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (addonManager.isPresentAndEnabled(growthAddon)) {
            growthAddon.removeCrop(block.getLocation());
        }

        Autofarm farm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(farm)) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerUnlinkAutofarmEvent(player, farm)
        );
    }

}