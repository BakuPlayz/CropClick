package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
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

import java.util.logging.Logger;


/**
 * A listener handling all the unlinking {@link Autofarm} events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerUnlinkAutofarmListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final AutofarmManager autofarmManager;


    public PlayerUnlinkAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all {@link Player players} interacting at a {@link Autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractWithFarm(@NotNull BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtils.canUnlinkFarm(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(autofarm)) {
            return;
        }

        if (!PermissionUtils.canUnlinkOthersFarm(player, autofarm)) {
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerUnlinkAutofarmEvent(player, autofarm)
        );
    }


    /**
     * Handles all {@link Player players} unlinking an {@link Autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerUnlinkAutofarm(@NotNull PlayerUnlinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (!PermissionUtils.canUnlinkFarm(player)) {
            event.setCancelled(true);
            return;
        }

        if (isDebugging) {
            logger.info(String.format("%s (Player): Called the unlinked event!", player.getName()));
        }

        Bukkit.getPluginManager().callEvent(
                new AutofarmUnlinkEvent(event.getAutofarm())
        );
    }

}