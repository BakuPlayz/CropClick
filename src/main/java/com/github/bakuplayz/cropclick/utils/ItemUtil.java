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
    private short damage;

    private ItemMeta meta;
    private Material material;

    private String name;
    private List<String> lore = new ArrayList<>();

    public ItemUtil(final Material material) {
        this(material, "", 1, null);
    }

    public ItemUtil(final Material material, final int amount) {
        this(material, "", amount, null);
    }

    public ItemUtil(final Material material, final short damage) {
        this(material, "", 1, damage);
    }

    public ItemUtil(final Material material, final @NotNull String name) {
        this(material, name, 1, null);
    }

    public ItemUtil(final Material material, final @NotNull String name, final int amount) {
        this(material, name, amount, null);
    }

    public ItemUtil(final Material material, final @NotNull String name, final int amount, final short damage) {
        this(material, name, amount, null);
        this.damage = damage;
    }

    public ItemUtil(final Material material, final @NotNull String name, final int amount, final List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.name = name;
    }

    @Deprecated
    public ItemUtil(final int id) {
        this(Material.getMaterial(id), "", 1, null);
    }

    public ItemUtil(final @NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            meta = stack.getItemMeta();
        }
        this.amount = stack.getAmount();
        this.material = stack.getType();
        this.damage = stack.getDurability();
    }

    public ItemUtil(final ItemStack stack, final int amount) {
        this(stack);
        this.amount = amount;
    }

    public ItemUtil(final ItemStack stack, final @NotNull String name) {
        this(stack);
        this.name = name;
    }

    public ItemUtil(final ItemStack stack, final @NotNull String name, final int amount) {
        this(stack, amount);
        this.name = name;
    }

    public ItemUtil(final ItemStack stack, final @NotNull String name, final int amount, final List<String> lore) {
        this(stack, name, amount);
        this.lore = lore;
    }

    public ItemUtil setAmount(final int amount) {
        this.amount = amount <= -1 ? this.amount : amount;
        return this;
    }

    public ItemUtil setDamage(final short damage) {
        this.damage = damage <= -1 ? this.damage : damage;
        return this;
    }

    @Deprecated
    public ItemUtil setMaterial(final int id) {
        this.material = id <= -1 ? this.material : Material.getMaterial(id);
        return this;
    }

    public ItemUtil setMaterial(final Material material) {
        this.material = material == null ? this.material : material;
        return this;
    }

    public ItemUtil setName(final @NotNull String name) {
        this.name = name;
        return this;
    }

    public ItemUtil setLore(final @NotNull List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemUtil setLore(final String... lore) {
        this.lore = lore == null ? this.lore : Arrays.asList(lore);
        return this;
    }

    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount, damage);
        ItemMeta meta = this.meta != null ? this.meta : stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (name != null) meta.setDisplayName(name);
        stack.setItemMeta(meta);

        return stack;
    }
}