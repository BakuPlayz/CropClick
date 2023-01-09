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

package com.github.bakuplayz.cropclick.configs.converter;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.converter.base.SourceValue;
import com.github.bakuplayz.cropclick.configs.converter.base.YamlConverter;
import com.github.bakuplayz.cropclick.utils.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;


/**
 * A class that represents a configuration converter for the file 'config.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ConfigConverter {

    /**
     * Makes the configuration conversion.
     *
     * @param plugin the plugin instance.
     *
     * @apiNote written by BakuPlayz and <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>..
     */
    public static void makeConversion(@NotNull CropClick plugin) {
        File inFile = new File(
                plugin.getDataFolder(),
                "config.yml"
        );
        File outFile = new File(
                plugin.getDataFolder() + "/old",
                "config.yml"
        );

        YamlConfiguration legacyConfig = YamlConfiguration.loadConfiguration(inFile);
        ConfigurationSection newConfig = ConfigConverter.convertFormat(legacyConfig);

        FileUtils.copyYamlTo(plugin.getConfig(), newConfig);
        FileUtils.move(inFile, outFile, true);
    }


    /**
     * Converts the {@link ConfigurationSection provided "legacy" format} to a {@link ConfigurationSection new format}.
     *
     * @param legacyFormat the legacy format.
     *
     * @return the converted format.
     *
     * @apiNote written by BakuPlayz and <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>..
     */
    private static @NotNull ConfigurationSection convertFormat(@NotNull ConfigurationSection legacyFormat) {
        YamlConverter converter = new YamlConverter();

        converter.addConversion(SourceValue.of("Activated-Update-Message-Player"), "updateMessage.player");
        converter.addConversion(SourceValue.of("Activated-Update-Message-Console"), "updateMessage.console");

        converter.addConversion(SourceValue.of("Activated-Dispenser"), "autofarms.isEnabled");

        YamlConfiguration target = new YamlConfiguration();
        converter.execute(legacyFormat, target);

        return target;
    }

}