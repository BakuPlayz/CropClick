package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.DoubleChest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;


/**
 * A class representing {@link Autofarm Autofarms} as a JSON file.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmDataStorage extends DataStorage {

    private @Getter HashMap<UUID, Autofarm> autofarms;

    /**
     * A variable used to save the {@link #autofarms hashmap of autofarms} appropriately, when using GSON.
     */
    private final Type type;


    public AutofarmDataStorage(@NotNull CropClick plugin) {
        super(plugin, "autofarms.json");
        this.type = new TypeToken<HashMap<UUID, Autofarm>>() {}.getType();
        this.autofarms = new HashMap<>();
    }


    /**
     * Fetches the {@link #fileData file data}.
     */
    @Override
    public void fetchData() {
        super.fetchData();
        loadFarms();
    }


    /**
     * Saves the {@link #fileData file data}.
     */
    @Override
    public void saveData() {
        unlinkDestroyedFarms();
        saveFarms();
    }


    /**
     * Links the {@link Autofarm provided autofarm}.
     *
     * @param autofarm the farm to link.
     */
    public void linkFarm(@NotNull Autofarm autofarm) {
        autofarms.put(autofarm.getFarmerID(), autofarm);
        AutofarmUtils.addCachedID(plugin, autofarm);
    }


    /**
     * Unlinks the {@link Autofarm provided autofarm}.
     *
     * @param autofarm the farm to unlink.
     */
    public void unlinkFarm(@NotNull Autofarm autofarm) {
        autofarms.remove(autofarm.getFarmerID());
        AutofarmUtils.removeCachedID(plugin, autofarm);
    }


    /**
     * Loads all the {@link #autofarms}.
     */
    private void loadFarms() {
        HashMap<UUID, Autofarm> loaded = gson.fromJson(fileData, type);
        this.autofarms = loaded != null ? loaded : new HashMap<>();
    }


    /**
     * Saves all the {@link #autofarms}.
     */
    private void saveFarms() {
        try {
            String data = gson.toJson(autofarms, type);
            JsonElement dataAsJson = jsonParser.parse(data);
            fileData = dataAsJson.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER.send(fileName);
        }

        super.saveData();
    }


    /**
     * Unlinks all the destroyed {@link Autofarm autofarms}.
     */
    private void unlinkDestroyedFarms() {
        AutofarmManager manager = plugin.getAutofarmManager();

        if (manager == null) {
            autofarms.values().removeIf(farm -> !farm.isLinked());
            return;
        }

        try {
            autofarms.values().removeIf(farm -> !farm.isComponentsPresent(manager));
        } catch (Exception e) {
            LanguageAPI.Console.AUTOFARM_STORAGE_FAILED_REMOVE.send();
        }
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided farmerID.
     *
     * @param farmerID the id to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public @Nullable Autofarm findFarmById(String farmerID) {
        if (farmerID == null) {
            return null;
        }
        return autofarms.getOrDefault(UUID.fromString(farmerID), null);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block crop block}.
     *
     * @param block the crop block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public @Nullable Autofarm findFarmByCrop(@NotNull Block block) {
        return autofarms.values().stream()
                        .filter(Autofarm::isLinked)
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> farm.getCropLocation().equals(block.getLocation()))
                        .findFirst().orElse(null);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block container block}.
     *
     * @param block the container block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public @Nullable Autofarm findFarmByContainer(@NotNull Block block) {
        return autofarms.values().stream()
                        .filter(Autofarm::isLinked)
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> {
                            boolean filterByDoubly = filterByDoubly(farm, block);
                            boolean filterByDoubleChest = filterByDoubleChest(farm, block);

                            if (filterByDoubly || filterByDoubleChest) {
                                return true;
                            }

                            Location blockLocation = block.getLocation();
                            Location containerLocation = farm.getContainerLocation();
                            return containerLocation.equals(blockLocation);
                        })
                        .findFirst().orElse(null);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block dispenser block}.
     *
     * @param block the dispenser block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public @Nullable Autofarm findFarmByDispenser(@NotNull Block block) {
        return autofarms.values().stream()
                        .filter(Autofarm::isLinked)
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> farm.getDispenserLocation().equals(block.getLocation()))
                        .findFirst().orElse(null);
    }


    /**
     * Filters searches based on {@link DoublyLocation provided doubly location} matching with the {@link Location provided location}.
     *
     * @param doubly   the doubly location.
     * @param location the location to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubly(@NotNull DoublyLocation doubly, @NotNull Location location) {
        Location singlyLocation = doubly.getSingly();
        Location doublyLocation = doubly.getDoubly();
        return singlyLocation.equals(location) || doublyLocation.equals(location);
    }


    /**
     * Filters searches based on {@link DoublyLocation doubly location} matching with the {@link Block provided block's} location.
     *
     * @param autofarm the farm to base the doubly location on.
     * @param block    the block to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubly(@NotNull Autofarm autofarm, @NotNull Block block) {
        Location containerLocation = autofarm.getContainerLocation();
        if (!(containerLocation instanceof DoublyLocation)) {
            return false;
        }

        return filterByDoubly((DoublyLocation) autofarm.getContainerLocation(), block.getLocation());
    }


    /**
     * Filters searches based on {@link DoubleChest double chests} matching with the {@link Block provided block's} location.
     *
     * @param autofarm the farm to base the doubly location on.
     * @param block    the block to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubleChest(@NotNull Autofarm autofarm, @NotNull Block block) {
        if (!BlockUtils.isDoubleChest(block)) {
            return false;
        }

        DoublyLocation doubleChest = LocationUtils.findDoubly(block);

        if (doubleChest == null) {
            return false;
        }

        return filterByDoubly(doubleChest, autofarm.getContainerLocation());
    }

}