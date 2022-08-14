package com.github.bakuplayz.cropclick.listeners.entity;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class EntityDestroyAutofarmListener implements Listener {

    private final AutofarmManager autofarmManager;


    public EntityDestroyAutofarmListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
    }


    /**
     * If the autofarms are enabled, and within the explosion, then remove the autofarm from the farm data.
     *
     * @param event The event that is being called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplodeBlock(@NotNull EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        List<Block> explodedBlocks = event.blockList();
        List<Block> explodedComponents = getExplodedComponents(explodedBlocks);

        for (Block component : explodedComponents) {

            Autofarm autofarm = autofarmManager.findAutofarm(component);

            if (autofarm == null) {
                continue;
            }

            Bukkit.getPluginManager().callEvent(
                    new AutofarmUnlinkEvent(autofarm)
            );
        }
    }


    /**
     * Get all the exploded blocks that are autofarm components.
     *
     * @param explodedBlocks The list of blocks that were exploded.
     *
     * @return A list of blocks that are autofarm components.
     */
    private @NotNull List<Block> getExplodedComponents(@NotNull List<Block> explodedBlocks) {
        return explodedBlocks.stream()
                             .filter(autofarmManager::isComponent)
                             .collect(Collectors.toList());
    }

}