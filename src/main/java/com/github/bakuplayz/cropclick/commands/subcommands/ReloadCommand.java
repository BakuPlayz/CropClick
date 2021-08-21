package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class ReloadCommand implements SubCommand {

    private final CropClick plugin;

    private final CropsConfig cropsConfig;
    private final AddonsConfig addonsConfig;
    private final LanguageConfig languageConfig;

    public ReloadCommand(final @NotNull CropClick plugin) {
        this.languageConfig = plugin.getLanguageConfig();
        this.addonsConfig = plugin.getAddonsConfig();
        this.cropsConfig = plugin.getCropsConfig();
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "reload";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.RELOAD_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "cropclick reload";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "cropclick.reload";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("cropclick.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            plugin.reloadConfig();
            LanguageAPI.Console.FILE_RELOAD.sendMessage("config.yml");

            cropsConfig.reloadConfig();
            addonsConfig.reloadConfig();
            languageConfig.reloadConfig();
        }catch (Exception exception) {
            LanguageAPI.Command.RELOAD_FAILED.sendMessage(plugin, player);
        } finally {
            LanguageAPI.Command.RELOAD_SUCCESS.sendMessage(plugin, player);
        }
    }
}
