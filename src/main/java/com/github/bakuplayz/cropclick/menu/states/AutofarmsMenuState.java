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

package com.github.bakuplayz.cropclick.menu.states;

import com.github.bakuplayz.cropclick.commands.subcommands.AutofarmsCommand;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
import org.bukkit.block.Dispenser;


/**
 * An enumeration representing all the states the {@link AutofarmsMenu} could be in.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum AutofarmsMenuState {

    /**
     * The access state when calling {@link AutofarmsCommand}.
     */
    COMMAND,

    /**
     * The access state when opening the {@link AutofarmsMenu} via another menu.
     */
    MENU,

    /**
     * The access state when clicking an {@link Dispenser}.
     */
    DISPENSER

}