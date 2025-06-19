/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configurations.ConfigurationManager;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.database.DatabaseManager;
import com.github.bakuplayz.cropclick.datacontainers.DataServiceManager;
import com.github.bakuplayz.cropclick.listeners.autofarm.harvest.AutofarmHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.autofarm.link.AutofarmLinkListener;
import com.github.bakuplayz.cropclick.listeners.autofarm.link.AutofarmUnlinkListener;
import com.github.bakuplayz.cropclick.listeners.autofarm.link.AutofarmUpdateListener;
import com.github.bakuplayz.cropclick.listeners.entity.EntityDestroyAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.harvest.HarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.destory.PlayerDestroyCropListener;
import com.github.bakuplayz.cropclick.listeners.player.harvest.PlayerHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtContainerListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtCropListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtDispenserListener;
import com.github.bakuplayz.cropclick.listeners.player.join.PlayerJoinListener;
import com.github.bakuplayz.cropclick.listeners.player.leave.PlayerLeaveListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUpdateAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.plant.PlayerPlantCropListener;
import com.github.bakuplayz.cropclick.permissions.PermissionManager;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.world.WorldManager;
import com.github.bakuplayz.spigotspin.SpigotSpin;
import dev.bakuplayz.spigotstore.task.TaskScheduler;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * The class representing the core of CropClick -- my precious.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 2.0.0
 */
public final class CropClick extends JavaPlugin {

    /**
     * A singleton plugin instance of CropClick, used *ONLY* to
     * communicate with the {@link CropClickAPI} and {@link CropPlayer}.
     */
    @Getter(AccessLevel.PACKAGE)
    private static CropClick instance;


    @Getter
    private TaskScheduler taskScheduler;

    @Getter
    private CropManager cropManager;

    @Getter
    private WorldManager worldManager;

    @Getter
    private AddonManager addonManager;

    @Getter
    private UpdateManager updateManager;

    @Getter
    private CommandManager commandManager;

    @Getter
    private DataServiceManager dataManager;

    @Getter
    private DatabaseManager databaseManager;

    @Getter
    private AutofarmManager autofarmManager;

    @Getter
    private ConfigurationManager configManager;

    @Getter
    private PermissionManager permissionManager;


    /**
     * Stops the execution of {@link CropClick}.
     */
    @Override
    public void onDisable() {
        // TODO: Close all connections, and create new ones... ???
        taskScheduler.cleanupTasks();
        CropClick.instance = null;
    }


    /**
     * Starts the execution of {@link CropClick}.
     */
    @Override
    public void onEnable() {
        CropClick.instance = this;

        new SpigotSpin(this);

        registerSchedulers();
        registerManagers();
        registerAddons();
        registerCommands();
        registerListeners();
        registerPermissions();
    }


    /**
     * Registers all the managers.
     */
    private void registerManagers() {
        // DO NOT MOVE THE ORDER OF THESE THREE, WILL CAUSE CRASHES! 😥
        this.configManager = new ConfigurationManager(this);
        this.databaseManager = new DatabaseManager(this);  // dependent on above
        this.dataManager = new DataServiceManager(this); // dependent on above

        this.addonManager = new AddonManager();
        this.cropManager = new CropManager(this);
        this.worldManager = new WorldManager(this);
        this.updateManager = new UpdateManager(this);
        this.commandManager = new CommandManager(this);
        this.autofarmManager = new AutofarmManager(this);
        this.permissionManager = new PermissionManager(this);
    }


    /**
     * Registers all the schedulers.
     */
    private void registerSchedulers() {
        this.taskScheduler = new TaskScheduler(this);
    }


    /**
     * Registers all the {@link Subcommand commands}.
     */
    private void registerCommands() {
        PluginCommand command = getCommand("cropclick");
        if (command == null) {
            Log.severe("Commands failed to register, please reload the server.");
            return;
        }

        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);
    }


    /**
     * Registers all the {@link Listener listeners}.
     */
    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerLeaveListener(), this);
        manager.registerEvents(new PlayerJoinListener(this), this);

        manager.registerEvents(new PlayerInteractAtCropListener(this), this);
        manager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);
        manager.registerEvents(new PlayerInteractAtContainerListener(this), this);
        manager.registerEvents(new PlayerInteractAtDispenserListener(this), this);

        manager.registerEvents(new HarvestCropListener(), this);
        manager.registerEvents(new PlayerHarvestCropListener(this), this);
        manager.registerEvents(new AutofarmHarvestCropListener(this), this);

        manager.registerEvents(new PlayerPlantCropListener(this), this);
        manager.registerEvents(new PlayerDestroyCropListener(this), this);

        manager.registerEvents(new PlayerLinkAutofarmListener(this), this);
        manager.registerEvents(new PlayerUnlinkAutofarmListener(this), this);
        manager.registerEvents(new PlayerUpdateAutofarmListener(this), this);

        manager.registerEvents(new AutofarmUpdateListener(), this);
        manager.registerEvents(new AutofarmLinkListener(this), this);
        manager.registerEvents(new AutofarmUnlinkListener(this), this);

        manager.registerEvents(new EntityDestroyAutofarmListener(this), this);
    }


    /**
     * Registers all the {@link Permission permissions}.
     */
    private void registerPermissions() {
        permissionManager.registerPermissions(this);
    }


    /**
     * Registers all the {@link AbstractAddon addons}.
     */
    private void registerAddons() {
        addonManager.registerAddons(this);
    }

}