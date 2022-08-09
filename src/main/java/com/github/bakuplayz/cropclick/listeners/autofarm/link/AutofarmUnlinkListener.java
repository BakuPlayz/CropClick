package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
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
public final class AutofarmUnlinkListener implements Listener {

    private final AutofarmDataStorage farmData;
    private final AutofarmManager autofarmManager;


    public AutofarmUnlinkListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.farmData = plugin.getFarmData();
    }


    /**
     * When triggered it unlinks and 'un-caches' the Autofarm components.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmUnlink(@NotNull AutofarmUnlinkEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        System.out.println("Autofarm -- Unlinked");

        farmData.removeFarm(event.getAutofarm());
    }

}