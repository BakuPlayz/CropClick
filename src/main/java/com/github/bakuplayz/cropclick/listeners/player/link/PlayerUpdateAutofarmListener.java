package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUpdateAutofarmEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class PlayerUpdateAutofarmListener implements Listener {

    private final AutofarmManager manager;

    public PlayerUpdateAutofarmListener(final @NotNull CropClick plugin) {
        this.manager = plugin.getAutofarmManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmUpdateLink(final @NotNull PlayerUpdateAutofarmEvent event) {
        Autofarm oldAutofarm = event.getOldAutofarm();
        Autofarm newAutofarm = event.getNewAutofarm();
        Player player = event.getPlayer();

        if (!player.hasPermission("cropclick.autofarmer.update")) {
            return;
        }

        if (!manager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        manager.unlinkAutofarm(player, oldAutofarm);
        manager.linkAutofarm(player, newAutofarm);
    }
}
