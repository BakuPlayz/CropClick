package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.Event;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUpdateEvent;
import org.bukkit.Bukkit;
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
public final class AutofarmUpdateListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final AutofarmManager autofarmManager;


    public AutofarmUpdateListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * When triggered it unlinks and 'un-caches' the Autofarm components,
     * and then links the new one and caches that.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmUpdate(@NotNull AutofarmUpdateEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        Event unlinkEvent = new AutofarmUnlinkEvent(
                event.getOldAutofarm()
        );

        Event linkEvent = new AutofarmLinkEvent(
                event.getNewAutofarm()
        );

        if (isDebugging) {
            logger.info(String.format("%s (Autofarm): Called the update event!", event.getOldAutofarm().getShortenedID()));
        }

        Bukkit.getPluginManager().callEvent(unlinkEvent);
        Bukkit.getPluginManager().callEvent(linkEvent);
    }

}