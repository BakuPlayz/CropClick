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

package com.github.bakuplayz.cropclick.crops.seeds;

import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Drop;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class AbstractSeed implements Seed {

    protected final CropsConfig cropsConfig;


    public AbstractSeed(@NotNull CropsConfig config) {
        this.cropsConfig = config;
    }


    /**
     * Creates a {@link Drop} instance for this seed using its associated menu type.
     * The drop's name, amount, and chance are retrieved from the configuration if available;
     * otherwise, the provided default values are used.
     *
     * @param defAmount the default amount to drop if not specified in the configuration.
     * @param defChance the default chance of dropping if not specified in the configuration.
     *
     * @return a {@link Drop} representing the seed's drop configuration.
     */
    protected Drop createDrop(int defAmount, double defChance) {
        return new Drop(getMenuType(),
                cropsConfig.getString(ConfigurationKey.SEED_DROP_NAME, getName()),
                cropsConfig.getIntOrDefault(ConfigurationKey.SEED_DROP_AMOUNT, defAmount, getName()),
                cropsConfig.getDoubleOrDefault(ConfigurationKey.SEED_DROP_CHANCE, defChance, getName())
        );
    }


    /**
     * Checks whether the {@link AbstractSeed seed} has a {@link Drop drop}.
     *
     * @return true if it has, otherwise false.
     */
    @Override
    public boolean hasDrop() {
        return getDrop() != null;
    }


    /**
     * Harvests the {@link AbstractSeed seed}.
     *
     * @param inventory the inventory to add the drops to.
     */
    @Override
    public void harvest(@NotNull Inventory inventory) {
        if (!hasDrop()) {
            return;
        }

        Drop drop = getDrop();
        if (!drop.willDrop()) {
            return;
        }

        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

        if (dropItem.getAmount() != 0) {
            inventory.addItem(dropItem);
        }
    }


    /**
     * Checks whether the {@link AbstractSeed seed} is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return cropsConfig.getBoolean(ConfigurationKey.SEED_ENABLED, getName());
    }


    /**
     * Checks whether the name has changed.
     *
     * @return true if it has, otherwise false.
     */
    private boolean hasNameChanged() {
        return !getName().equals(getDrop().getName());
    }

}