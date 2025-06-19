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

package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * An interface representing a subcommand.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Subcommand {

    String getName();


    /**
     * Gets the implementing command's description of the command.
     *
     * @return the command's description.
     */
    @NotNull
    String getDescription();


    /**
     * Gets the implementing command's usage of the command.
     *
     * @return the command's usage.
     */
    @NotNull
    default String getUsage() {
        return String.format("cropclick %s", getName());
    }


    /**
     * Gets the implementing command's permission of the command.
     *
     * @return the command's permission.
     */
    @NotNull
    default String getPermission() {
        return PermissionKey.COMMAND.getPermission(getName());
    }
    

    /**
     * Performs the implementing command.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    void perform(@NotNull Player player, String... args);

}