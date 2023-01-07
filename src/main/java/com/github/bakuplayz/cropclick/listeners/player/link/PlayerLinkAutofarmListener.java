package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;


/**
 * A listener handling all the {@link Autofarm} linking events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerLinkAutofarmListener implements Listener {

    private final CropClick plugin;


    public PlayerLinkAutofarmListener(@NotNull CropClick plugin) {
        this.plugin = plugin;
    }


    /**
     * Handles all the {@link Player player} link {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLinkAutofarm(@NotNull PlayerLinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (!PermissionUtils.canLinkFarm(player)) {
            event.setCancelled(true);
            return;
        }

        LanguageAPI.Menu.LINK_ACTION_SUCCESS.send(plugin, player);

        if (plugin.isDebugging()) {
            plugin.getLogger().info(String.format("%s (Player): Called the link event!", player.getName()));
        }

        Bukkit.getPluginManager().callEvent(
                new AutofarmLinkEvent(event.getAutofarm())
        );
    }

}