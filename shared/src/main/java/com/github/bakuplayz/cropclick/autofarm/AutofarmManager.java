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
import com.github.bakuplayz.cropclick.api.AutofarmAPI;
import com.github.bakuplayz.cropclick.common.AutofarmUtils;
import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


/**
 * A class managing {@link Autofarm Autofarms}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.2.0
 */
public final class AutofarmManager implements AutofarmAPI {


    @NotNull
    private final CropManager cropManager;

    @NotNull
    private final DefaultConfig defaultConfig;

    @NotNull
    private final AutofarmDataService dataService;

    @NotNull
    private final AutofarmFinder autofarmFinder;


    public AutofarmManager(@NotNull CropClick plugin) {
        this.defaultConfig = plugin.getConfigManager().getDefaultConfig();
        this.dataService = plugin.getDataManager().getAutofarmService();
        this.cropManager = plugin.getCropManager();
        this.autofarmFinder = new AutofarmFinder(dataService);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findAutofarm(@NotNull Block block) {
        if (BlockUtils.isAir(block)) {
            return null;
        }

        if (AutofarmUtils.hasCachedID(block)) {
            String farmerID = AutofarmUtils.getCachedID(block);
            return autofarmFinder.findById(farmerID);
        }

        if (AutofarmUtils.isDispenser(block)) {
            return dataService.findFarmByDispenser(block);
        }

        if (AutofarmUtils.isContainer(block)) {
            return dataService.findFarmByContainer(block);
        }

        if (AutofarmUtils.isCrop(cropManager, block)) {
            return dataService.findFarmByCrop(block);
        }

        Block blockAbove = block.getRelative(BlockFace.UP);
        if (AutofarmUtils.isCrop(cropManager, blockAbove)) {
            return dataService.findFarmByCrop(blockAbove);
        }

        return null;
    }


    /**
     * Gets all the {@link Autofarm autofarms}.
     *
     * @return the found autofarms.
     */
    @NotNull
    public List<Autofarm> getAutofarms() {
        return new ArrayList<>(dataService.getMany(0, 10000).join());
    }


    /**
     * Checks whether the {@link Autofarm autofarms} are enabled.
     *
     * @return true if they are, otherwise false.
     */
    public boolean isEnabled() {
        return defaultConfig.get(ConfigurationKey.AUTOFARMS_ENABLED);
    }


    public void setEnabled(boolean isEnabled) {
        defaultConfig.set(ConfigurationKey.AUTOFARMS_ENABLED, isEnabled);
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
        return isEnabled();
    }


    public boolean isComponent(@NotNull Block block) {
        if (AutofarmUtils.isDispenser(block)) return true;
        if (AutofarmUtils.isContainer(block)) return true;
        return AutofarmUtils.isCrop(cropManager, block);
    }


    /**
     * Gets the amount of {@link Autofarm autofarms}.
     *
     * @return the amount of autofarms.
     */
    public int getAmountOfFarms() {
        return dataService.getMany(0, 10000).join().size();
    }

}