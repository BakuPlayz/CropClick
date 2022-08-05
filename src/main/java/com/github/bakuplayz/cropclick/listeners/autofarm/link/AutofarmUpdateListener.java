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


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmUpdateListener implements Listener {

    private final AutofarmManager autofarmManager;


    public AutofarmUpdateListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
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

        System.out.println("Autofarm -- Update");

        Bukkit.getPluginManager().callEvent(unlinkEvent);
        Bukkit.getPluginManager().callEvent(linkEvent);
    }

}