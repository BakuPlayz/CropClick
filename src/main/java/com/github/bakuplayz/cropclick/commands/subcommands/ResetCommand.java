package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ResetCommand extends Subcommand {

    private final File pluginFolder;

    private final CropManager cropManager;


    public ResetCommand(@NotNull CropClick plugin) {
        super(plugin, "reset", LanguageAPI.Command.RESET_DESCRIPTION);
        this.pluginFolder = plugin.getDataFolder();
        this.cropManager = plugin.getCropManager();
    }


    /**
     * It deletes all the configs and data storages, then sets up the configs and data storages again, when performed.
     *
     * @param player The player who executed the command.
     * @param args   The arguments that the player typed in.
     */
    @Override
    public void perform(Player player, String[] args) {
        try {
            deleteConfigs();
            deleteDataStorages();
            LanguageAPI.Command.RESET_DELETE.send(plugin, player);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Command.RESET_FAILED.send(plugin, player);
        } finally {
            plugin.setupConfigs();
            plugin.setupStorages();
            resetCropSettings();
            LanguageAPI.Command.RESET_SUCCESS.send(plugin, player);
        }
    }


    /**
     * It deletes the config files, if they are present.
     */
    private void deleteConfigs()
            throws IOException {
        Files.deleteIfExists(new File(pluginFolder, "crops.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "config.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "addons.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "players.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "language.yml").toPath());
    }


    /**
     * It deletes the data storages, if they are present.
     */
    private void deleteDataStorages()
            throws IOException {
        Files.deleteIfExists(new File(pluginFolder, "worlds.json").toPath());
        Files.deleteIfExists(new File(pluginFolder, "autofarms.json").toPath());
    }


    /**
     * For each crop, reset the settings.
     */
    private void resetCropSettings() {
        cropManager.getCrops().forEach(cropManager::resetSettings);
    }

}