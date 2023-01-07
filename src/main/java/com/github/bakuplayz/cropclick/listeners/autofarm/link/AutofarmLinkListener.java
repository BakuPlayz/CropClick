package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A listener handling all the link {@link Autofarm} events caused by a {@link Autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmLinkListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final AutofarmDataStorage farmData;
    private final AutofarmManager autofarmManager;


    public AutofarmLinkListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.isDebugging = plugin.isDebugging();
        this.farmData = plugin.getFarmData();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all the {@link Autofarm autofarm} link events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmLink(@NotNull AutofarmLinkEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        if (isDebugging) {
            logger.info(String.format(
                    "%s (Autofarm): Called the link event!",
                    event.getAutofarm().getShortenedID())
            );
        }

        farmData.linkFarm(event.getAutofarm());
    }

}