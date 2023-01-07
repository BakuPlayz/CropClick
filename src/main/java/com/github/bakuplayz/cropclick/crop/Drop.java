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
    private @Getter final String name;
    private @Getter final Material type;

    private @Getter final double chance;
    private @Getter final double randomChance;


    public Drop(@NotNull Material type,
                @NotNull String name,
                int amount,
                double chance) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.randomChance = new Random().nextDouble();
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
                .toItemStack();
    }

}