package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class ItemUtil {

    private int amount;
    private short damage = 0;
    private Material material;
    private ItemMeta itemMeta;
    private String displayName = "";
    private List<String> lore = new ArrayList<>();

    public ItemUtil(final Material material) {
        this(material, "", 1, null);
    }

    public ItemUtil(final Material material, final int amount) {
        this(material, "", amount, null);
    }

    public ItemUtil(final Material material, final short damage) {
        this(material);
        this.damage = damage;
    }

    public ItemUtil(final Material material, final String displayName) {
        this(material, displayName, 1, null);
    }

    public ItemUtil(final Material material, final String displayName, final int amount) {
        this(material, displayName, amount, null);
    }

    public ItemUtil(final Material material, final String displayName, final int amount, final List<String> lore) {
        this.displayName = displayName;
        this.material = material;
        this.amount = amount;
        this.lore = lore;
    }

    public ItemUtil(final int materialId) {
        this(Material.getMaterial(materialId), "", 1, null);
    }

    public ItemUtil(final @NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            itemMeta = stack.getItemMeta();
        }
        this.amount = stack.getAmount();
        this.material = stack.getType();
        this.damage = stack.getDurability();
    }

    public ItemUtil(final ItemStack stack, final int amount) {
        this(stack);
        this.amount = amount;
    }

    public ItemUtil(final ItemStack stack, final String displayName) {
        this(stack);
        this.displayName = displayName;
    }

    public ItemUtil(final ItemStack stack, final String displayName, final int amount) {
        this(stack, amount);
        this.displayName = displayName;
    }

    public ItemUtil(final ItemStack stack, final String displayName, final int amount, final List<String> lore) {
        this(stack, displayName, amount);
        this.lore = lore;
    }

    public ItemUtil setAmount(final int amount) {
        if (amount < -1) return this;
        this.amount = amount;
        return this;
    }

    public ItemUtil setDamage(final short damage) {
        if (damage < -1) return this;
        this.damage = damage;
        return this;
    }

    public ItemUtil setMaterial(final int materialId) {
        if (materialId < -1) return this;
        this.material = Material.getMaterial(materialId);
        return this;
    }

    public ItemUtil setMaterial(final Material material) {
        if (material == null) return this;
        this.material = material;
        return this;
    }

    public ItemUtil setDisplayName(final String displayName) {
        if (displayName == null) return this;
        this.displayName = displayName;
        return this;
    }

    public ItemUtil setLore(final List<String> lore) {
        if (lore == null) return this;
        this.lore = lore;
        return this;
    }

    public ItemUtil setLore(final String... lore) {
        if (lore == null) return this;
        this.lore = Arrays.asList(lore);
        return this;
    }

    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount, damage);
        ItemMeta meta = itemMeta != null ? itemMeta : stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (displayName != null) meta.setDisplayName(displayName);
        stack.setItemMeta(meta);

        return stack;
    }
}
