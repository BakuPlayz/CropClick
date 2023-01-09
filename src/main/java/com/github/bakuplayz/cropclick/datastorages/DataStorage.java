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

package com.github.bakuplayz.cropclick.datastorages;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.location.LocationTypeAdapter;
import com.google.gson.*;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * A class representing an JSON file (with the help of the GSON API).
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class DataStorage implements Storageable {

    protected final CropClick plugin;


    protected final Gson gson;
    protected final JsonParser jsonParser;


    protected @Getter File file;
    protected @Getter JsonObject fileData;
    protected final @Getter String fileName;


    public DataStorage(@NotNull CropClick plugin, @NotNull String fileName) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .setPrettyPrinting()
                .create();
        this.jsonParser = new JsonParser();
    }


    /**
     * Sets up the extending object's {@link DataStorage DataStorage file}.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void setup() {
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/datastorage", fileName);

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SETUP.send(plugin.getLogger(), fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_LOADING_SETUP.send(plugin.getLogger(), fileName);
        }
    }


    /**
     * Fetches the data from the extending object's {@link DataStorage DataStorage file}.
     */
    public void fetchData() {
        try {
            FileReader reader = new FileReader(file);
            JsonElement data = jsonParser.parse(reader);
            fileData = data != JsonNull.INSTANCE
                       ? data.getAsJsonObject()
                       : new JsonObject();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_LOAD.send(plugin.getLogger(), fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_LOADED_DATA.send(plugin.getLogger(), fileName);
        }
    }


    /**
     * Saves the data to the extending object's {@link DataStorage DataStorage file}.
     */
    public void saveData() {
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(fileData, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_REMOVED.send(plugin.getLogger(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER.send(plugin.getLogger(), fileName);
        }
    }

}