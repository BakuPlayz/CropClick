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

package com.github.bakuplayz.cropclick.permissions;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractGroundCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractTallCrop;
import com.github.bakuplayz.cropclick.crops.abstracts.AbstractWallCrop;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.bakuplayz.cropclick.permissions.CropPermission.CropPermissionAction;
import static com.github.bakuplayz.cropclick.permissions.CropPermission.CropPermissionBase;


/**
 * A manager controlling the plugin's {@link Permission permissions}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PermissionManager {

    private final PluginManager pluginManager;

    private final List<Subcommand> subcommands;

    private final List<Crop> crops;


    public PermissionManager(@NotNull CropClick plugin) {
        this.subcommands = plugin.getCommandManager().getCommands();
        this.crops = plugin.getCropManager().getRegisteredCrops();
        this.pluginManager = Bukkit.getPluginManager();
    }


    /**
     * Registers all the {@link Permission permissions} for {@link CropClick}.
     *
     * @param plugin the plugin instance.
     */
    public void registerPermissions(@NotNull CropClick plugin) {
        registerCommands();

        /* Runs once the server is done loading in order to register all crops,
         * both CropClick's and other plugins. */
        plugin.getTaskScheduler().scheduleLater(this::registerCrops, TaskContext.BUKKIT, 0);
    }


    /**
     * Registers all the {@link Permission permissions} for each of the {@link Subcommand registred subcommands}.
     */
    private void registerCommands() {
        Permission allPermission = CommandPermission.ALL_COMMANDS;
        pluginManager.addPermission(allPermission);

        subcommands.forEach(command -> {
            Permission child = new CommandPermission(command);
            child.addParent(allPermission, true);
            pluginManager.addPermission(child);
        });

        Permission generalPermission = CommandPermission.GENERAL_COMMAND;
        generalPermission.addParent(allPermission, true);
        pluginManager.addPermission(generalPermission);
    }


    /**
     * Registers all the {@link Permission permissions} for each of the {@link Crop registred crops}.
     */
    private void registerCrops() {
        registerCropType(CropPermissionAction.PLANT);
        registerCropType(CropPermissionAction.HARVEST);
        registerCropType(CropPermissionAction.DESTROY);
    }


    /**
     * Registers all the {@link Permission permissions} for each of the {@link Crop registred crops} provided {@link CropPermissionAction action types}.
     *
     * @param type the action type.
     */
    private void registerCropType(@NotNull CropPermissionAction type) {
        Permission allPermission = type.getAllPermission();
        pluginManager.addPermission(allPermission);

        Permission groundPermission = new CropPermission(CropPermissionBase.GROUND, type);
        Permission tallPermission = new CropPermission(CropPermissionBase.TALL, type);
        Permission wallPermission = new CropPermission(CropPermissionBase.WALL, type);

        crops.forEach(crop -> {
            Permission child = new CropPermission(crop.getName(), type);

            if (crop instanceof AbstractGroundCrop) {
                child.addParent(groundPermission, true);
            } else if (crop instanceof AbstractTallCrop) {
                child.addParent(tallPermission, true);
            } else if (crop instanceof AbstractWallCrop) {
                child.addParent(wallPermission, true);
            } else {
                child.addParent(allPermission, true);
            }

            pluginManager.addPermission(child);
        });

        groundPermission.addParent(allPermission, true);
        tallPermission.addParent(allPermission, true);
        wallPermission.addParent(allPermission, true);
        pluginManager.addPermission(groundPermission);
        pluginManager.addPermission(tallPermission);
        pluginManager.addPermission(wallPermission);
    }

}