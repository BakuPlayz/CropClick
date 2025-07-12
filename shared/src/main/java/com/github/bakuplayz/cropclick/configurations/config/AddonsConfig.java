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

import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.impl.AbstractPersistentYaml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'addons.yml'.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AddonsConfig extends AbstractPersistentYaml {

    public AddonsConfig() {
        super("addons.yml");
    }


    /**
     * Checks whether the {@link AbstractAddon addon} is enabled based on the provided name.
     *
     * @param addonName the name of the addon.
     *
     * @return true if enabled, otherwise false.
     */
    public boolean isEnabled(@NotNull String addonName) {
        return getBoolean(ConfigurationKey.ADDON_ENABLED, addonName);
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ConfigurationKey implements PersistentYamlKey {

        ADDON_ENABLED("addons.%s.isEnabled", true);

        @NotNull
        private final String path;

        private final Object defaultValue;

    }


}