package com.github.bakuplayz.cropclick.permissions;

import com.github.bakuplayz.cropclick.commands.Subcommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CommandPermission extends Permission {

    /**
     * Permission to use all the commands.
     */
    public static Permission ALL_COMMANDS = new Permission(
            "cropclick.command.*",
            "Permission to use every command.",
            PermissionDefault.OP
    );


    /**
     * Constructor for creating a command permission.
     */
    public CommandPermission(@NotNull Subcommand command) {
        super(command.getPermission(), PermissionDefault.OP);
        setDescription("Permission to use the " + command.getName() + " command.");
    }

}