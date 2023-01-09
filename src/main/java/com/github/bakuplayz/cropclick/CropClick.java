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
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.configs.config.*;
import com.github.bakuplayz.cropclick.configs.converter.AutofarmsConverter;
import com.github.bakuplayz.cropclick.configs.converter.ConfigConverter;
import com.github.bakuplayz.cropclick.configs.converter.CropConverter;
import com.github.bakuplayz.cropclick.configs.converter.PlayerConverter;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.datastorages.datastorage.WorldDataStorage;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.listeners.MenuListener;
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
import com.github.bakuplayz.cropclick.metric.Metrics;
import com.github.bakuplayz.cropclick.permissions.PermissionManager;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


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
    private static @Getter(AccessLevel.PACKAGE) CropClick plugin;

    private @Getter CropManager cropManager;
    private @Getter WorldManager worldManager;
    private @Getter AddonManager addonManager;
    private @Getter UpdateManager updateManager;
    private @Getter CommandManager commandManager;
    private @Getter AutofarmManager autofarmManager;
    private @Getter PermissionManager permissionManager;

    private @Getter UsageConfig usageConfig;
    private @Getter CropsConfig cropsConfig;
    private @Getter AddonsConfig addonsConfig;
    private @Getter PlayersConfig playersConfig;
    private @Getter LanguageConfig languageConfig;

    private @Getter WorldDataStorage worldData;
    private @Getter AutofarmDataStorage farmData;

    /**
     * A variable used for resetting only the required items, when a reset is called.
     */
    private boolean isReset;

    /**
     * A variable used for debugging purposes, when enabled it will, for instance log every event call.
     */
    private final @Getter boolean isDebugging = false;

    /**
     * A variable used for getting statistics using bStats.
     */
    private final @Getter(AccessLevel.PACKAGE) Metrics metrics = new Metrics(this, 5160);


    /**
     * Starts the execution of {@link CropClick}.
     */
    @Override
    public void onEnable() {
        if (VersionUtils.between(8.0, 12.9)) {
            LanguageAPI.Console.NOT_SUPPORTED_VERSION.send(getLogger());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        CropClick.plugin = this;

        registerConfigs();
        setupConfigs();

        registerStorages();
        setupStorages();

        handleLegacyConfigs();

        startStoragesSaveInterval();

        registerManagers();
        registerCommands();
        registerListeners();

        loadConfigSections();
    }


    /**
     * Stops the execution of {@link CropClick}.
     */
    @Override
    public void onDisable() {
        CropClick.plugin = null;

        worldData.saveData();
        farmData.saveData();

        Bukkit.getScheduler().cancelTasks(this);
    }


    /**
     * Resets {@link CropClick} (a very expensive compute).
     */
    public void onReset() {
        this.isReset = true;

        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            registerConfigs();
            setupConfigs();

            registerStorages();
            setupStorages();
            startStoragesSaveInterval();

            registerManagers();

            loadConfigSections();
        }, 0);
    }


    /**
     * Handles the {@link Config legacy configurations}.
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

        cropsConfig.setup();
        cropsConfig.setupSections();

        usageConfig.setup();
        addonsConfig.setup();
        playersConfig.setup();
        languageConfig.setup();
    }


    /**
     * Loads all the {@link CropClick} sections.
     */
    public void loadConfigSections() {
        cropsConfig.loadSections();
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
        this.worldData = new WorldDataStorage(this);
        this.farmData = new AutofarmDataStorage(this);
    }


    /**
     * Sets up {@link CropClick CropClick's} data storages.
     */
    public void setupStorages() {
        farmData.setup();
        if (!isReset) {
            farmData.fetchData();
            farmData.saveData();
        }

        worldData.setup();
        if (!isReset) {
            worldData.fetchData();
            worldData.saveData();
        }
    }


    /**
     * Starts the saving interval for {@link DataStorage data storages}.
     */
    private void startStoragesSaveInterval() {
        final int TEN_MINUTES_PERIOD = 10 * 60 * 20; // Written as Minecraft ticks.
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, farmData::saveData, 0, TEN_MINUTES_PERIOD);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, worldData::saveData, 0, TEN_MINUTES_PERIOD);
    }


    /**
     * Registers all the {@link Subcommand commands}.
     */
    private void registerCommands() {
        PluginCommand command = getCommand("cropclick");
        if (command == null) {
            LanguageAPI.Console.FAILED_TO_REGISTER_COMMANDS.send(getLogger());
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

        manager.registerEvents(new MenuListener(), this);

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

}