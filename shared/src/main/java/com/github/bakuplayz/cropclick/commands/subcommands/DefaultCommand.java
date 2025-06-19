package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.menus.MainMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.common.Languages.Command.DEFAULT_DESCRIPTION;


/**
 * A class representing the '/crop' command.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
@AllArgsConstructor
public final class DefaultCommand implements Subcommand {

    private final CropClick plugin;


    @NotNull
    @Override
    public String getName() {
        return "";
    }


    @NotNull
    @Override
    public String getDescription() {
        return DEFAULT_DESCRIPTION.get(plugin);
    }


    /**
     * Performs the '/crop' command, opening the {@link MainMenu}.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        new MainMenu(plugin).open(player);
    }

}
