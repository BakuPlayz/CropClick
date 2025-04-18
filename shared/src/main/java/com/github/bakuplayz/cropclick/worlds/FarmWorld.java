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
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.common.CollectionUtils;
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
     * Toggles an {@link AbstractAddon addon} in the {@link FarmWorld world}.
     *
     * @param manager the addon manager.
     * @param name    the name of the addon.
     */
    public void toggleAddon(@NotNull AddonManager manager, @NotNull String name) {
        AbstractAddon addon = manager.findByName(name);
        if (addon == null) {
            Log.debug("Failed to toggle the {} addon, couldn't find it.", name);
            return;
        }

        CollectionUtils.toggleItem(banishedAddons, addon);
    }


    /**
     * Checks whether an {@link AbstractAddon addon} is banished in the {@link FarmWorld world}.
     *
     * @param manager the addon manager.
     * @param name    the name of the addon.
     *
     * @return true if banished, otherwise false.
     */
    public boolean isBanishedAddon(@NotNull AddonManager manager, @NotNull String name) {
        AbstractAddon addon = manager.findByName(name);
        if (addon == null) {
            Log.debug("Failed check state of the {} addon, couldn't find it.", name);
            return false;
        }
        return banishedAddons.contains(addon);
    }

}