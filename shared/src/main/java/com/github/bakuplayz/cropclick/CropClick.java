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

package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.common.metric.Metrics;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import com.github.bakuplayz.cropclick.configurations.config.*;
import com.github.bakuplayz.cropclick.configurations.converter.AutofarmsConverter;
import com.github.bakuplayz.cropclick.configurations.converter.ConfigConverter;
import com.github.bakuplayz.cropclick.configurations.converter.CropConverter;
import com.github.bakuplayz.cropclick.configurations.converter.PlayerConverter;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.datacontainers.AutofarmDataContainer;
import com.github.bakuplayz.cropclick.datacontainers.WorldDataContainer;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
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
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUpdateAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.plant.PlayerPlantCropListener;
import com.github.bakuplayz.cropclick.permissions.PermissionManager;
import com.github.bakuplayz.cropclick.sql.ConnectionPool;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import com.github.bakuplayz.spigotspin.SpigotSpin;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.FAILED_TO_REGISTER_COMMANDS;


/**
 * The class representing the core of CropClick -- my precious.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropClick extends JavaPlugin {

    /**
     * A singleton plugin instance of CropClick, used *ONLY* to communicate with the {@link CropClickAPI}.
     */
    @Getter(AccessLevel.PUBLIC)
    private static CropClick instance;

    /**
     * A variable used for debugging purposes, when enabled it will, for instance log every event call.
     */
    @Getter
    private final boolean isDebugging = false;

    /**
     * A variable used for getting statistics using bStats.
     */
    @Getter(AccessLevel.PACKAGE)
    private final Metrics metrics = new Metrics(this, 5160);

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
    private AutofarmManager autofarmManager;

    @Getter
    private PermissionManager permissionManager;

    @Getter
    private UsageConfig usageConfig;

    @Getter
    private CropsConfig cropsConfig;

    @Getter
    private AddonsConfig addonsConfig;

    @Getter
    private PlayersConfig playersConfig;

    @Getter
    private LanguageConfig languageConfig;

    @Getter
    private WorldDataContainer worldData;

    @Getter
    private AutofarmDataContainer farmData;

    @Getter
    private ConnectionPool database;

    /**
     * A variable used for resetting only the required items, when a reset is called.
     */
    private boolean isReset;


    /**
     * Stops the execution of {@link CropClick}.
     */
    @Override
    public void onDisable() {
        CropClick.instance = null;

        worldData.save();
        farmData.save();

        Bukkit.getScheduler().cancelTasks(this);
    }


    /**
     * Starts the execution of {@link CropClick}.
     */
    @Override
    public void onEnable() {
        registerConfigs();
        setupConfigs();

        registerStorages();
        setupStorages();

        handleLegacyConfigs();
        registerManagers();

        new SpigotSpin(this);

        CropClick.instance = this;

        Bukkit.getScheduler().runTaskLaterAsynchronously(instance, () -> addonManager.registerAddons(), 0);

        registerWorlds();
        registerCommands();
        registerListeners();
        registerPermissions();
        registerWorlds();
        registerAddons();

        startStoragesSaveInterval();
        startUpdateFetchInterval();
    }


    /**
     * Resets {@link CropClick} (a very expensive compute).
     */
    public void onReset() {
        this.isReset = true;

        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            registerConfigs();
            setupConfigs();

            registerDatabase();
            registerStorages();
            setupStorages();
            startStoragesSaveInterval();

            registerManagers();
        }, 0);
    }


    /**
     * Handles the {@link AbstractConfiguration legacy configurations}.
     */
    private void handleLegacyConfigs() {
        if (usageConfig.isNewFormatVersion()) {
            return;
        }

        CropConverter.makeConversion(this);
        PlayerConverter.makeConversion(this);
        ConfigConverter.makeConversion(this);
        AutofarmsConverter.makeConversion(this);

        usageConfig.updateUsageInfo();
    }


    /**
     * Sets up {@link CropClick CropClick's} configurations.
     */
    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send(getLogger(), "config.yml");
        getConfig().options().copyDefaults(true);
        saveConfig();

        cropsConfig.create();
        usageConfig.create();
        addonsConfig.create();
        playersConfig.create();
        languageConfig.create();
    }


    /**
     * Registers the database (or the SQL connection pool).
     */
    private void registerDatabase() {
        // TODO: Make this work with the database configuration better.
        this.database = new ConnectionPool(null);
    }


    /**
     * Registers all the {@link CropClick} configurations.
     */
    private void registerConfigs() {
        this.usageConfig = new UsageConfig(this);
        this.cropsConfig = new CropsConfig(this);
        this.addonsConfig = new AddonsConfig(this);
        this.playersConfig = new PlayersConfig(this);
        this.languageConfig = new LanguageConfig(this);
    }


    /**
     * Registers all the {@link CropClick} data storages.
     */
    private void registerStorages() {
        this.worldData = new WorldDataContainer();
        this.farmData = new AutofarmDataContainer();
    }


    /**
     * Sets up {@link CropClick CropClick's} data storages.
     */
    public void setupStorages() {
        farmData.create();
        worldData.create();
    }


    /**
     * Starts the saving interval for {@link com.github.bakuplayz.spigotspin.container.DataContainer data containers}.
     */
    private void startStoragesSaveInterval() {
        final int TEN_MINUTES = 10 * 60 * 20; // Written as Minecraft ticks.
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, farmData::save, 0, TEN_MINUTES);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, worldData::save, 0, TEN_MINUTES);
    }


    /**
     * Starts fetching updates from the {@link CropClick CropClick's} update server.
     */
    private void startUpdateFetchInterval() {
        final int THIRTY_MINUTES = 30 * 60 * 20; // Written as Minecraft ticks.
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, updateManager::fetchUpdate, 0, THIRTY_MINUTES);
    }


    /**
     * Registers all the {@link Subcommand commands}.
     */
    private void registerCommands() {
        PluginCommand command = getCommand("cropclick");
        if (command == null) {
            FAILED_TO_REGISTER_COMMANDS.send(getLogger());
            return;
        }

        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);
    }


    /**
     * Registers all the managers.
     */
    private void registerManagers() {
        this.cropManager = new CropManager(this);
        this.worldManager = new WorldManager(this);
        this.addonManager = new AddonManager(this);
        this.autofarmManager = new AutofarmManager(this);

        if (!isReset) {
            this.updateManager = new UpdateManager(this);
            this.commandManager = new CommandManager(this);
            this.permissionManager = new PermissionManager(this);
        }
    }


    /**
     * Registers all the {@link Listener listeners}.
     */
    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerJoinListener(this), this);

        manager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);
        manager.registerEvents(new PlayerInteractAtContainerListener(this), this);
        manager.registerEvents(new PlayerInteractAtDispenserListener(this), this);
        manager.registerEvents(new PlayerInteractAtCropListener(this), this);

        manager.registerEvents(new HarvestCropListener(this), this);
        manager.registerEvents(new PlayerHarvestCropListener(this), this);
        manager.registerEvents(new AutofarmHarvestCropListener(this), this);

        manager.registerEvents(new PlayerPlantCropListener(this), this);
        manager.registerEvents(new PlayerDestroyCropListener(this), this);

        manager.registerEvents(new PlayerUpdateAutofarmListener(this), this);
        manager.registerEvents(new PlayerUnlinkAutofarmListener(this), this);
        manager.registerEvents(new PlayerLinkAutofarmListener(this), this);

        manager.registerEvents(new AutofarmUpdateListener(this), this);
        manager.registerEvents(new AutofarmUnlinkListener(this), this);
        manager.registerEvents(new AutofarmLinkListener(this), this);

        manager.registerEvents(new EntityDestroyAutofarmListener(this), this);
    }


    /**
     * Registers all the {@link Permission permissions}.
     */
    private void registerPermissions() {
        permissionManager.registerPermissions(this);
    }


    /**
     * Registers all the {@link World worlds}.
     */
    private void registerWorlds() {
        worldManager.registerWorlds();
    }


    /**
     * Registers all the {@link AbstractAddon addons}.
     */
    private void registerAddons() {
        addonManager.registerAddons();
    }

}