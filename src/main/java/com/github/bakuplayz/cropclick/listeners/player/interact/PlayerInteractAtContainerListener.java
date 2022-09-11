package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtContainerEvent;
import com.github.bakuplayz.cropclick.menu.menus.links.ContainerLinkMenu;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerInteractAtContainerListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager autofarmManager;


    public PlayerInteractAtContainerListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }


    /**
     * If a player interacts with a container, open a menu for them.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractAtContainer(@NotNull PlayerInteractAtContainerEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Autofarm autofarm = autofarmManager.findAutofarm(block);

        if (autofarmManager.isUsable(autofarm)) {
            if (!PermissionUtils.canInteractAtOthersFarm(player, autofarm.getOwnerID())) {
                event.setCancelled(true);
                return;
            }

            if (AutofarmUtils.componentHasMeta(block)) {
                AutofarmUtils.addMeta(plugin, autofarm);
            }
        }

        new ContainerLinkMenu(
                plugin,
                player,
                block,
                autofarm
        ).open();
    }

}