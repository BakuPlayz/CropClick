package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.LanguageConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class ReloadCommand extends SubCommand {

    private final CropsConfig cropsConfig;
    private final AddonsConfig addonsConfig;
    private final LanguageConfig languageConfig;

    public ReloadCommand(@NotNull CropClick plugin) {
        super(plugin, "reload");
        this.cropsConfig = plugin.getCropsConfig();
        this.addonsConfig = plugin.getAddonsConfig();
        this.languageConfig = plugin.getLanguageConfig();
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            plugin.reloadConfig();
            LanguageAPI.Console.FILE_RELOAD.send("config.yml");

            cropsConfig.reloadConfig();
            addonsConfig.reloadConfig();
            languageConfig.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Command.RELOAD_FAILED.send(plugin, player);
        } finally {
            LanguageAPI.Command.RELOAD_SUCCESS.send(plugin, player);
        }
    }
}