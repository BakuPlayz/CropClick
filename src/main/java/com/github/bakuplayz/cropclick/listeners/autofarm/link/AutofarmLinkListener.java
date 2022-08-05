package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmLinkListener implements Listener {

    private final AutofarmDataStorage farmData;
    private final AutofarmManager autofarmManager;


    public AutofarmLinkListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.farmData = plugin.getFarmData();
    }


    /**
     * When triggered it links and caches the Autofarm components.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmLink(@NotNull AutofarmLinkEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        System.out.println("Autofarm -- Linked");

        farmData.addFarm(event.getAutofarm());
    }

}