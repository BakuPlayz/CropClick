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
import com.github.bakuplayz.cropclick.listeners.autofarm.AutofarmHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.destory.PlayerDestroyCropListener;
import com.github.bakuplayz.cropclick.listeners.player.harvest.PlayerHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtContainerListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtCropListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtDispenserListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.plant.PlayerPlantCropListener;
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
import org.jetbrains.annotations.NotNull;

import java.io.File;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public class CropClick extends JavaPlugin {

    private @Getter CropManager cropManager;
    private @Getter WorldManager worldManager;
    private @Getter AddonManager addonManager;
    private @Getter UpdateManager updateManager;
    private @Getter CommandManager commandManager;
    private @Getter AutofarmManager autofarmManager;


    private @Getter CropsConfig cropsConfig;
    private @Getter AddonsConfig addonsConfig;
    private @Getter PlayersConfig playersConfig;
    private @Getter LanguageConfig languageConfig;

    private @Getter WorldDataStorage worldData;
    private @Getter AutofarmDataStorage farmData;

    private static @Getter(AccessLevel.PACKAGE) CropClick plugin;

    private boolean isUnitTest;


    public CropClick() {
        super();
    }


    // A constructor for unit testing.
    protected CropClick(@NotNull JavaPluginLoader loader,
                        @NotNull PluginDescriptionFile description,
                        @NotNull File dataFolder,
                        @NotNull File file) {
        super(loader, description, dataFolder, file);
        this.isUnitTest = true;
    }


    // TODO: Should be called once every 10 minutes or so, also before shutdown. (Call on another thread using bukkit.Schedule()) -- dataStorages


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

        registerManagers();
        registerCommands();
        registerListeners();
    }


    @Override
    public void onDisable() {
        CropClick.plugin = null;

        worldData.saveData();
        farmData.saveData();

        Bukkit.getScheduler().cancelTasks(this);
    }


    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send("config.yml");
        //getConfig().options().copyDefaults(true);
        saveConfig();

        cropsConfig.setup();
        addonsConfig.setup();
        playersConfig.setup();
        languageConfig.setup();
    }


    private void registerConfigs() {
        this.cropsConfig = new CropsConfig(this);
        this.addonsConfig = new AddonsConfig(this);
        this.playersConfig = new PlayersConfig(this);
        this.languageConfig = new LanguageConfig(this);
    }


    private void registerStorages() {
        this.worldData = new WorldDataStorage(this);
        this.farmData = new AutofarmDataStorage(this);
    }


    private void setupStorages() {
        farmData.setup();
        farmData.fetchData();
        farmData.saveData();

        worldData.setup();
        worldData.fetchData();
        worldData.saveData();
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
        this.cropManager = new CropManager(cropsConfig);
        this.worldManager = new WorldManager(this);
        this.addonManager = new AddonManager(this);
        this.updateManager = new UpdateManager(this);
        this.commandManager = new CommandManager(this);
        this.autofarmManager = new AutofarmManager(this);
    }


    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new MenuListener(), this);

        manager.registerEvents(new PlayerInteractAtCropListener(this), this);
        manager.registerEvents(new PlayerInteractAtDispenserListener(this), this);
        manager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);
        manager.registerEvents(new PlayerInteractAtContainerListener(this), this);

        manager.registerEvents(new PlayerLinkAutofarmListener(this), this);
        manager.registerEvents(new PlayerUnlinkAutofarmListener(this), this);

        manager.registerEvents(new PlayerPlantCropListener(this), this);
        manager.registerEvents(new PlayerDestroyCropListener(this), this);
        manager.registerEvents(new PlayerHarvestCropListener(this), this);

        manager.registerEvents(new AutofarmHarvestCropListener(this), this);
    }

}