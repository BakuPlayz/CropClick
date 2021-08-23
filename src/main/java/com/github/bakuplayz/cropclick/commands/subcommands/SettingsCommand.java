package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.menu.menus.HelpMenu;
import com.github.bakuplayz.cropclick.menu.menus.SettingsMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class SettingsCommand implements SubCommand {

    private final CropClick plugin;

    public SettingsCommand(final @NotNull CropClick plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "settings";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.SETTINGS_DESCRIPTION.get(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "cropclick settings";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "cropclick.settings";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("cropclick.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        new SettingsMenu(plugin, player).open();
    }
}
