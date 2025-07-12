/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.permissions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum PermissionKey {

    COMMAND("cropclick.command.%s"),
    COMMAND_GENERAL("cropclick.command.general"),

    AUTOFARM_TOGGLE_ALL("cropclick.toggle.all"),
    AUTOFARM_LINK("cropclick.autofarm.link"),
    AUTOFARM_UNLINK("cropclick.autofarm.unlink"),
    AUTOFARM_UNLINK_OTHERS("cropclick.autofarm.unlink.others"),
    AUTOFARM_UPDATE("cropclick.autofarm.update"),
    AUTOFARM_UPDATE_OTHERS("cropclick.autofarm.update.others"),
    AUTOFARM_INTERACT("cropclick.autofarm.interact"),
    AUTOFARM_INTERACT_OTHERS("cropclick.autofarm.interact.others"),
    AUTOFARM_CLAIM("cropclick.autofarm.claim"),

    CROP_PLANT("cropclick.plant.%s"),
    CROP_DESTROY("cropclick.destroy.%s"),
    CROP_HARVEST("cropclick.harvest.%s");

    @NotNull
    private final String permission;


    public String getPermission(Object @NotNull ... args) {
        return String.format(permission, args);
    }

}
