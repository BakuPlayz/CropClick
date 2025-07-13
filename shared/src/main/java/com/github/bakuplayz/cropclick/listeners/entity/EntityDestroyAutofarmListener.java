/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.listeners.entity;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.common.Autofarms;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.Log.Tag;
import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


/**
 * A listener handling all the {@link Entity} destroy {@link Autofarm} events.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class EntityDestroyAutofarmListener implements Listener {

    private final DefaultConfig config;

    private final CropManager cropManager;

    private final AutofarmManager autofarmManager;

    private final TaskScheduler taskScheduler;


    public EntityDestroyAutofarmListener(@NotNull CropClick plugin) {
        this.config = plugin.getConfigManager().getDefaultConfig();
        this.taskScheduler = plugin.getStore().getTaskScheduler();
        this.autofarmManager = plugin.getAutofarmManager();
        this.cropManager = plugin.getCropManager();
    }


    /**
     * Handles all the {@link Entity entity} explode {@link Autofarm autofarm} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplodeAutofarm(@NotNull EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        if (!config.getBoolean(ConfigurationKey.AUTOFARMS_ENABLED)) {
            return;
        }

        List<Block> components = getExplodedComponents(event.blockList());
        for (Block component : components) {
            autofarmManager.getFinder().findByBlock(component).thenAccept(autofarm -> {
                if (autofarm == null) return;

                taskScheduler.runTask(() -> {
                    Log.debug("{0}: Called the destroy autofarm event for autofarm ({1}).", Tag.ENTITY, event.getEntity().getName(), autofarm.getFarmerId());
                    Bukkit.getPluginManager().callEvent(
                            new AutofarmUnlinkEvent(autofarm)
                    );
                }, TaskContext.BUKKIT);
            });
        }
    }


    /**
     * Gets all the exploded autofarm components.
     *
     * @param explodedBlocks the list of exploded blocks.
     *
     * @return the components that exploded.
     */
    @NotNull
    private List<Block> getExplodedComponents(@NotNull List<Block> explodedBlocks) {
        return explodedBlocks.stream()
                       .filter((block) -> Autofarms.isComponent(cropManager, block))
                       .collect(Collectors.toList());
    }

}