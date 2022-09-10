package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Drop {

    private @Getter final int amount;
    private @Getter final short damage;
    private @Getter final String name;
    private @Getter final Material type;

    private final double randomChance;
    private @Getter final double chance;


    public Drop(@NotNull Material type,
                @NotNull String name,
                int amount,
                double chance) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.damage = -1;
        this.randomChance = new Random().nextDouble();
    }


    public Drop(@NotNull Material type,
                @NotNull String name,
                int amount,
                double chance,
                short damage) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.damage = damage;
        this.randomChance = new Random().nextDouble();
    }


    /**
     * If the chance is greater than or equal to a random number between 0 and 1, then drop the drops.
     *
     * @return A boolean value.
     */
    public boolean willDrop() {
        return randomChance >= chance;
    }


    /**
     * It returns an ItemStack with the type, name, and amount of the Item.
     *
     * @return An ItemStack.
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