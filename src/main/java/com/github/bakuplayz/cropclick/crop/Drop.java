package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.utils.ItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class Drop {

    private final Random rand;

    private @Getter final int amount;
    private @Getter final String name;
    private @Getter final Material type;
    private @Getter final double chance;

    public Drop(Material type,
                String name,
                int amount,
                double chance) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.chance = chance;
        this.rand = new Random();
    }

    public boolean willDrop() {
        return chance >= rand.nextDouble();
    }

    public @NotNull ItemStack toItemStack() {
        return new ItemUtil(type)
                .setName(name)
                .setAmount((int) (amount * chance))
                .toItemStack();
    }

}