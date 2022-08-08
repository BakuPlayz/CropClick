package com.github.bakuplayz.cropclick.permissions;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.GroundCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.WallCrop;
import com.github.bakuplayz.cropclick.permissions.command.CommandPermission;
import com.github.bakuplayz.cropclick.permissions.crop.CropPermission;
import com.github.bakuplayz.cropclick.permissions.crop.CropPermissionBase;
import com.github.bakuplayz.cropclick.permissions.crop.CropPermissionType;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PermissionManager {

    private final PluginManager pluginManager;

    private final List<Subcommand> subcommands;
    private final List<Crop> crops;


    public PermissionManager(@NotNull CropClick plugin) {
        this.subcommands = plugin.getCommandManager().getCommands();
        this.crops = plugin.getCropManager().getCrops();
        this.pluginManager = Bukkit.getPluginManager();

        registerCommands();

        // Runs later, in order to make sure all the crop permissions are registered,
        // both CropClick's and other plugins.
        Bukkit.getScheduler().runTaskLater(plugin, this::registerCrops, 0);
    }


    /**
     * It creates a permission for each subcommand, and then adds that permission as a child of the "cropclick.command.*"
     * permission.
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
     * Register all the permissions, for crops that has been registered.
     */
    private void registerCrops() {
        registerCropType(CropPermissionType.PLANT);
        registerCropType(CropPermissionType.HARVEST);
        registerCropType(CropPermissionType.DESTROY);
    }


    /**
     * It creates a permission for each permission type, and then creates a child permission for each permission type and crop.
     */
    private void registerCropType(@NotNull CropPermissionType type) {
        Permission allPermission = type.getAllPermission();
        pluginManager.addPermission(allPermission);

        Permission groundPermission = new CropPermission(CropPermissionBase.GROUND, type);
        Permission tallPermission = new CropPermission(CropPermissionBase.TALL, type);
        Permission wallPermission = new CropPermission(CropPermissionBase.WALL, type);

        crops.forEach(crop -> {
            Permission child = new CropPermission(crop.getName(), type);

            if (crop instanceof GroundCrop) {
                child.addParent(groundPermission, true);
            } else if (crop instanceof TallCrop) {
                child.addParent(tallPermission, true);
            } else if (crop instanceof WallCrop) {
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