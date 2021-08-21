package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.menu.menus.HelpMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class HelpCommand implements SubCommand {

    private final CropClick plugin;

    public HelpCommand(final @NotNull CropClick plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "help";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.HELP_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "cropclick help";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "cropclick.help";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("cropclick.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        new HelpMenu(player, plugin).open();
    }
}
