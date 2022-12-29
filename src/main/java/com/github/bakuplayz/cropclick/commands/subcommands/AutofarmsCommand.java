package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the '/crop autofarms' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmsCommand extends Subcommand {

    public AutofarmsCommand(@NotNull CropClick plugin) {
        super(plugin, "autofarms", LanguageAPI.Command.AUTOFARM_DESCRIPTION);
    }


    /**
     * Performs the '/crop autofarms' command, opening the {@link AutofarmsMenu}.
     *
     * @param player The player who executed the command.
     * @param args   The arguments passed to the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        new AutofarmsMenu(
                plugin,
                player,
                AutofarmsMenuState.COMMAND_REDIRECT
        ).open();
    }

}