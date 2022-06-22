package com.github.bakuplayz.cropclick.crop.crops.templates;

import com.github.bakuplayz.cropclick.utils.ItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class Drop {

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
    }

    public @NotNull ItemStack toStack() {
        return new ItemUtil(type)
                .setName(name)
                .setAmount(amount)
                .toItemStack();
    }

}