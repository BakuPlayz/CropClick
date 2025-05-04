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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.common.Collections;
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
@Getter
@ToString
@EqualsAndHashCode
public final class FarmWorld {

    @JsonProperty(value = "name", required = true)
    private final String name;

    /**
     * A variable containing all the banished addons in the {@link FarmWorld farm world}.
     */
    @JsonProperty(value = "banished_addons", required = true)
    private final List<AbstractAddon> banishedAddons;

    @Setter
    @Accessors(fluent = true)
    @JsonProperty(value = "is_banished", required = true)
    private boolean isBanished;

    @Setter
    @Accessors(fluent = true)
    @JsonProperty(value = "allows_players", required = true)
    private boolean allowsPlayers;

    @Setter
    @Accessors(fluent = true)
    @JsonProperty(value = "allows_autofarms", required = true)
    private boolean allowsAutofarms;


    public FarmWorld(@NotNull World world) {
        this.banishedAddons = new ArrayList<>();
        this.name = world.getName();
        this.allowsAutofarms = true;
        this.allowsPlayers = true;
    }


    @JsonCreator
    public FarmWorld(
            @JsonProperty("name") @NotNull String name,
            @JsonProperty("is_banished") boolean isBanished,
            @JsonProperty("allows_players") boolean allowsPlayers,
            @JsonProperty("allows_autofarms") boolean allowsAutofarms,
            @JsonProperty("banished_addons") List<AbstractAddon> banishedAddons
    ) {
        this.allowsAutofarms = allowsAutofarms;
        this.banishedAddons = banishedAddons;
        this.allowsPlayers = allowsPlayers;
        this.isBanished = isBanished;
        this.name = name;
    }


    /**
     * Toggles the banishment state of the specified {@link AbstractAddon} in this {@link FarmWorld}.
     *
     * @param addon the addon to toggle.
     */
    public void toggleAddon(@NotNull AbstractAddon addon) {
        Collections.toggleItem(banishedAddons, addon);
    }


    /**
     * Checks whether the specified {@link AbstractAddon} is currently banished in this {@link FarmWorld}.
     *
     * @param addon the addon to check.
     *
     * @return true if the addon is banished; false otherwise.
     */
    public boolean isAddonBanished(@NotNull AbstractAddon addon) {
        return banishedAddons.contains(addon);
    }

}