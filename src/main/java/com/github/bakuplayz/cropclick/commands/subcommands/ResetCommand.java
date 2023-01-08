package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * A class representing the '/crop reset' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ResetCommand extends Subcommand {

    private final File pluginFolder;


    public ResetCommand(@NotNull CropClick plugin) {
        super(plugin, "reset", LanguageAPI.Command.RESET_DESCRIPTION);
        this.pluginFolder = plugin.getDataFolder();
    }


    /**
     * Performs the '/crop reset' command, resetting all settings to default.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        try {
            deleteConfigs();
            deleteDataStorages();
            LanguageAPI.Command.RESET_DELETE.send(plugin, player);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Command.RESET_FAILED.send(plugin, player);
        } finally {
            plugin.onReset();
            LanguageAPI.Command.RESET_SUCCESS.send(plugin, player);
        }
    }


    /**
     * Deletes all the {@link Config config files}.
     *
     * @throws IOException thrown if any deletion failed.
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
     * Deletes all the {@link DataStorage data storage files}.
     *
     * @throws IOException thrown if any deletion failed.
     */
    private void deleteDataStorages()
            throws IOException {
        Files.deleteIfExists(new File(pluginFolder, "worlds.json").toPath());
        Files.deleteIfExists(new File(pluginFolder, "autofarms.json").toPath());
    }

}