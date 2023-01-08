package com.github.bakuplayz.cropclick.listeners.player.join;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A listener handling all the {@link Player} join events caused by a {@link Player}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerJoinListener implements Listener {

    private final Logger logger;
    private final boolean isDebugging;

    private final UpdateManager updateManager;


    public PlayerJoinListener(@NotNull CropClick plugin) {
        this.updateManager = plugin.getUpdateManager();
        this.isDebugging = plugin.isDebugging();
        this.logger = plugin.getLogger();
    }


    /**
     * Handles all the {@link Player operator} join events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onOperatorJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) {
            return;
        }

        if (isDebugging) {
            logger.info(String.format("%s (Operator): Called the join event!", player.getName()));
        }
        
        updateManager.sendAlert(player);
    }

}