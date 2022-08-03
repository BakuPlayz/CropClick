package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmDataStorage extends DataStorage {

    private @Getter HashMap<UUID, Autofarm> farms;


    public AutofarmDataStorage(@NotNull CropClick plugin) {
        super(plugin, "autofarms.json");
        this.farms = new HashMap<>();
    }


    /**
     * Handles the fetching and loading of the farms.
     */
    @Override
    public void fetchData() {
        super.fetchData();
        loadFarms();
    }


    /**
     * Handles the saving of the farms.
     */
    @Override
    public void saveData() {
        removeUnlinkedFarms();
        saveFarms();
    }


    /**
     * Adds an autofarm to the list of autofarms.
     *
     * @param autofarm The autofarm object to add to the list of autofarms.
     */
    public void addFarm(@NotNull Autofarm autofarm) {
        farms.put(autofarm.getFarmerID(), autofarm);
    }


    /**
     * It removes an autofarm from the list of autofarms.
     *
     * @param autofarm The autofarm object that you want to remove.
     */
    public void removeFarm(@NotNull Autofarm autofarm) {
        farms.remove(autofarm.getFarmerID());
    }


    /**
     * It loads the farms from the file.
     */
    private void loadFarms() {
        HashMap<UUID, Autofarm> loaded = gson.fromJson(fileData, new TypeToken<HashMap<UUID, Autofarm>>() {}.getType());
        this.farms = loaded != null ? loaded : new HashMap<>();
    }


    /**
     * It converts the HashMap of farms into a JSON object, then saves it to the file.
     */
    private void saveFarms() {
        String data = gson.toJson(farms, new TypeToken<HashMap<UUID, Autofarm>>() {}.getType());
        JsonElement dataAsJson = JsonParser.parseString(data);
        fileData = dataAsJson.getAsJsonObject();

        super.saveData();
    }


    /**
     * Remove all farms from the map that are not linked to any other farm.
     */
    private void removeUnlinkedFarms() {
        farms.values().removeIf(farm -> !farm.isLinked());
    }


    /**
     * If the farmerID is null, return null, otherwise return the farm with the given farmerID.
     *
     * @param farmerID The ID of the farmer.
     *
     * @return The first found farm with and ID, that equal the cached ID.
     */
    public @Nullable Autofarm findFarmById(String farmerID) {
        if (farmerID == null) {
            return null;
        }
        return farms.getOrDefault(UUID.fromString(farmerID), null);
    }


    /**
     * Find the first farm that is linked, enabled, and has the given block as its crop location.
     *
     * @param block The block that was interacted with
     *
     * @return The first farm that is linked, enabled, and has the same crop location as the block.
     */
    public @Nullable Autofarm findFarmByCrop(@NotNull Block block) {
        return farms.values().stream().filter(Autofarm::isLinked).filter(Autofarm::isEnabled).filter(farm -> farm.getCropLocation().equals(block.getLocation())).findFirst().orElse(null);
    }


    /**
     * Find the first farm that is linked, enabled, and has a container at the given location.
     *
     * @param block The block that was interacted with.
     *
     * @return The first farm that is linked, enabled, and has the same location as the block.
     */
    public @Nullable Autofarm findFarmByContainer(@NotNull Block block) {
        return farms.values().stream().filter(Autofarm::isLinked).filter(Autofarm::isEnabled).filter(farm -> {
            Location blockLocation = block.getLocation();
            Location containerLocation = farm.getContainerLocation();

            if (containerLocation instanceof DoublyLocation) {
                DoublyLocation location = (DoublyLocation) containerLocation;
                Location singlyLocation = location.getSingly();
                Location doublyLocation = location.getDoubly();
                return singlyLocation.equals(blockLocation) || doublyLocation.equals(blockLocation);
            }

            return containerLocation.equals(blockLocation);
        }).findFirst().orElse(null);
    }


    /**
     * Find the farm that is linked to the given dispenser block, if any.
     *
     * @param block The block that was interacted with.
     *
     * @return The first farm that is linked, enabled, and has a dispenser at the given location.
     */
    public @Nullable Autofarm findFarmByDispenser(@NotNull Block block) {
        return farms.values().stream().filter(Autofarm::isLinked).filter(Autofarm::isEnabled).filter(farm -> farm.getDispenserLocation().equals(block.getLocation())).findFirst().orElse(null);
    }

}