package com.github.bakuplayz.cropclick.configs.converter;

import com.github.bakuplayz.cropclick.configs.converter.base.SourceValue;
import com.github.bakuplayz.cropclick.configs.converter.base.YamlConverter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ConfigConverter {

    public static @NotNull ConfigurationSection convertFormat(@NotNull ConfigurationSection legacyFormat) {
        YamlConverter converter = new YamlConverter();

        converter.addConversion(SourceValue.of("Activated-Update-Message-Player"), "updateMessage.player");
        converter.addConversion(SourceValue.of("Activated-Update-Message-Console"), "updateMessage.console");

        converter.addConversion(SourceValue.of("Activated-Dispenser"), "autofarms.isEnabled");

        YamlConfiguration target = new YamlConfiguration();
        converter.execute(legacyFormat, target);

        return target;
    }

}