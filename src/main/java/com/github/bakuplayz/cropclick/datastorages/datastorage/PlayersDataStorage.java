package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public final class PlayersDataStorage extends DataStorage {

    public PlayersDataStorage(@NotNull CropClick plugin) {
        super("players_temp.json", plugin);
    }

    public void clearData() {
        fileData = new JsonObject();
        saveData();
    }
}
