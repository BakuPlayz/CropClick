package com.github.bakuplayz.cropclick.datastorages;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.google.gson.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class DataStorage {

    protected CropClick plugin;

    protected File file;
    protected String fileName;
    protected @Getter JsonObject fileData;

    protected final Gson gson;

    public DataStorage(@NotNull String fileName,
                       @NotNull CropClick plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.gson = new Gson();
    }

    public void setup() {
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/datastorage", fileName);

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SETUP.send(fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_LOADING_SETUP.send(fileName);
        }
    }

    public void fetchData() {
        try {
            FileReader reader = new FileReader(file);
            JsonElement data = JsonParser.parseReader(reader);
            fileData = data != JsonNull.INSTANCE
                    ? data.getAsJsonObject()
                    : new JsonObject();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_LOAD.send(fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_LOADED_DATA.send(fileName);
        }
    }

    public void saveData() {
        try {
            FileWriter writer = new FileWriter(file, false);
            gson.toJson(fileData, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_REMOVED.send(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER.send(fileName);
        }
    }

}