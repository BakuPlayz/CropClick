package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.menu.menus.HelpMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class HelpCommand extends SubCommand {

    public HelpCommand(@NotNull CropClick plugin) {
        super(plugin, "help");
    }

    @Override
    public void perform(Player player, String[] args) {
        new HelpMenu(player, plugin).open();
    }

}