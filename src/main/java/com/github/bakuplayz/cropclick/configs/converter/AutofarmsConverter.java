package com.github.bakuplayz.cropclick.configs.converter;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.location.LocationTypeAdapter;
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
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmsConverter {

    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final static Type AUTOFARM_MAP_TYPE = new TypeToken<HashMap<UUID, Autofarm>>() {}.getType();


    /**
     * It loads the old autofarms.yml file, converts it to the new format, and saves it to the new file.
     *
     * @param plugin The plugin instance.
     *
     * @apiNote Written by BakuPlayz.
     */
    public static void makeConversion(@NotNull CropClick plugin) {
        File inFile = new File(
                plugin.getDataFolder(),
                "autofarm.yml"
        );
        File oldFolder = new File(
                plugin.getDataFolder() + "/old"
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
            storage.addFarm(newFarm);
        }
        storage.saveData();

        try {
            Files.move(inFile.toPath(), oldFolder.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(""); //TODO: Make up a good error response message.
        }
    }


    /**
     * It converts a legacy format to the new format.
     *
     * @param legacyFormat The configuration section that contains the legacy format.
     *
     * @apiNote Written by BakuPlayz and <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>.
     *
     * @return A JsonObject
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
            DoublyLocation doublyLocation = LocationUtils.getAsDoubly(containerLocation);

            JsonObject autofarmObj = new JsonObject();

            autofarmObj.addProperty("ownerID", "-1");
            autofarmObj.add("dispenser", LocationTypeAdapter.serialize(dispenserLocation));
            autofarmObj.add("crop", LocationTypeAdapter.serialize(cropLocation));
            autofarmObj.add("container", LocationTypeAdapter.serialize(
                    doublyLocation != null ? doublyLocation : containerLocation
            ));

            output.add(UUID.randomUUID().toString(), autofarmObj);
        }

        return output;
    }


    /**
     * Returns the Location represented by this ConfigurationSection
     *
     * @param section The ConfigurationSection to get the Location from.
     *
     * @return A Location object
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