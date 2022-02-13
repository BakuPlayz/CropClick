package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
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
 */
public final class ResetCommand extends SubCommand {

    public ResetCommand(@NotNull CropClick plugin) {
        super(plugin, "reset");
    }

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

    private void deleteConfigs() throws IOException {
        Files.deleteIfExists(new File(plugin.getDataFolder(), "crops.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "config.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "addons.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "language.yml").toPath());
    }

    private void deleteDataStorages() throws IOException {
        Files.deleteIfExists(new File(plugin.getDataFolder(), "autofarms.json").toPath());
    }
}