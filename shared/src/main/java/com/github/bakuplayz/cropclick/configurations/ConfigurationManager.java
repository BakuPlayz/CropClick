/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
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
package com.github.bakuplayz.cropclick.configurations;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.configurations.config.*;
import com.github.bakuplayz.cropclick.configurations.converter.AutofarmsConverter;
import com.github.bakuplayz.cropclick.configurations.converter.ConfigConverter;
import com.github.bakuplayz.cropclick.configurations.converter.CropConverter;
import com.github.bakuplayz.cropclick.configurations.converter.PlayerConverter;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.FILE_SETUP_LOAD;

/**
 * A manager controlling all the {@link Configuration configurations}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class ConfigurationManager implements LoggerContext {

    private final CropClick plugin;

    @Getter
    private final UsageConfig usageConfig;

    @Getter
    private final CropsConfig cropsConfig;

    @Getter
    private final AddonsConfig addonsConfig;

    @Getter
    private final PlayersConfig playersConfig;

    @Getter
    private final LanguageConfig languageConfig;

    @Getter
    private final DatabaseConfig databaseConfig;


    public ConfigurationManager(@NotNull CropClick plugin) {
        this.usageConfig = new UsageConfig(plugin);
        this.cropsConfig = new CropsConfig(plugin);
        this.addonsConfig = new AddonsConfig(plugin);
        this.playersConfig = new PlayersConfig(plugin);
        this.languageConfig = new LanguageConfig(plugin);
        this.databaseConfig = new DatabaseConfig(plugin);
        this.plugin = plugin;

        setupConfigs();
        handleLegacyConfigs();
    }


    @NotNull
    public Collection<Configuration> getAll() {
        return Arrays.asList(usageConfig, cropsConfig, addonsConfig, playersConfig, languageConfig, databaseConfig);
    }


    /**
     * Sets up and create configurations iff missing.
     */
    private void setupConfigs() {
        FILE_SETUP_LOAD.send(getLogger(), "config.yml");
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        cropsConfig.create();
        usageConfig.create();
        addonsConfig.create();
        playersConfig.create();
        languageConfig.create();
    }


    /**
     * Handles the {@link AbstractConfiguration legacy configurations}.
     */
    private void handleLegacyConfigs() {
        if (usageConfig.isNewFormatVersion()) {
            return;
        }

        CropConverter.makeConversion(plugin);
        PlayerConverter.makeConversion(plugin);
        ConfigConverter.makeConversion(plugin);
        AutofarmsConverter.makeConversion(plugin);

        usageConfig.updateUsageInfo();
    }


}
