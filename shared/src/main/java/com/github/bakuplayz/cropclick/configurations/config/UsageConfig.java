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
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'usage.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UsageConfig extends AbstractConfiguration {

    public UsageConfig(@NotNull CropClick plugin) {
        super(plugin, "usage.yml");
    }


    /**
     * Updates the usage information, used to handle legacy configuration.
     */
    public void updateUsageInfo() {
        set(UsageConfig.ConfigurationKey.LAST_OPENED_IN, plugin.getDescription().getDescription());
    }


    /**
     * Checks whether the current configuration format is the latest.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isNewFormatVersion() {
        String lastOpenedIn = get(UsageConfig.ConfigurationKey.LAST_OPENED_IN);
        return !lastOpenedIn.startsWith("0") && !lastOpenedIn.startsWith("1");
    }


    @Getter
    @AllArgsConstructor
    public enum ConfigurationKey implements com.github.bakuplayz.cropclick.configurations.ConfigurationKey {

        LAST_OPENED_IN("last-opened-in", "");

        @NotNull
        private final String path;

        private final Object defaultValue;

        /*
        @Nullable
        private final ConversionFunction<Object> getter;

        @Nullable
        private final ConversionFunction<Object> setter;
*/
    }

}