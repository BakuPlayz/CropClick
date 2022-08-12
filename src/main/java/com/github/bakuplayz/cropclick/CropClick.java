package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public class CropClick extends JavaPlugin {

    private @Getter CropManager cropManager;
    private @Getter WorldManager worldManager;
    private @Getter AddonManager addonManager;
    private @Getter UpdateManager updateManager;
    private @Getter CommandManager commandManager;
    private @Getter AutofarmManager autofarmManager;
    private @Getter PermissionManager permissionManager;

    private @Getter CropsConfig cropsConfig;
    private @Getter AddonsConfig addonsConfig;
    private @Getter PlayersConfig playersConfig;
    private @Getter LanguageConfig languageConfig;

    private @Getter WorldDataStorage worldData;
    private @Getter AutofarmDataStorage farmData;

    private static @Getter(AccessLevel.PACKAGE) CropClick plugin;

    private boolean isUnitTest;
    private boolean isReset;


    /**
     * The default plugin constructor.
     */
    public CropClick() {
        super();
    }


    /**
     * A constructor for unit testing.
     */
    protected CropClick(JavaPluginLoader loader,
                        PluginDescriptionFile description,
                        File dataFolder,
                        File file) {
        super(loader, description, dataFolder, file);
        this.isUnitTest = true;
    }


    @Override
    public void onEnable() {
        if (isUnitTest) {
            registerConfigs();
            setupConfigs();

            registerStorages();
            setupStorages();
            return;
        }

        if (!VersionUtils.between(8.0, 13.9)) {
            LanguageAPI.Console.NOT_SUPPORTED_VERSION.send();
            return;
        }

        CropClick.plugin = this;

        registerConfigs();
        setupConfigs();

        registerStorages();
        setupStorages();
        startStoragesSaveInterval();

        registerManagers();
        registerCommands();
        registerListeners();
        
        loadConfigSections();
    }


    @Override
    public void onDisable() {
        CropClick.plugin = null;

        worldData.saveData();
        farmData.saveData();

        Bukkit.getScheduler().cancelTasks(this);
    }


    /**
     * It resets the plugin (takes long to compute, 90ms+).
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


    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send("config.yml");
        //getConfig().options().copyDefaults(true);
        saveConfig();

        if (!isUnitTest) {
            cropsConfig.setup();
            cropsConfig.setupSections();
        }

        addonsConfig.setup();
        playersConfig.setup();
        languageConfig.setup();
    }


    public void loadConfigSections() {
        cropsConfig.loadSections();
    }


    private void registerConfigs() {
        if (!isUnitTest) {
            this.cropsConfig = new CropsConfig(this);
        }

        this.addonsConfig = new AddonsConfig(this);
        this.playersConfig = new PlayersConfig(this);
        this.languageConfig = new LanguageConfig(this);
    }


    private void registerStorages() {
        this.worldData = new WorldDataStorage(this);
        this.farmData = new AutofarmDataStorage(this);
    }


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


    private void startStoragesSaveInterval() {
        long TEN_MINUTES_PERIOD = 12000L;
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, farmData::saveData, 0, TEN_MINUTES_PERIOD);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, worldData::saveData, 0, TEN_MINUTES_PERIOD);
    }


    private void registerCommands() {
        PluginCommand command = getCommand("cropclick");
        if (command == null) {
            LanguageAPI.Console.FAILED_TO_REGISTER_COMMANDS.send();
            return;
        }

        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);
    }


    private void registerManagers() {
        this.cropManager = new CropManager(this);
        this.worldManager = new WorldManager(this);
        this.autofarmManager = new AutofarmManager(this);

        if (!isReset) {
            this.addonManager = new AddonManager(this);
            this.updateManager = new UpdateManager(this);
            this.commandManager = new CommandManager(this);
            this.permissionManager = new PermissionManager(this);
        }
    }


    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new MenuListener(), this);

        manager.registerEvents(new PlayerInteractAtCropListener(this), this);
        manager.registerEvents(new PlayerInteractAtDispenserListener(this), this);
        manager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);
        manager.registerEvents(new PlayerInteractAtContainerListener(this), this);

        manager.registerEvents(new HarvestCropListener(), this);
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

        if (VersionUtils.between(8.3, 13.9)) {
            manager.registerEvents(new EntityDestroyAutofarmListener(this), this);
        }
    }

}