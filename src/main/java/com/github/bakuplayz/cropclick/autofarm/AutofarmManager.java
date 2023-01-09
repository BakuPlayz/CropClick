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

package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.utils.Enableable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * A class managing {@link Autofarm Autofarms}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmManager implements Enableable {

    private final @NotNull CropClick plugin;
    private final @NotNull CropManager cropManager;
    private final @NotNull PlayersConfig playersConfig;
    private final @NotNull AutofarmDataStorage farmStorage;


    public AutofarmManager(@NotNull CropClick plugin) {
        this.playersConfig = plugin.getPlayersConfig();
        this.cropManager = plugin.getCropManager();
        this.farmStorage = plugin.getFarmData();
        this.plugin = plugin;
    }


    /**
     * Selects the {@link Block provided block}, if it is a {@link Crop crop} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectCrop(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectCrop(player, block);
    }


    /**
     * Selects the {@link Block provided block}, if it is a {@link Container container} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectContainer(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectContainer(player, block);
    }


    /**
     * Selects the {@link Block provided block}, if it is a {@link Dispenser dispenser} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectDispenser(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectDispenser(player, block);
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Crop crop} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectCrop(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectCrop(player, block);
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Container container} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectContainer(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectContainer(player, block);
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Dispenser dispenser} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectDispenser(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectDispenser(player, block);
    }


    /**
     * Deselects all the {@link Player provided player's} selected {@link Component autofarm components}.
     *
     * @param player the player to deselect all the components for.
     */
    public void deselectComponents(@NotNull Player player) {
        playersConfig.deselectComponents(player);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public @Nullable Autofarm findAutofarm(@NotNull Block block) {
        if (BlockUtils.isAir(block)) {
            return null;
        }

        if (AutofarmUtils.hasCachedID(block)) {
            String farmerID = AutofarmUtils.getCachedID(block);
            return farmStorage.findFarmById(farmerID);
        }

        if (AutofarmUtils.isDispenser(block)) {
            return farmStorage.findFarmByDispenser(block);
        }

        if (AutofarmUtils.isContainer(block)) {
            return farmStorage.findFarmByContainer(block);
        }

        if (AutofarmUtils.isCrop(cropManager, block)) {
            return farmStorage.findFarmByCrop(block);
        }

        Block blockAbove = block.getRelative(BlockFace.UP);
        if (AutofarmUtils.isCrop(cropManager, blockAbove)) {
            return farmStorage.findFarmByCrop(blockAbove);
        }

        return null;
    }


    /**
     * Gets the {@link Crop selected crop} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location for.
     *
     * @return the selected crop's location, otherwise null.
     */
    public @Nullable Location getSelectedCrop(@NotNull Player player) {
        return playersConfig.getSelectedCrop(player);
    }


    /**
     * Gets the {@link Container selected container} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location for.
     *
     * @return the selected container's location, otherwise null.
     */
    public @Nullable Location getSelectedContainer(@NotNull Player player) {
        return playersConfig.getSelectedContainer(player);
    }


    /**
     * Gets the {@link Dispenser selected dispenser} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location for.
     *
     * @return the selected dispenser's location, otherwise null.
     */
    public @Nullable Location getSelectedDispenser(@NotNull Player player) {
        return playersConfig.getSelectedDispenser(player);
    }


    /**
     * Checks whether the {@link Block provided crop block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isCropSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isCropSelected(player, block);
    }


    /**
     * Checks whether the {@link Block provided container block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isContainerSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isContainerSelected(player, block);
    }


    /**
     * Checks whether the {@link Block provided dispenser block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isDispenserSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isDispenserSelected(player, block);
    }


    /**
     * Checks whether the {@link Autofarm provided autofarm} is linked.
     *
     * @param autofarm the autofarm to check.
     *
     * @return true if linked, otherwise false.
     */
    public boolean isLinked(Autofarm autofarm) {
        return autofarm != null && autofarm.isLinked();
    }


    /**
     * Checks whether the {@link Autofarm provided autofarm} is usable.
     *
     * @param autofarm the autofarm to check.
     *
     * @return true if usable, otherwise false.
     */
    public boolean isUsable(Autofarm autofarm) {
        if (autofarm == null) return false;
        if (!autofarm.isLinked()) return false;
        if (!autofarm.isEnabled()) return false;
        if (!autofarm.hasContainer()) return false;
        return isEnabled();
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Component autofarm component}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isComponent(@NotNull Block block) {
        if (AutofarmUtils.isDispenser(block)) return true;
        if (AutofarmUtils.isContainer(block)) return true;
        return AutofarmUtils.isCrop(cropManager, block);
    }


    /**
     * Checks whether the {@link Autofarm autofarms} are enabled.
     *
     * @return true if they are, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("autofarms.isEnabled", true);
    }


    /**
     * Gets the amount of {@link AutofarmDataStorage#getAutofarms() autofarms}.
     *
     * @return the amount of autofarms.
     */
    public int getAmountOfFarms() {
        return farmStorage.getAutofarms().size();
    }


    /**
     * Gets all the {@link AutofarmDataStorage#getAutofarms() autofarms}.
     *
     * @return the found autofarms.
     */
    public @NotNull List<Autofarm> getAutofarms() {
        return new ArrayList<>(farmStorage.getAutofarms().values());
    }

}