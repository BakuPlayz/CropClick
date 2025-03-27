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
     *
     * @return true if harvested, otherwise false.
     */
    @Override
    public boolean harvest(@NotNull Inventory inventory) {
        if (!hasDrop()) {
            return false;
        }

        Drop drop = getDrop();
        if (!drop.willDrop()) {
            return false;
        }

        ItemStack dropItem = drop.toItemStack(
                hasNameChanged()
        );

        if (dropItem.getAmount() != 0) {
            inventory.addItem(dropItem);
        }

        return true;
    }


    /**
     * Checks whether the {@link AbstractSeed seed} is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return cropsConfig.get(ConfigurationKey.SEED_ENABLED, getName());
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