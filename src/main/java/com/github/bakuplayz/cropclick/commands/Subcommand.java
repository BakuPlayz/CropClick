package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a subcommand.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class Subcommand implements Commandable {

    protected final CropClick plugin;

    private final @Getter String name;
    private final LanguageAPI.Command description;


    public Subcommand(@NotNull CropClick plugin,
                      @NotNull String name,
                      @NotNull LanguageAPI.Command description) {
        this.description = description;
        this.plugin = plugin;
        this.name = name;
    }


    /**
     * Gets the description of the {@link Subcommand extending command}.
     *
     * @return the command's description.
     */
    public @NotNull String getDescription() {
        return description.get(plugin);
    }


    /**
     * Gets the usage of the {@link Subcommand extending command}.
     *
     * @return the command's usage.
     */
    public @NotNull String getUsage() {
        return "cropclick " + name;
    }


    /**
     * Gets the permission for the {@link Subcommand extending command}.
     *
     * @return the command's permission.
     */
    public @NotNull String getPermission() {
        return "cropclick.command." + name;
    }


    /**
     * Checks whether the {@link Player provided player} has permission to perform the {@link Subcommand extending command}.
     *
     * @param player the player to check.
     *
     * @return true if it has, otherwise false.
     */
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission(getPermission());
    }


    /**
     * Performs the {@link Subcommand extending command}.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    public abstract void perform(@NotNull Player player, String[] args);

}