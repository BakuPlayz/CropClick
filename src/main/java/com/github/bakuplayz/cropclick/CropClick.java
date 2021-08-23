package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.commands.CommandManager;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.listeners.MenuListener;
import com.github.bakuplayz.cropclick.listeners.autofarm.AutofarmHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.destory.PlayerDestroyCropListener;
import com.github.bakuplayz.cropclick.listeners.player.harvest.PlayerHarvestCropListener;
import com.github.bakuplayz.cropclick.listeners.player.interact.PlayerInteractAtAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerLinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUnlinkAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.link.PlayerUpdateAutofarmListener;
import com.github.bakuplayz.cropclick.listeners.player.plant.PlayerPlantCropListener;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CropClick extends JavaPlugin {

    private @Getter CropManager cropManager;
    private @Getter WorldManager worldManager;
    private @Getter AddonManager addonManager;
    private @Getter CommandManager commandManager;
    private @Getter AutofarmManager autofarmManager;

    private @Getter CropsConfig cropsConfig;
    private @Getter AddonsConfig addonsConfig;
    private @Getter LanguageConfig languageConfig;
    private @Getter AutofarmDataStorage autofarmDataStorage;

    private static @Getter(AccessLevel.PACKAGE) CropClick plugin;

    @Override
    public void onEnable() {
        if (VersionUtil.isInInterval(8.0, 13.9)) {
            plugin = this;

            registerConfigs();
            setupConfigs();

            registerDataStorages();
            setupDataStorages();

            registerManagers();
            registerCommands();
            registerListeners();
        } else {
            LanguageAPI.Console.NOT_SUPPORTED_VERSION.send();
        }
    }

    @Override
    public void onDisable() {
        plugin = null;

        autofarmDataStorage.removeUnlinkedAutofarms();
        autofarmDataStorage.saveAutofarms();

        Bukkit.getScheduler().cancelTasks(this);
    }

    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send("config.yml");
        getConfig().options().copyDefaults(true);
        saveConfig();

        cropsConfig.setup();
        addonsConfig.setup();
        languageConfig.setup();
    }

    private void registerConfigs() {
        this.cropsConfig = new CropsConfig(this);
        this.addonsConfig = new AddonsConfig(this);
        this.languageConfig = new LanguageConfig(this);
    }

    private void registerDataStorages() {
        this.autofarmDataStorage = new AutofarmDataStorage(this);
    }

    private void setupDataStorages() {
        autofarmDataStorage.setup();
        autofarmDataStorage.fetchData();
        autofarmDataStorage.saveData();
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
        this.addonManager = new AddonManager(addonsConfig);
        this.commandManager = new CommandManager(this);
        this.autofarmManager = new AutofarmManager(this);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new MenuListener(), this);

        pluginManager.registerEvents(new PlayerPlantCropListener(this), this);
        pluginManager.registerEvents(new PlayerHarvestCropListener(this), this);
        pluginManager.registerEvents(new PlayerDestroyCropListener(this), this);
        pluginManager.registerEvents(new PlayerLinkAutofarmListener(this), this);
        pluginManager.registerEvents(new PlayerUnlinkAutofarmListener(this), this);
        pluginManager.registerEvents(new PlayerUpdateAutofarmListener(this), this);
        pluginManager.registerEvents(new PlayerInteractAtAutofarmListener(this), this);

        pluginManager.registerEvents(new AutofarmHarvestCropListener(this), this);
    }
}
