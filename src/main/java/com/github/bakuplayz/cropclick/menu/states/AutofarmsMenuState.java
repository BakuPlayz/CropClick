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