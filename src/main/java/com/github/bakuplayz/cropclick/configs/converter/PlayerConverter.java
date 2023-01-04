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
 * A class that represents a configuration converter for the file 'player.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerConverter {

    /**
     * It loads the old player.yml file, converts it to the new format, and saves it to the new file.
     *
     * @param plugin The plugin instance.
     *
     * @apiNote Written by BakuPlayz.
     */
    public static void makeConversion(@NotNull CropClick plugin) {
        File inFile = new File(
                plugin.getDataFolder(),
                "player.yml"
        );
        File outFile = new File(
                plugin.getDataFolder() + "/old",
                "player.yml"
        );

        YamlConfiguration legacyPlayer = YamlConfiguration.loadConfiguration(inFile);
        ConfigurationSection newPlayer = PlayerConverter.convertFormat(legacyPlayer);

        FileUtils.copyYamlTo(plugin.getPlayersConfig().getConfig(), newPlayer);
        FileUtils.move(inFile, outFile);
    }


    /**
     * Convert a legacy configuration section to a new one.
     *
     * @param legacyFormat The configuration section that contains the legacy format.
     *
     * @return A ConfigurationSection
     */
    private static @NotNull ConfigurationSection convertFormat(@NotNull ConfigurationSection legacyFormat) {
        YamlConverter converter = new YamlConverter();

        converter.addConversion(SourceValue.of("Disabled"), "disabled");

        YamlConfiguration target = new YamlConfiguration();
        converter.execute(legacyFormat, target);

        return target;
    }

}