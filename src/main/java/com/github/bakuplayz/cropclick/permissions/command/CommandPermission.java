/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    /**
     * Permission to use the '/crop' command.
     */
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