package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.main.HelpMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class HelpCommand extends SubCommand {

    public HelpCommand(@NotNull CropClick plugin) {
        super(plugin, "help", LanguageAPI.Command.HELP_DESCRIPTION);
    }


    /**
     * It opens a help menu for the player, when performed.
     *
     * @param player The player who executed the command.
     * @param args   The arguments that the player typed in.
     */
    @Override
    public void perform(Player player, String[] args) {
        new HelpMenu(plugin, player, false).open();
    }

}