package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public abstract class SubCommand {

    protected final CropClick plugin;

    private final @Getter String commandName;

    public SubCommand(@NotNull CropClick plugin,
                      @NotNull String commandName) {
        this.commandName = commandName;
        this.plugin = plugin;
    }

    public final @NotNull String getDescription() {
        return LanguageAPI.Command.valueOf(commandName.toUpperCase() + "_DESCRIPTION").get(plugin);
    }

    public final @NotNull String getUsage() {
        return "cropclick " + commandName;
    }

    public final @NotNull String getPermission() {
        return "cropclick." + commandName;
    }

    public final boolean hasPermission(@NotNull Player player) {
        return player.hasPermission(getPermission());
    }

    public abstract void perform(Player player, String[] args);
}