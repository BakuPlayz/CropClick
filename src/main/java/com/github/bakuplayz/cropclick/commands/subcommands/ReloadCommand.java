package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A class representing the '/crop reload' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ReloadCommand extends Subcommand {

    private final Logger logger;
    private final CropsConfig cropsConfig;
    private final AddonsConfig addonsConfig;
    private final PlayersConfig playersConfig;
    private final LanguageConfig languageConfig;


    public ReloadCommand(@NotNull CropClick plugin) {
        super(plugin, "reload", LanguageAPI.Command.RELOAD_DESCRIPTION);
        this.logger = plugin.getLogger();
        this.cropsConfig = plugin.getCropsConfig();
        this.addonsConfig = plugin.getAddonsConfig();
        this.playersConfig = plugin.getPlayersConfig();
        this.languageConfig = plugin.getLanguageConfig();
    }


    /**
     * Performs the '/crop reload' command, reloading the plugin.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        try {
            plugin.reloadConfig();
            LanguageAPI.Console.FILE_RELOAD.send(logger, "config.yml");

            cropsConfig.reloadConfig();
            addonsConfig.reloadConfig();
            playersConfig.reloadConfig();
            languageConfig.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Command.RELOAD_FAILED.send(plugin, player);
        } finally {
            LanguageAPI.Command.RELOAD_SUCCESS.send(plugin, player);
        }
    }

}