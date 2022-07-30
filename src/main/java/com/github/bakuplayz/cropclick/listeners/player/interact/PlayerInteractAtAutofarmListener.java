package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.Event;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtCropEvent;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtDispenserEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtil;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
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
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PlayerInteractAtAutofarmListener implements Listener {

    private final CropManager cropManager;
    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final AutofarmManager autofarmManager;


    public PlayerInteractAtAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
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
        if (event.isCancelled()) return;

        Block block = event.getClickedBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        Action action = event.getAction();
        Player player = event.getPlayer();
        if (!isLeftShift(player, action)) {
            return;
        }

        if (!addonManager.canModify(player)) {
            return;
        }

        if (!PermissionUtil.canInteractAtFarm(player)) {
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessable(world)) {
            return;
        }

        if (!autofarmManager.isEnabled()) {
            return;
        }

        if (autofarmManager.isComponent(block)) {
            event.setCancelled(true);
        }

        //TODO: Should work with doublyLocations
        if (AutofarmUtil.isContainer(block)) {
            Container container = AutofarmUtil.getContainer(block);
            Event containerEvent = new PlayerInteractAtContainerEvent(player, block, container);
            Bukkit.getPluginManager().callEvent(containerEvent);
        }

        if (AutofarmUtil.isDispenser(block)) {
            Dispenser dispenser = AutofarmUtil.getDispenser(block);
            Event dispenserEvent = new PlayerInteractAtDispenserEvent(player, dispenser);
            Bukkit.getPluginManager().callEvent(dispenserEvent);
        }

        if (AutofarmUtil.isCrop(cropManager, block)) {
            Crop crop = AutofarmUtil.getCrop(cropManager, block);
            Event cropEvent = new PlayerInteractAtCropEvent(player, block, crop);
            Bukkit.getPluginManager().callEvent(cropEvent);
        }
    }


    /**
     * Returns true if the player is sneaking and left-clicking a block.
     *
     * @param player The player who clicked the block.
     * @param action The action that was performed.
     *
     * @return A boolean value.
     */
    private boolean isLeftShift(@NotNull Player player, @NotNull Action action) {
        return player.isSneaking() && action == Action.LEFT_CLICK_BLOCK;
    }

}