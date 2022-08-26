package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ItemUtil {

    private @Getter int amount;

    private @Getter ItemMeta meta;
    private @Getter Material material;

    private @Getter String name;
    private @Getter List<String> lore;


    public ItemUtil(@NotNull Material material) {
        this(material, "", 1, null);
    }


    public ItemUtil(@NotNull Material material, int amount) {
        this(material, "", amount, null);
    }


    public ItemUtil(@NotNull Material material, @NotNull String name) {
        this(material, name, 1, null);
    }


    public ItemUtil(@NotNull Material material, @NotNull String name, int amount) {
        this(material, name, amount, null);
    }


    public ItemUtil(@NotNull Material material, @NotNull String name, int amount, List<String> lore) {
        this.lore = lore == null ? Collections.emptyList() : lore;
        this.material = material;
        this.amount = amount;
        this.name = name;
    }


    public ItemUtil(@NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            this.meta = stack.getItemMeta();
        }
        this.amount = stack.getAmount();
        this.material = stack.getType();
        this.lore = meta != null && meta.hasLore()
                    ? meta.getLore()
                    : Collections.emptyList();
    }


    public ItemUtil(@NotNull ItemStack stack, int amount) {
        this(stack);
        this.amount = amount;
    }


    public ItemUtil(@NotNull ItemStack stack, @NotNull String name) {
        this(stack);
        this.name = name;
    }


    public ItemUtil(@NotNull ItemStack stack, @NotNull String name, int amount) {
        this(stack, amount);
        this.name = name;
    }


    public ItemUtil(@NotNull ItemStack stack, @NotNull String name, int amount, List<String> lore) {
        this(stack, name, amount);
        this.lore = lore;
    }


    /**
     * If the amount is less than or equal to -1, then set the amount to the current amount. Otherwise, set the amount to
     * the amount
     *
     * @param amount The amount of the item.
     *
     * @return The ItemUtil object.
     */
    public ItemUtil setAmount(int amount) {
        this.amount = amount <= -1 ? this.amount : amount;
        return this;
    }


    /**
     * If the material is null, then set the material to the current material, otherwise set the material to the material.
     *
     * @param material The material of the item.
     *
     * @return The ItemUtil object
     */
    public ItemUtil setMaterial(Material material) {
        this.material = material == null ? this.material : material;
        return this;
    }


    /**
     * Sets the name of the item and returns the ItemUtil instance.
     *
     * @param name The name of the item.
     *
     * @return The ItemUtil object itself.
     */
    public ItemUtil setName(String name) {
        this.name = name == null ? this.name : name;
        return this;
    }


    /**
     * This function sets the name of the item.
     *
     * @param plugin The plugin instance
     * @param name   The name of the item.
     *
     * @return The ItemUtil object.
     */
    public ItemUtil setName(@NotNull CropClick plugin, @NotNull LanguageAPI.Menu name) {
        this.name = name.get(plugin);
        return this;
    }


    /**
     * If the lore is null, then set the lore to the current lore, otherwise set the lore to the new lore.
     *
     * @param lore The lore of the item.
     *
     * @return The ItemUtil object.
     */
    public ItemUtil setLore(List<String> lore) {
        this.lore = lore == null ? this.lore : lore;
        return this;
    }


    /**
     * This function sets the lore of the item to the given lore, or if the given lore is null, it sets the lore to the
     * current lore.
     *
     * @return The ItemUtil object.
     */
    public ItemUtil setLore(String... lore) {
        this.lore = lore == null ? this.lore : Arrays.asList(lore);
        return this;
    }


    /**
     * Converts the ItemUtil to an ItemStack, with a given name, material, amount and lore (if there is one).
     *
     * @return An ItemStack.
     */
    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = this.meta != null ? this.meta : stack.getItemMeta();

        assert meta != null; // Only here for the compiler.

        if (lore != null) meta.setLore(lore);
        if (name != null) meta.setDisplayName(name);
        if (name != null || lore != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            stack.setItemMeta(meta);
        }

        return stack;
    }

}