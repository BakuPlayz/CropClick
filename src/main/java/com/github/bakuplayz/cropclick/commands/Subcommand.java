package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class Subcommand {

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
     * It returns a string that is the description of the command.
     *
     * @return The description of the command.
     */
    public final @NotNull String getDescription() {
        return description.get(plugin);
    }


    /**
     * It returns a string that is the usage of the command.
     *
     * @return The usage of the command.
     */
    public final @NotNull String getUsage() {
        return "cropclick " + name;
    }


    /**
     * It returns the permission to execute the command.
     *
     * @return The permission for the command.
     */
    public final @NotNull String getPermission() {
        return "cropclick.command." + name;
    }


    /**
     * Returns true if the player has the permission, false otherwise.
     *
     * @param player The player to check the permission for.
     *
     * @return A boolean value.
     */
    public final boolean hasPermission(@NotNull Player player) {
        return player.hasPermission(getPermission());
    }


    /**
     * "This function is called when the command is executed."
     * <p>
     * The first parameter is the player who executed the command. The second parameter is an array of strings that
     * contains the arguments that were passed to the command.
     * </p>
     *
     * @param player The player who executed the command.
     * @param args   The arguments passed to the command.
     */
    public abstract void perform(Player player, String[] args);

}