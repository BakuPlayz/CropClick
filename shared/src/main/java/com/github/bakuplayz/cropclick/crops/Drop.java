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

package com.github.bakuplayz.cropclick.crops;

import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


/**
 * A class representing an {@link ItemStack item} drop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@AllArgsConstructor
public final class Drop {

    private final static Random RANDOM = new Random();

    private final XMaterial type;

    private final String name;

    private final int amount;

    private final double chance;


    /**
     * Checks whether the {@link Drop drop} will drop.
     *
     * @return true if it will, otherwise false.
     */
    public boolean willDrop() {
        return getChance() >= 1 - chance;
    }


    /**
     * Gets the {@link Drop drop} as an {@link ItemStack item}.
     *
     * @param nameChanged true if the name has been changed, otherwise false.
     *
     * @return the drop as an item.
     */
    @NotNull
    public ItemStack toItemStack(boolean nameChanged) {
        ItemStack item = new ItemStack(type.parseItem());
        ItemMeta meta = item.getItemMeta();

        item.setAmount((int) Math.round(amount * getChance()));
        if (nameChanged) {
            meta.setDisplayName(Messages.colorize(name));
            item.setItemMeta(meta);
        }

        return item;
    }


    private double getChance() {
        return RANDOM.nextDouble();
    }

}