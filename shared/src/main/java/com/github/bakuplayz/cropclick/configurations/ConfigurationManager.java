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

import com.github.bakuplayz.cropclick.configurations.config.*;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYaml;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * A manager controlling all the {@link PersistentYaml configurations}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
@Getter
public final class ConfigurationManager {

    private final UsageConfig usageConfig;

    private final CropsConfig cropsConfig;

    private final AddonsConfig addonsConfig;

    private final DefaultConfig defaultConfig;

    private final PlayersConfig playersConfig;

    private final LanguageConfig languageConfig;

    private final DatabaseConfig databaseConfig;


    public ConfigurationManager() {
        this.usageConfig = new UsageConfig();
        this.cropsConfig = new CropsConfig();
        this.addonsConfig = new AddonsConfig();
        this.playersConfig = new PlayersConfig();
        this.defaultConfig = new DefaultConfig();
        this.languageConfig = new LanguageConfig();
        this.databaseConfig = new DatabaseConfig();
    }


    @NotNull
    public Collection<PersistentYaml> getAll() {
        return Arrays.asList(defaultConfig, usageConfig, cropsConfig, addonsConfig, playersConfig, languageConfig, databaseConfig);
    }

}
