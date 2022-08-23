package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtCropEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtDispenserEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.EventUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtAutofarmListener implements Listener {

    private final CropManager cropManager;
    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final PlayersConfig playersConfig;
    private final AutofarmManager autofarmManager;


    public PlayerInteractAtAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.playersConfig = plugin.getPlayersConfig();
        this.addonManager = plugin.getAddonManager();
        this.worldManager = plugin.getWorldManager();
        this.cropManager = plugin.getCropManager();
    }


    /**
     * If the player is left-clicking a block, and the block is a crop, container, or dispenser, then call the appropriate
     * event.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtBlock(@NotNull PlayerInteractEvent event) {
        if (!EventUtils.isMainHand(event)) {
            return;
        }

        Block block = event.getClickedBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        Player player = event.getPlayer();
        if (!EventUtils.isLeftShift(player, action)) {
            return;
        }

        if (!playersConfig.isEnabled(player)) {
            return;
        }

        if (!autofarmManager.isEnabled()) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        if (!PermissionUtils.canInteractAtFarm(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!world.allowsPlayers()) {
            return;
        }

        if (autofarmManager.isComponent(block)) {
            event.setCancelled(true);
        }

        if (AutofarmUtils.isContainer(block)) {
            Container container = AutofarmUtils.getContainer(block);
            Event containerEvent = new PlayerInteractAtContainerEvent(player, block, container);
            Bukkit.getPluginManager().callEvent(containerEvent);
        }

        if (AutofarmUtils.isDispenser(block)) {
            Dispenser dispenser = AutofarmUtils.getDispenser(block);
            Event dispenserEvent = new PlayerInteractAtDispenserEvent(player, dispenser);
            Bukkit.getPluginManager().callEvent(dispenserEvent);
        }

        if (AutofarmUtils.isCrop(cropManager, block)) {
            Crop crop = AutofarmUtils.getCrop(cropManager, block);
            Event cropEvent = new PlayerInteractAtCropEvent(player, block, crop);
            Bukkit.getPluginManager().callEvent(cropEvent);
        }
    }

}