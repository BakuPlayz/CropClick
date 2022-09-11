package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmCommand extends Subcommand {

    public AutofarmCommand(@NotNull CropClick plugin) {
        super(plugin, "autofarm", LanguageAPI.Command.AUTOFARM_DESCRIPTION);
    }


    @Override
    public void perform(Player player, String[] args) {
        new AutofarmsMenu(
                plugin,
                player,
                AutofarmsMenuState.COMMAND_REDIRECT
        ).open();
    }

}