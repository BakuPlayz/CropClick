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
import com.github.bakuplayz.cropclick.datastorages.datastorage.WorldDataStorage;
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

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropClick extends JavaPlugin {

    private @Getter CropManager cropManager;
    private @Getter WorldManager worldManager;
    private @Getter AddonManager addonManager;
    private @Getter CommandManager commandManager;
    private @Getter AutofarmManager autofarmManager;

    private @Getter CropsConfig cropsConfig;
    private @Getter AddonsConfig addonsConfig;
    private @Getter LanguageConfig languageConfig;

    private @Getter WorldDataStorage worldData;
    private @Getter AutofarmDataStorage farmData;

    private static @Getter(AccessLevel.PACKAGE) CropClick plugin;

    @Override
    public void onEnable() {
        if (!VersionUtil.between(8.0, 13.9)) {
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

        worldData.saveWorlds();

        farmData.removeUnlinkedFarms();
        farmData.saveFarms();

        Bukkit.getScheduler().cancelTasks(this);
    }

    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.send("config.yml");
        //getConfig().options().copyDefaults(true);
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
        this.addonManager = new AddonManager(addonsConfig);
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
        manager.registerEvents(new PlayerUpdateAutofarmListener(this), this);

        manager.registerEvents(new PlayerPlantCropListener(this), this);
        manager.registerEvents(new PlayerDestroyCropListener(this), this);
        manager.registerEvents(new PlayerHarvestCropListener(this), this);

        manager.registerEvents(new AutofarmHarvestCropListener(this), this);
    }

}
