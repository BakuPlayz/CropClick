package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
     * If the damage is less than or equal to -1, then set the damage to the current damage, otherwise set the damage to
     * the damage
     *
     * @param damage The damage value of the item.
     *
     * @return The ItemUtil object
     */
    public ItemUtil setDamage(int damage) {
        this.damage = damage <= -1 ? this.damage : (short) damage;
        return this;
    }


    /**
     * Sets the material of the item, if the id is greater than -1.
     *
     * @param id The ID of the item.
     *
     * @return The ItemUtil object.
     */
    @Deprecated
    public ItemUtil setMaterial(int id) {
        this.material = id <= -1 ? this.material : Material.getMaterial(id);
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
     * Converts the ItemUtil to an ItemStack, with a given name, material, amount, damage and lore (if there is one).
     *
     * @return An ItemStack.
     */
    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount, damage);
        ItemMeta meta = this.meta != null ? this.meta : stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (name != null) meta.setDisplayName(name);
        if (name != null || lore != null) {
            stack.setItemMeta(meta);
        }

        return stack;
    }


    /**
     * This function returns an ItemStack with the given owner, and the rest of the properties of this ItemBuilder.
     *
     * @param owner The owner of the skull.
     *
     * @return An ItemStack.
     */
    @NotNull
    public ItemStack toSkullItem(@NotNull String owner) {
        ItemStack stack = new ItemStack(material, amount, (byte) 3);
        SkullMeta meta = this.meta != null
                         ? (SkullMeta) this.meta
                         : (SkullMeta) stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (name != null) meta.setDisplayName(name);
        meta.setOwner(owner);

        stack.setItemMeta(meta);

        return stack;
    }

}