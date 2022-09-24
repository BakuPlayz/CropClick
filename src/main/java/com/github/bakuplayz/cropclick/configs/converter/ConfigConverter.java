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
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ConfigConverter {

    /**
     * It loads the old config.yml file, converts it to the new format, and saves it to the new file.
     *
     * @param plugin The plugin instance.
     *
     * @apiNote Written by BakuPlayz.
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
        FileUtils.moveFile(inFile, outFile);
    }


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