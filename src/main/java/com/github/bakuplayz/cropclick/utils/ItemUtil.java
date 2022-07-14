package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    public ItemUtil(Material material) {
        this(material, "", 1, null);
    }

    public ItemUtil(Material material, int amount) {
        this(material, "", amount, null);
    }

    public ItemUtil(Material material, short damage) {
        this(material, "", 1, damage);
    }

    public ItemUtil(Material material, @NotNull String name) {
        this(material, name, 1, null);
    }

    public ItemUtil(Material material, @NotNull String name, int amount) {
        this(material, name, amount, null);
    }

    public ItemUtil(Material material, @NotNull String name, int amount, short damage) {
        this(material, name, amount, null);
        this.damage = damage;
    }

    public ItemUtil(Material material, @NotNull String name, int amount, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.name = name;
    }

    @Deprecated
    public ItemUtil(int id) {
        this(Material.getMaterial(id), "", 1, null);
    }

    public ItemUtil(@NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            meta = stack.getItemMeta();
        }
        this.amount = stack.getAmount();
        this.material = stack.getType();
        this.damage = stack.getDurability();
    }

    public ItemUtil(ItemStack stack, int amount) {
        this(stack);
        this.amount = amount;
    }

    public ItemUtil(ItemStack stack, @NotNull String name) {
        this(stack);
        this.name = name;
    }

    public ItemUtil(ItemStack stack, @NotNull String name, int amount) {
        this(stack, amount);
        this.name = name;
    }

    public ItemUtil(ItemStack stack, @NotNull String name, int amount, List<String> lore) {
        this(stack, name, amount);
        this.lore = lore;
    }

    public ItemUtil setAmount(int amount) {
        this.amount = amount <= -1 ? this.amount : amount;
        return this;
    }

    public ItemUtil setDamage(int damage) {
        this.damage = damage <= -1 ? this.damage : (short) damage;
        return this;
    }

    @Deprecated
    public ItemUtil setMaterial(int id) {
        this.material = id <= -1 ? this.material : Material.getMaterial(id);
        return this;
    }

    public ItemUtil setMaterial(Material material) {
        this.material = material == null ? this.material : material;
        return this;
    }

    public ItemUtil setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    public ItemUtil setName(@NotNull CropClick plugin, @NotNull LanguageAPI.Menu name) {
        this.name = name.get(plugin);
        return this;
    }

    public ItemUtil setLore(@NotNull List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemUtil setLore(String... lore) {
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

    public @NotNull ItemStack toSkullItem(@NotNull String skullName) {
        ItemStack stack = new ItemStack(material, amount, (short) 3);
        SkullMeta meta = this.meta != null
                ? (SkullMeta) this.meta
                : (SkullMeta) stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (name != null) meta.setDisplayName(name);

        if (material == Material.SKULL_ITEM) {
            meta.setOwner(skullName);
        }

        stack.setItemMeta(meta);

        return stack;
    }

}