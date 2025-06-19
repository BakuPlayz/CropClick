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

package com.github.bakuplayz.cropclick.world;

import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.common.Collections;
import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Represent a {@link World world} as a {@link FarmWorld farm world}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FarmWorld {

    private final String name;

    /**
     * A variable containing all the banished addons in the {@link FarmWorld farm world}.
     */
    private final List<String> banishedAddons;

    @Setter
    @Accessors(fluent = true)
    private boolean isBanished;

    @Setter
    @Accessors(fluent = true)
    private boolean allowsPlayers;

    @Setter
    @Accessors(fluent = true)
    private boolean allowsAutofarms;


    @NotNull
    public static FarmWorld fromWorld(@NotNull World world) {
        return new FarmWorld(world.getName(), java.util.Collections.emptyList(), false, true, true);
    }


    @NotNull
    public static FarmWorld createBasic(
            @NotNull String name,
            boolean isBanished,
            boolean allowsPlayers,
            boolean allowsAutofarms,
            @NotNull List<String> banishedAddons
    ) {
        return new FarmWorld(name, banishedAddons, isBanished, allowsPlayers, allowsAutofarms);
    }


    /**
     * Toggles the banishment state of the specified {@link AbstractAddon} in this {@link FarmWorld}.
     *
     * @param addon the addon to toggle.
     */
    public void toggleAddon(@NotNull AbstractAddon addon) {
        Collections.toggleItem(banishedAddons, addon.getName());
    }


    /**
     * Checks whether the specified {@link AbstractAddon} is currently banished in this {@link FarmWorld}.
     *
     * @param addon the addon to check.
     *
     * @return true if the addon is banished; false otherwise.
     */
    public boolean isAddonBanished(@NotNull AbstractAddon addon) {
        return banishedAddons.contains(addon.getName());
    }

}