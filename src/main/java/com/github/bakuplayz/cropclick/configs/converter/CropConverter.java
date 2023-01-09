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
import com.github.bakuplayz.cropclick.configs.converter.base.ExactValue;
import com.github.bakuplayz.cropclick.configs.converter.base.SourceValue;
import com.github.bakuplayz.cropclick.configs.converter.base.YamlConverter;
import com.github.bakuplayz.cropclick.runnables.particles.Particle;
import com.github.bakuplayz.cropclick.utils.FileUtils;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * A class that represents a configuration converter for the file 'crop.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropConverter {

    private static final Function<Object, Object> PARTICLE_CONVERTER = value -> {
        ConfigurationSection section = (ConfigurationSection) value;
        ConfigurationSection output = new MemoryConfiguration();

        ConfigurationSection particleObj = new MemoryConfiguration();
        particleObj.set("delay", 100.0);
        particleObj.set("speed", 1.0);
        particleObj.set("amount",
                MathUtils.clamp(
                        section.getInt("Amount"),
                        Particle.MIN_AMOUNT,
                        Particle.MAX_AMOUNT
                )
        );

        for (String particleName : section.getStringList("Active")) {
            output.set(particleName, particleObj);
        }

        return output;
    };

    private static final Function<Object, Object> SOUND_CONVERTER = value -> {
        ConfigurationSection section = (ConfigurationSection) value;
        ConfigurationSection output = new MemoryConfiguration();

        ConfigurationSection soundObj = new MemoryConfiguration();
        soundObj.set("delay", 100.0);
        soundObj.set("volume", 1.0);
        soundObj.set("pitch", 0.1);

        for (String soundName : section.getStringList("Active")) {
            output.set(soundName, soundObj);
        }

        return output;
    };


    /**
     * Makes the configuration conversion.
     *
     * @param plugin the plugin instance.
     *
     * @apiNote written by BakuPlayz.
     */
    public static void makeConversion(@NotNull CropClick plugin) {
        File inFile = new File(
                plugin.getDataFolder(),
                "crop.yml"
        );
        File outFile = new File(
                plugin.getDataFolder() + "/old",
                "crop.yml"
        );

        YamlConfiguration legacyCrops = YamlConfiguration.loadConfiguration(inFile);
        ConfigurationSection newCrops = CropConverter.convertFormat(legacyCrops);

        FileUtils.copyYamlTo(plugin.getCropsConfig().getConfig(), newCrops);
        FileUtils.move(inFile, outFile, true);
    }


    /**
     * Converts the {@link ConfigurationSection provided "legacy" format} to a {@link ConfigurationSection new format}.
     *
     * @param legacyFormat the legacy format.
     *
     * @return the converted format.
     *
     * @apiNote written by BakuPlayz.
     */
    private static @NotNull ConfigurationSection convertFormat(@NotNull ConfigurationSection legacyFormat) {
        YamlConverter converter = new YamlConverter();

        Map<String, String> cropNames = new HashMap<>();
        cropNames.put("Wheat", "wheat");
        cropNames.put("Beetroot", "beetroot");
        cropNames.put("Carrot", "carrot");
        cropNames.put("Potato", "potato");
        cropNames.put("Netherwart", "netherWart");
        cropNames.put("Sugarcane", "sugarCane");
        cropNames.put("Cactus", "cactus");
        cropNames.put("Cocoa-Beans", "cocoaBean");

        for (Map.Entry<String, String> entry : cropNames.entrySet()) {
            String fromId = entry.getKey();
            String toId = entry.getValue();

            converter.addConversion(SourceValue.of("Settings.Names." + fromId), "crops." + toId + ".drop.name");
            converter.addConversion(SourceValue.of("Crops-Value." + fromId), "crops." + toId + ".drop.amount");
            converter.addConversion(SourceValue.of("Settings.AtLeastOne"), "crops." + toId + ".drop.atLeastOne");

            converter.addConversion(SourceValue.of("Settings.Replant"), "crops." + toId + ".shouldReplant");
            converter.addConversion(SourceValue.of("Activated-Crops." + fromId), "crops." + toId + ".isHarvestable");

            converter.addConversion(SourceValue.of("Settings.MCMMO.XP"), "crops." + toId + ".addons.mcMMO.experience");
            converter.addConversion(SourceValue.of("Settings.Jobs.XP"), "crops." + toId + ".addons.jobsReborn.experience");
            converter.addConversion(SourceValue.of("Settings.Jobs.Money"), "crops." + toId + ".addons.jobsReborn.money");
            converter.addConversion(SourceValue.of("Settings.Jobs.Points"), "crops." + toId + ".addons.jobsReborn.points");

            converter.addConversion(SourceValue.of("Settings.Particles", CropConverter.PARTICLE_CONVERTER), "crops." + toId + ".particles");
            converter.addConversion(SourceValue.of("Settings.Sounds", CropConverter.SOUND_CONVERTER), "crops." + toId + ".sounds");
        }

        Map<String, String> seedNames = new HashMap<>();
        seedNames.put("Wheat-Seeds", "wheatSeed");
        seedNames.put("Beetroot-Seeds", "beetrootSeed");
        seedNames.put("Poison-Potato-Percent", "poisonousPotato");

        for (Map.Entry<String, String> entry : seedNames.entrySet()) {
            String fromId = entry.getKey();
            String toId = entry.getValue();

            converter.addConversion(SourceValue.of("Settings.Names." + fromId), "seeds." + toId + ".drop.name");
            converter.addConversion(SourceValue.of("Crops-Value." + fromId), "seeds." + toId + ".drop.amount");

            converter.addConversion(SourceValue.of("Activated-Crops." + fromId), "seeds." + toId + ".isEnabled");
        }

        converter.addConversion(ExactValue.of(1), "seeds.poisonousPotato.drop.amount");
        converter.addConversion(
                SourceValue.of("Crops-Value.Poison-Potato-Percent", value -> (int) value * 0.01d),
                "seeds.poisonousPotato.drop.chance"
        );

        YamlConfiguration target = new YamlConfiguration();
        converter.execute(legacyFormat, target);

        return target;
    }

}