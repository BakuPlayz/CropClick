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
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.main.HelpMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the '/crop help' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HelpCommand extends Subcommand {

    public HelpCommand(@NotNull CropClick plugin) {
        super(plugin, "help", LanguageAPI.Command.HELP_DESCRIPTION);
    }


    /**
     * Performs the '/crop help' command, opening the {@link HelpMenu}.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        new HelpMenu(plugin, player, false).openMenu();
    }

}