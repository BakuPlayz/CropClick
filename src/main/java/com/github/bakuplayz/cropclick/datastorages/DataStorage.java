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

    protected final Gson gson = new Gson();
    protected final JsonParser parser = new JsonParser();

    public DataStorage(final @NotNull String fileName,
                       final @NotNull CropClick plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
    }

    public void setup() {
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/datastorage", fileName);

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SETUP.sendMessage(fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_LOADING_SETUP.sendMessage(fileName);
        }
    }

    public void fetchData() {
        try {
            FileReader reader = new FileReader(file);
            JsonElement data = parser.parse(reader);
            this.fileData = data != JsonNull.INSTANCE
                    ? data.getAsJsonObject()
                    : new JsonObject();
        } catch (Exception exception) {
            exception.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_FETCH.sendMessage(fileName);
        } finally {
            LanguageAPI.Console.DATA_STORAGE_FETCHED_DATA.sendMessage(fileName);
        }
    }

    public void saveData() {
        try {
            FileWriter writer = new FileWriter(file, false);
            gson.toJson(fileData, writer);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE.sendMessage(fileName);
        }
    }
}
