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

package com.github.bakuplayz.cropclick.listeners.autofarm.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import com.github.bakuplayz.cropclick.events.autofarm.link.AutofarmUnlinkEvent;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import dev.bakuplayz.spigotstore.task.impl.TaskScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.Log.Tag;


/**
 * A listener handling all the unlink {@link Autofarm} events caused by a {@link Autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmUnlinkListener implements Listener {

    private final TaskScheduler taskScheduler;

    private final AutofarmDataService service;

    private final AutofarmManager autofarmManager;


    public AutofarmUnlinkListener(@NotNull CropClick plugin) {
        this.service = plugin.getDataManager().getAutofarmService();
        this.autofarmManager = plugin.getAutofarmManager();
        this.taskScheduler = plugin.getTaskScheduler();
    }


    /**
     * Handles all the {@link Autofarm autofarm} unlink events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmUnlink(@NotNull AutofarmUnlinkEvent event) {
        Autofarm autofarm = event.getAutofarm();

        Log.debug("{0}: Called the unlink event.", Tag.AUTOFARM, autofarm.getShortenedId());

        service.deleteOne(autofarm.getFarmerId().toString()).thenAccept((success) -> {
            if (!success) return;

            taskScheduler.runTask(() -> {
                Log.debug("{0}: Successfully removed autofarm.", Tag.AUTOFARM, autofarm.getShortenedId());
                autofarmManager.getBlocksCache().removeIDs(autofarm);
            }, TaskContext.BUKKIT);
        });
    }

}