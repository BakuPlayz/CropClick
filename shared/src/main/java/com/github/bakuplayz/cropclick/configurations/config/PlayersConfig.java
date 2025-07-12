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
import com.github.bakuplayz.cropclick.crops.Crop;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
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
public final class PlayersConfig extends AbstractPersistentYaml {


    public PlayersConfig() {
        super("players.yml");
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
        return getLocationOrDefault(ConfigurationKey.SELECTED_CROP, null, playerId);
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
        return getLocationOrDefault(ConfigurationKey.SELECTED_CONTAINER, null, playerId);
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
        return getLocationOrDefault(ConfigurationKey.SELECTED_DISPENSER, null, playerId);
    }


    /**
     * Gets all the disabled players' Ids, meaning all the players who are unable to use {@link CropClick}.
     *
     * @return the disabled players.
     */
    @NotNull
    public List<String> getDisabledPlayers() {
        return getList(ConfigurationKey.DISABLED_PLAYERS);
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        ALL_PLAYERS("", Collections.emptyList()),
        SELECTED_PLAYER("%s.components", null),
        SELECTED_CROP("%s.components.crop", null),
        SELECTED_DISPENSER("%s.components.dispenser", null),
        SELECTED_CONTAINER("%s.components.container", null),
        DISABLED_PLAYERS("disabled", Collections.emptyList()),
        LINK_MODE_ENABLED("%s.link.mode", false);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }

}