package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class ReloadCommand extends SubCommand {

    private final CropsConfig cropsConfig;
    private final AddonsConfig addonsConfig;
    private final PlayersConfig playersConfig;
    private final LanguageConfig languageConfig;


    public ReloadCommand(@NotNull CropClick plugin) {
        super(plugin, "reload", LanguageAPI.Command.RELOAD_DESCRIPTION);
        this.cropsConfig = plugin.getCropsConfig();
        this.addonsConfig = plugin.getAddonsConfig();
        this.playersConfig = plugin.getPlayersConfig();
        this.languageConfig = plugin.getLanguageConfig();
    }


    /**
     * Reloads the config files and sends the status of reloading to the player, when performed.
     *
     * @param player The player who executed the command.
     * @param args   The arguments that the player typed in.
     */
    @Override
    public void perform(Player player, String[] args) {
        try {
            plugin.reloadConfig();
            LanguageAPI.Console.FILE_RELOAD.send("config.yml");

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