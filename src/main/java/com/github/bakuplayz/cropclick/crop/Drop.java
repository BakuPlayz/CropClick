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

package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


/**
 * A class representing an {@link ItemStack item} drop.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Drop {

    private @Getter final int amount;
    private @Getter final int damage;
    private @Getter final String name;
    private @Getter final Material type;

    private @Getter final double chance;
    private @Getter final double randomChance;


    public Drop(@NotNull Material type,
                @NotNull String name,
                int amount,
                double chance,
                int damage) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.damage = damage;
        this.randomChance = new Random().nextDouble();
    }


    public Drop(@NotNull Material type,
                @NotNull String name,
                int amount,
                double chance) {
        this(type, name, amount, chance, 0);
    }


    /**
     * Checks whether the {@link Drop drop} will drop.
     *
     * @return true if it will, otherwise false.
     */
    public boolean willDrop() {
        return randomChance >= chance;
    }


    /**
     * Gets the {@link Drop drop} as an {@link ItemStack item}.
     *
     * @param nameChanged true if the name has been changed, otherwise false.
     *
     * @return the drop as an item.
     */
    public @NotNull ItemStack toItemStack(boolean nameChanged) {
        int randomAmount = (int) Math.round(amount * randomChance);
        return new ItemBuilder(type)
                .setName(nameChanged ? name : null)
                .setAmount(randomAmount)
                .setDamage(damage)
                .toItemStack();
    }

}