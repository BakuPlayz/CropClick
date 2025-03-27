/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.datacontainers;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.common.AutofarmUtils;
import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.common.LocationUtils;
import com.github.bakuplayz.cropclick.common.location.DoublyLocation;
import com.github.bakuplayz.spigotspin.container.DataContainer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.DoubleChest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.AUTOFARM_STORAGE_FAILED_REMOVE;
import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.DATA_STORAGE_FAILED_SAVE_OTHER;

public final class AutofarmDataContainer extends DataContainer<HashMap<UUID, Autofarm>> implements LoggerContext {

    public AutofarmDataContainer() {
        super("autofarms.json");
        setHandler(new Handler());
    }


    /**
     * Links the {@link Autofarm provided autofarm}.
     *
     * @param autofarm the farm to link.
     */
    public void linkFarm(@NotNull Autofarm autofarm) {
        getData().put(autofarm.getFarmerId(), autofarm);
        AutofarmUtils.addCachedID(CropClick.getInstance(), autofarm);
    }


    /**
     * Unlinks the {@link Autofarm provided autofarm}.
     *
     * @param autofarm the farm to unlink.
     */
    public void unlinkFarm(@NotNull Autofarm autofarm) {
        getData().remove(autofarm.getFarmerId());
        AutofarmUtils.removeCachedID(CropClick.getInstance(), autofarm);
    }


    /**
     * Unlinks all the destroyed {@link Autofarm autofarms}.
     */
    private void unlinkDestroyedFarms() {
        AutofarmManager manager = CropClick.getInstance().getAutofarmManager();

        if (manager == null) {
            getData().values().removeIf(farm -> !farm.isLinked());
            return;
        }

        try {
            getData().values().removeIf(farm -> !farm.isComponentsPresent(manager));
        } catch (Exception e) {
            AUTOFARM_STORAGE_FAILED_REMOVE.send(getLogger());
        }
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided farmerID.
     *
     * @param farmerID the id to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findFarmById(String farmerID) {
        if (farmerID == null) {
            return null;
        }
        return getData().getOrDefault(UUID.fromString(farmerID), null);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block crop block}.
     *
     * @param block the crop block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findFarmByCrop(@NotNull Block block) {
        return getData().values().stream()
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
    @Nullable
    public Autofarm findFarmByContainer(@NotNull Block block) {
        return getData().values().stream()
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
    @Nullable
    public Autofarm findFarmByDispenser(@NotNull Block block) {
        return getData().values().stream()
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


    private final class Handler implements EventHandler {

        @Override
        public void onCreateSuccess() {

        }


        @Override
        public void onCreateFailure() {

        }


        @Override
        public void onReloadSuccess() {

        }


        @Override
        public void onReloadFailure() {

        }


        @Override
        public void onSaveSuccess() {
            unlinkDestroyedFarms();
        }


        @Override
        public void onSaveFailure() {
            DATA_STORAGE_FAILED_SAVE_OTHER.send(getLogger(), fileName);
        }


        @Override
        public void onResetSuccess() {

        }


        @Override
        public void onResetFailure() {

        }

    }
}
