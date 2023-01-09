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
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.location.LocationTypeAdapter;
import com.github.bakuplayz.cropclick.utils.FileUtils;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * A class that represents a configuration converter for the file 'autofarm.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmsConverter {

    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final static Type AUTOFARM_MAP_TYPE = new TypeToken<HashMap<UUID, Autofarm>>() {}.getType();


    /**
     * Makes the configuration conversion.
     *
     * @param plugin the plugin instance.
     *
     * @apiNote written by BakuPlayz and <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>.
     */
    public static void makeConversion(@NotNull CropClick plugin) {
        File inFile = new File(
                plugin.getDataFolder(),
                "autofarm.yml"
        );
        File outFile = new File(
                plugin.getDataFolder() + "/old",
                "autofarm.yml"
        );

        YamlConfiguration legacyAutofarms = YamlConfiguration.loadConfiguration(inFile);
        JsonObject newAutofarms = AutofarmsConverter.convertFormat(legacyAutofarms);

        HashMap<UUID, Autofarm> data = AutofarmsConverter.GSON.fromJson(
                newAutofarms,
                AutofarmsConverter.AUTOFARM_MAP_TYPE
        );

        AutofarmDataStorage storage = plugin.getFarmData();
        for (Map.Entry<UUID, Autofarm> entry : data.entrySet()) {
            Autofarm oldFarm = entry.getValue();
            UUID farmerID = entry.getKey();

            Autofarm newFarm = new Autofarm(
                    farmerID,
                    Autofarm.UNKNOWN_OWNER,
                    oldFarm.isEnabled(),
                    oldFarm.getCropLocation(),
                    oldFarm.getContainerLocation(),
                    oldFarm.getDispenserLocation()
            );
            storage.linkFarm(newFarm);
        }
        storage.saveData();

        FileUtils.move(inFile, outFile, true);
    }


    /**
     * Converts the {@link ConfigurationSection provided "legacy" format} to a {@link ConfigurationSection new format}.
     *
     * @param legacyFormat the legacy format.
     *
     * @return the converted format.
     *
     * @apiNote written by BakuPlayz and <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>.
     */
    private static @NotNull JsonObject convertFormat(@NotNull ConfigurationSection legacyFormat) {
        JsonObject output = new JsonObject();

        ConfigurationSection dispenserSection = legacyFormat.getConfigurationSection("Dispenser");

        if (dispenserSection == null) {
            return output;
        }

        Set<String> dispenserIndices = dispenserSection.getKeys(false);
        for (String index : dispenserIndices) {
            ConfigurationSection dispenser = dispenserSection.getConfigurationSection(index);

            if (dispenser == null) {
                continue;
            }

            Location dispenserLocation = AutofarmsConverter.toLocation(dispenser);

            ConfigurationSection linkedSection = dispenser.getConfigurationSection("Linked");
            if (linkedSection == null) {
                continue;
            }

            ConfigurationSection linkedCropSection = linkedSection.getConfigurationSection("Crop");
            ConfigurationSection linkedContainerSection = linkedSection.getConfigurationSection("Chest");
            if (linkedCropSection == null) {
                continue;
            }

            if (linkedContainerSection == null) {
                continue;
            }

            Location cropLocation = AutofarmsConverter.toLocation(linkedCropSection);
            Location containerLocation = AutofarmsConverter.toLocation(linkedContainerSection);
            DoublyLocation doublyLocation = LocationUtils.findDoubly(containerLocation);

            JsonObject autofarmObj = new JsonObject();

            autofarmObj.addProperty("ownerID", "-1");
            autofarmObj.add("dispenser", LocationTypeAdapter.serializeLocation(dispenserLocation));
            autofarmObj.add("crop", LocationTypeAdapter.serializeLocation(cropLocation));
            autofarmObj.add("container",
                    doublyLocation != null
                    ? LocationTypeAdapter.serializeDoublyLocation(doublyLocation)
                    : LocationTypeAdapter.serializeLocation(containerLocation)
            );

            output.add(UUID.randomUUID().toString(), autofarmObj);
        }

        return output;
    }


    /**
     * Converts the {@link ConfigurationSection provided section} to a {@link Location location}.
     *
     * @param section the section to convert.
     *
     * @return the converted location.
     *
     * @apiNote written by BakuPlayz.
     */
    private static @NotNull Location toLocation(@NotNull ConfigurationSection section) {
        int x = section.getInt("X");
        int y = section.getInt("Y");
        int z = section.getInt("Z");
        String worldName = section.getString("World");
        World world = worldName != null
                      ? Bukkit.getWorld(worldName)
                      : Bukkit.getWorlds().get(0);

        return new Location(world, x, y, z);
    }

}