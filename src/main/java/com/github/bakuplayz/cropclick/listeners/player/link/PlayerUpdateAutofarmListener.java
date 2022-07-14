package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUpdateAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
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

    private final WorldManager worldManager;
    private final AddonManager addonManager;
    private final AutofarmManager autofarmManager;

    public PlayerUpdateAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.addonManager = plugin.getAddonManager();
        this.worldManager = plugin.getWorldManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmUpdateLink(@NotNull PlayerUpdateAutofarmEvent event) {
        if (event.isCancelled()) return;

        Autofarm oldAutofarm = event.getOldAutofarm();
        Autofarm newAutofarm = event.getNewAutofarm();
        Player player = event.getPlayer();

        if (!PermissionUtil.canUpdateLink(player)) {
            event.setCancelled(true);
            return;
        }

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        if (!autofarmManager.isUsable(oldAutofarm)) {
            event.setCancelled(true);
            return;
        }

        if (!autofarmManager.isUsable(newAutofarm)) {
            event.setCancelled(true);
            return;
        }

        if (!addonManager.canModify(player)) {
            event.setCancelled(true);
            return;
        }

        FarmWorld world = worldManager.findByPlayer(player);
        if (!worldManager.isAccessable(world)) {
            event.setCancelled(true);
            return;
        }

        autofarmManager.unlinkAutofarm(player, oldAutofarm);
        autofarmManager.linkAutofarm(player, newAutofarm);
    }

}
