package com.github.bakuplayz.cropclick.configs.converter;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.converter.base.SourceValue;
import com.github.bakuplayz.cropclick.configs.converter.base.YamlConverter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * (DESCRIPTION)
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
        File oldFolder = new File(
                plugin.getDataFolder() + "/old"
        );

        YamlConfiguration legacyConfig = YamlConfiguration.loadConfiguration(inFile);
        ConfigurationSection newConfig = PlayerConverter.convertFormat(legacyConfig);

        try {
            plugin.getPlayersConfig().getConfig().save(newConfig.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(""); //TODO: Make up a good error response message.
        }

        try {
            Files.move(inFile.toPath(), oldFolder.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(""); //TODO: Make up a good error response message.
        }
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