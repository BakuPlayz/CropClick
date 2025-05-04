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

package com.github.bakuplayz.cropclick.configurations.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import com.github.bakuplayz.cropclick.crops.Crop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;


/**
 * A class representing the YAML file: 'players.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayersConfig extends AbstractConfiguration {

    public PlayersConfig(@NotNull CropClick plugin) {
        super(plugin, "players.yml");
    }


    /**
     * Gets the {@link Crop selected crop} for the provided player id.
     *
     * @param playerId the player id to get the selected location from.
     *
     * @return the selected crop's location, otherwise null.
     */
    @Nullable
    public Location getSelectedCrop(@NotNull String playerId) {
        return get(PlayersConfig.ConfigurationKey.SELECTED_CROP, playerId);
    }


    /**
     * Gets the {@link Container selected container} for the provided player id.
     *
     * @param playerId the player id to get the selected location from.
     *
     * @return the selected container's location, otherwise null.
     */
    @Nullable
    public Location getSelectedContainer(@NotNull String playerId) {
        return get(PlayersConfig.ConfigurationKey.SELECTED_CONTAINER, playerId);
    }


    /**
     * Gets the {@link Dispenser selected dispenser} for the provided player id.
     *
     * @param playerId the player id to get the selected location from.
     *
     * @return the selected dispenser's location, otherwise null.
     */
    @Nullable
    public Location getSelectedDispenser(@NotNull String playerId) {
        return get(PlayersConfig.ConfigurationKey.SELECTED_DISPENSER, playerId);
    }


    /**
     * Gets all the disabled players' Ids, meaning all the players who are unable to use {@link CropClick}.
     *
     * @return the disabled players.
     */
    @NotNull
    public List<String> getDisabledPlayers() {
        return get(PlayersConfig.ConfigurationKey.DISABLED_PLAYERS);
    }


    @Getter
    @AllArgsConstructor
    public enum ConfigurationKey implements com.github.bakuplayz.cropclick.configurations.ConfigurationKey {

        ALL_PLAYERS("", Collections.emptyList()),
        SELECTED_PLAYER("%s", null),
        SELECTED_CROP("%s.crop", null),
        SELECTED_DISPENSER("%s.crop", null),
        SELECTED_CONTAINER("%s.container", null),
        DISABLED_PLAYERS("disabled", Collections.emptyList());

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}