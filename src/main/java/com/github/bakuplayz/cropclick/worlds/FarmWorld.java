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

package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.utils.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent a {@link World world} as a {@link FarmWorld farm world}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class FarmWorld {

    private final @Getter String name;

    private @Setter @Getter @Accessors(fluent = true) boolean isBanished;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsPlayers;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsAutofarms;

    /**
     * A variable containing all the banished addons in the {@link FarmWorld farm world}.
     */
    private final @Getter List<Addon> banishedAddons;


    public FarmWorld(@NotNull World world) {
        this.banishedAddons = new ArrayList<>();
        this.name = world.getName();
        this.allowsAutofarms = true;
        this.allowsPlayers = true;
    }


    public FarmWorld(@NotNull String name,
                     boolean isBanished,
                     boolean allowsPlayers,
                     boolean allowsAutofarms,
                     List<Addon> banishedAddons) {
        this.allowsAutofarms = allowsAutofarms;
        this.banishedAddons = banishedAddons;
        this.allowsPlayers = allowsPlayers;
        this.isBanished = isBanished;
        this.name = name;
    }


    /**
     * Toggles an {@link Addon addon} in the {@link FarmWorld world}.
     *
     * @param manager the addon manager.
     * @param name    the name of the addon.
     */
    public void toggleAddon(@NotNull AddonManager manager, @NotNull String name) {
        Addon addon = manager.findByName(name);
        if (addon == null) {
            return;
        }

        CollectionUtils.toggleItem(banishedAddons, addon);
    }


    /**
     * Checks whether an {@link Addon addon} is banished in the {@link FarmWorld world}.
     *
     * @param manager the addon manager.
     * @param name    the name of the addon.
     *
     * @return true if banished, otherwise false.
     */
    public boolean isBanishedAddon(@NotNull AddonManager manager, @NotNull String name) {
        Addon addon = manager.findByName(name);
        if (addon == null) {
            return false;
        }
        return banishedAddons.contains(addon);
    }

}