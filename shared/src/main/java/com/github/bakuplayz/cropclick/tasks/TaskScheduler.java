/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.tasks;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.Log;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskScheduler {

    private final CropClick plugin;

    private final List<Task> tasks;


    public TaskScheduler(@NotNull CropClick plugin) {
        this.tasks = Collections.synchronizedList(new ArrayList<>());
        this.plugin = plugin;
    }


    public void scheduleLater(@NotNull Task task, long ticks) {
        tasks.add(task);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                task.run();
            } catch (Exception e) {
                Log.debug("Failed to run task.", e);
            } finally {
                tasks.remove(task);
            }
        }, ticks);
    }


    public void scheduleRepeatingTask(@NotNull Task task, long start, long ticks) {
        tasks.add(task);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            try {
                task.run();
            } catch (Exception e) {
                Log.debug("Failed to run task.", e);
            } finally {
                tasks.remove(task);
            }
        }, start, ticks);
    }


    public void cleanupTasks() {
        synchronized (tasks) {
            tasks.stream().filter(Task::shouldRunOnCleanup).forEach(task -> {
                try {
                    task.run();
                } catch (Exception e) {
                    Log.debug("Failed to run task.", e);
                }
            });

            tasks.clear();
        }
    }


}
