package com.github.bakuplayz.cropclick.permissions.command;

import com.github.bakuplayz.cropclick.commands.Subcommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing a command permission.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CommandPermission extends Permission {

    /**
     * Permission to use all the commands.
     */
    public static final Permission ALL_COMMANDS = new Permission(
            "cropclick.command.*",
            "Permission to use every command.",
            PermissionDefault.OP
    );

    public static final Permission GENERAL_COMMAND = new Permission(
            "cropclick.command.general",
            "Permission to use the general command.",
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