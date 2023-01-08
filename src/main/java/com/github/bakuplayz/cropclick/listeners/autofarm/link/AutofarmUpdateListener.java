package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
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
 * A listener handling all the update {@link Autofarm} events caused by a {@link Autofarm}.
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
     * Handles all the {@link Autofarm autofarm} update link events.
     *
     * @param event the event that was fired.
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
            logger.info(String.format(
                    "%s (Autofarm): Called the update event!",
                    event.getOldAutofarm().getShortenedID())
            );
        }

        Bukkit.getPluginManager().callEvent(unlinkEvent);
        Bukkit.getPluginManager().callEvent(linkEvent);
    }

}