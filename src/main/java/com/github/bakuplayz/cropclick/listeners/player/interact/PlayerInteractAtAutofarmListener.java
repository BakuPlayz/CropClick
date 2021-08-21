package com.github.bakuplayz.cropclick.listeners.player.interact;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.interact.PlayerInteractAtAutofarmEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerInteractAtAutofarmListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager manager;

    public PlayerInteractAtAutofarmListener(final @NotNull CropClick plugin) {
        this.manager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }

    public void onPlayerInteractAtBlock(final @NotNull PlayerInteractEvent event) {
        // Call: PlayerInteractAtAutofarmEvent
    }

    public void onPlayerInteractAtAutofarm(final @NotNull PlayerInteractAtAutofarmEvent event) {
        if (!manager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        //check for permissions.
        // cancel event if autofarm is disabled.
    }
}
