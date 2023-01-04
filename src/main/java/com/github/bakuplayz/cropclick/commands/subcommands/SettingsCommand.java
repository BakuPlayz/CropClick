package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the '/crop settings' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SettingsCommand extends Subcommand {

    public SettingsCommand(@NotNull CropClick plugin) {
        super(plugin, "settings", LanguageAPI.Command.SETTINGS_DESCRIPTION);
    }


    /**
     * Performs the '/crop settings' command, showing the {@link SettingsMenu}.
     *
     * @param player The player who executed the command.
     * @param args   The arguments passed to the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        new SettingsMenu(plugin, player, false).openMenu();
    }

}