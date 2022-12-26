package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmUnlinkListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final AutofarmDataStorage farmData;
    private final AutofarmManager autofarmManager;


    public AutofarmUnlinkListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.isDebugging = plugin.isDebugging();
        this.farmData = plugin.getFarmData();
        this.logger = plugin.getLogger();
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

        if (isDebugging) {
            logger.info(String.format(
                    "%s (Autofarm): Called the unlinked event!",
                    event.getAutofarm().getShortenedID()
            ));
        }

        farmData.removeFarm(event.getAutofarm());
    }

}