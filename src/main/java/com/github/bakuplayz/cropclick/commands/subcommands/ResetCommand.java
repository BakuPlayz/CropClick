package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
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
public final class ResetCommand implements SubCommand {

    private final CropClick plugin;

    public ResetCommand(final @NotNull CropClick plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "reset";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.RESET_DESCRIPTION.get(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "cropclick reset";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "cropclick.reset";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("cropclick.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            deleteConfigs();
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
        Files.deleteIfExists(new File(plugin.getDataFolder(), "database.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "language.yml").toPath());
    }
}
