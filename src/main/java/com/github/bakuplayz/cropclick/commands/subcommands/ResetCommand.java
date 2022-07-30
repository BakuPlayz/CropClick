package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.SubCommand;
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
 * @version 1.6.0
 * @since 1.6.0
 */
public final class ResetCommand extends SubCommand {

    public ResetCommand(@NotNull CropClick plugin) {
        super(plugin, "reset", LanguageAPI.Command.RESET_DESCRIPTION);
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
            LanguageAPI.Command.RESET_SUCCESS.send(plugin, player);
        }
    }


    /**
     * It deletes the config files, if they are present.
     */
    private void deleteConfigs()
            throws IOException {
        Files.deleteIfExists(new File(plugin.getDataFolder(), "crops.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "config.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "addons.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "players.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "language.yml").toPath());
    }


    /**
     * It deletes the data storages, if they are present.
     */
    private void deleteDataStorages()
            throws IOException {
        Files.deleteIfExists(new File(plugin.getDataFolder(), "worlds.json").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "autofarms.json").toPath());
    }

}