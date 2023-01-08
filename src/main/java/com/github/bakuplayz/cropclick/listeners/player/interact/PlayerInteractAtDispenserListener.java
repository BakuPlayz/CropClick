package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtDispenserEvent;
import com.github.bakuplayz.cropclick.menu.menus.links.DispenserLinkMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the {@link Dispenser} interactions caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtDispenserListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager autofarmManager;


    public PlayerInteractAtDispenserListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} interact at {@link Dispenser dispenser} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtDispenser(@NotNull PlayerInteractAtDispenserEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Autofarm autofarm = autofarmManager.findAutofarm(block);

        if (autofarmManager.isUsable(autofarm)) {
            if (!PermissionUtils.canInteractAtOthersFarm(player, autofarm)) {
                event.setCancelled(true);
                return;
            }

            if (AutofarmUtils.hasCachedID(block)) {
                AutofarmUtils.addCachedID(plugin, autofarm);
            }
        }

        if (plugin.isDebugging()) {
            plugin.getLogger()
                  .info(String.format("%s (Player): Called the interact at dispenser event!", player.getName()));
        }

        new DispenserLinkMenu(
                plugin,
                player,
                block,
                autofarm,
                AutofarmsMenuState.DISPENSER
        ).openMenu();
    }

}