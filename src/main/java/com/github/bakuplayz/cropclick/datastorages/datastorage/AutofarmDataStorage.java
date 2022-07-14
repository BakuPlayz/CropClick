package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AutofarmDataStorage extends DataStorage {

    private HashMap<UUID, Autofarm> farms = new HashMap<>();

    public AutofarmDataStorage(@NotNull CropClick plugin) {
        super("autofarms.json", plugin);

        loadFarms();
    }

    public void addFarm(@NotNull Autofarm autofarm) {
        farms.put(autofarm.getFarmerID(), autofarm);
    }

    public void removeFarm(@NotNull Autofarm autofarm) {
        farms.remove(autofarm.getFarmerID());
    }

    private void loadFarms() {
        HashMap<UUID, Autofarm> loaded = gson.fromJson(fileData, (Type) Autofarm.class);
        this.farms = loaded != null ? loaded : new HashMap<>();
    }

    // TODO: Should be called once every 10 minutes or so, also before shutdown.
    public void saveFarms() {
        String data = gson.toJson(farms);
        JsonElement dataAsJson = JsonParser.parseString(data);
        fileData = dataAsJson.getAsJsonObject();

        saveData();
    }

    public void removeUnlinkedFarms() {
        farms.values().removeIf(farm -> !farm.isLinked());
    }

    public @Nullable Autofarm findFarmById(@NotNull String farmerID) {
        return farms.getOrDefault(UUID.fromString(farmerID), null);
    }

    public @Nullable Autofarm findFarmByCrop(@NotNull Block block) {
        return farms.values().stream()
                .filter(Autofarm::isLinked)
                .filter(Autofarm::isEnabled)
                .filter(farm -> farm.getCropLocation() == block.getLocation())
                .findFirst().orElse(null);
    }

    public @Nullable Autofarm findFarmByContainer(@NotNull Block block) {
        return farms.values().stream()
                .filter(Autofarm::isLinked)
                .filter(Autofarm::isEnabled)
                .filter(farm -> farm.getContainerLocation() == block.getLocation())
                .findFirst().orElse(null);
    }

    public @Nullable Autofarm findFarmByDispenser(@NotNull Block block) {
        return farms.values().stream()
                .filter(Autofarm::isLinked)
                .filter(Autofarm::isEnabled)
                .filter(farm -> farm.getDispenserLocation() == block.getLocation())
                .findFirst().orElse(null);
    }

    public @NotNull List<Autofarm> getFarms(int startIndex, int size) {
        Preconditions.checkArgument(startIndex < 0, "The startIndex cannot be less than zero.");
        Preconditions.checkArgument(size <= 0, "The size of retrieved farms cannot be less or equal to zero.");

        return farms.values().stream()
                .skip(startIndex)
                .limit(size)
                .collect(Collectors.toList());
    }

}
