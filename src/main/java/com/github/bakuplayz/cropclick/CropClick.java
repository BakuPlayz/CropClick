package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
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
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUpdateAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.plant.PlayerPlantCropListener;
import com.github.bakuplayz.cropclick.permissions.PermissionManager;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Represents the core of CropClick -- my precious.
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
    private final @Getter boolean isDebugging = true;


    /**
     * It starts the execution of the CropClick, conceptually equivalent to an 'main(args)' run.
     */
    @Override
    public void onEnable() {
        if (VersionUtils.between(8.0, 12.9)) {
            LanguageAPI.Console.NOT_SUPPORTED_VERSION.send();
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
     * It "ends" the execution of CropClick.
     */
    @Override
    public void onDisable() {
        CropClick.plugin = null;

        worldData.saveData();
        farmData.saveData();

        Bukkit.getScheduler().cancelTasks(this);
    }


    /**
     * It resets the plugin (a very expensive compute).
     */
    public void onReset() {
        this.isReset = true;

        registerConfigs();
        setupConfigs();

        registerStorages();
        setupStorages();
        startStoragesSaveInterval();

        registerManagers();

        loadConfigSections();
    }


    /**
     * It converts the old (legacy) configs, their new equivalents.
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
     * It initializes, loads and setups the referenced configurations, YAML files.
     */
    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send("config.yml");
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
     * It initializes and loads the "overloaded" configuration sections.
     */
    public void loadConfigSections() {
        cropsConfig.loadSections();
    }


    /**
     * It instantiates the referenced configurations, YAML files.
     */
    private void registerConfigs() {
        this.usageConfig = new UsageConfig(this);
        this.cropsConfig = new CropsConfig(this);
        this.addonsConfig = new AddonsConfig(this);
        this.playersConfig = new PlayersConfig(this);
        this.languageConfig = new LanguageConfig(this);
    }


    /**
     * It instantiates the referenced storages, JSON files.
     */
    private void registerStorages() {
        this.worldData = new WorldDataStorage(this);
        this.farmData = new AutofarmDataStorage(this);
    }


    /**
     * It initializes, loads and setups the referenced storages, JSON files.
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
     * It initializes the {@link DataStorage#saveData() saveData} interval/task for all the referenced storages, JSON files.
     */
    private void startStoragesSaveInterval() {
        final int TEN_MINUTES_PERIOD = 10 * 60 * 20; // Written as ticks (20 per second).
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, farmData::saveData, 0, TEN_MINUTES_PERIOD);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, worldData::saveData, 0, TEN_MINUTES_PERIOD);
    }


    /**
     * It registers the 'cropclick' command, and its subcommands.
     */
    private void registerCommands() {
        PluginCommand command = getCommand("cropclick");
        if (command == null) {
            LanguageAPI.Console.FAILED_TO_REGISTER_COMMANDS.send();
            return;
        }

        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);
    }


    /**
     * It instantiates the referenced managers.
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
     * It instantiates and registers the referenced listeners.
     */
    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new MenuListener(), this);

        manager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);
        manager.registerEvents(new PlayerInteractAtContainerListener(this), this);
        manager.registerEvents(new PlayerInteractAtDispenserListener(this), this);
        manager.registerEvents(new PlayerInteractAtCropListener(this), this);

        manager.registerEvents(new HarvestCropListener(this), this);
        manager.registerEvents(new PlayerHarvestCropListener(this), this);
        manager.registerEvents(new AutofarmHarvestCropListener(this), this);

        manager.registerEvents(new PlayerUpdateAutofarmListener(this), this);
        manager.registerEvents(new PlayerUnlinkAutofarmListener(this), this);
        manager.registerEvents(new PlayerLinkAutofarmListener(this), this);

        manager.registerEvents(new PlayerPlantCropListener(this), this);
        manager.registerEvents(new PlayerDestroyCropListener(this), this);

        manager.registerEvents(new AutofarmUpdateListener(this), this);
        manager.registerEvents(new AutofarmUnlinkListener(this), this);
        manager.registerEvents(new AutofarmLinkListener(this), this);

        manager.registerEvents(new EntityDestroyAutofarmListener(this), this);
    }

}