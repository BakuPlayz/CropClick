/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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


/**
 * A class representing the '/crop reload' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ReloadCommand extends Subcommand {

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
     * Performs the '/crop reload' command, reloading the plugin.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        try {
            plugin.reloadConfig();
            LanguageAPI.Console.FILE_RELOAD.send(plugin.getLogger(), "config.yml");

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