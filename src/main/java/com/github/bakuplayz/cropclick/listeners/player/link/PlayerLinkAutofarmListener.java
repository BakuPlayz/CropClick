package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmLinkEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
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
 * @since 1.6.0
 */
public final class PlayerLinkAutofarmListener implements Listener {

    private final CropClick plugin;

    private final CropManager cropManager;


    public PlayerLinkAutofarmListener(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.plugin = plugin;
    }


    /**
     * When a player links an autofarm, call the AutofarmLinkEvent.
     *
     * @param event The event that was called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLinkAutofarm(@NotNull PlayerLinkAutofarmEvent event) {
        if (event.isCancelled()) return;

        Autofarm autofarm = event.getAutofarm();
        Block cropBlock = autofarm.getCropLocation().getBlock();
        Crop crop = AutofarmUtils.getCrop(cropManager, cropBlock);

        assert crop != null; // Only here for the compiler.

        if (!crop.isLinkable()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        if (!PermissionUtils.canLinkFarm(player)) {
            event.setCancelled(true);
            return;
        }

        LanguageAPI.Menu.AUTOFARM_LINK_SUCCESS.send(plugin, player);

        System.out.println("Player -- Linked");

        Bukkit.getPluginManager().callEvent(
                new AutofarmLinkEvent(event.getAutofarm())
        );
    }

}