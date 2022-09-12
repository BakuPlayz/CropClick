package com.github.bakuplayz.cropclick.configs.converter;

import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
// TODO: Converts from the old autofarm config system to the new one.
public final class AutofarmsConverter {

    /*  NEW CONFIG:
        {
            "UUID": {
                "ownerID": "0",
                "container": {
                    "singly": {
                        "x": -311,
                        "y": 63,
                        "z": 234,
                        "world": "world"
                    },
                    "doubly": {
                        "x": -311,
                        "y": 63,
                        "z": 233,
                        "world": "world"
                    }
                },
                "dispenser": {
                    "x": -313,
                    "y": 64,
                    "z": 236,
                    "world": "world"
                },
                "crop": {
                    "x": -312,
                    "y": 63,
                    "z": 236,
                    "world": "world"
                }
            }
        }
    **/


    /* OLD CONFIG:
     * Chests:
     *   '1':
     *     X: -311.0
     *     Y: 63.0
     *     Z: 234.0
     *     World: world
     * AmountOfChests: 2
     * Crops:
     *   '1':
     *     X: -312.0
     *     Y: 63.0
     *     Z: 236.0
     *     World: world
     * AmountOfCrops: 2
     * Dispenser:
     *   '1':
     *     X: -313.0
     *     Y: 64.0
     *     Z: 236.0
     *     World: world
     *     Linked:
     *       Chest:
     *         X: -311
     *         Y: 63
     *         Z: 234
     *         World: world
     *       Crop:
     *         X: -312
     *         Y: 63
     *         Z: 236
     *         World: world
     * AmountOfDispensers: 2
     */
    public static JsonObject convertFormat(YamlConfiguration legacyFormat) {
        JsonObject newFormat = new JsonObject();

        ConfigurationSection chests = legacyFormat.getConfigurationSection("Chests");
        ConfigurationSection crops = legacyFormat.getConfigurationSection("Crops");
        ConfigurationSection dispenser = legacyFormat.getConfigurationSection("Dispenser");

        List<Location>

        Location chestLocation = legacyFormat.getLocation();

    }

}