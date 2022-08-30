package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
public final class ItemBuilder {

    private @Getter int amount;

    private @Getter ItemMeta meta;
    private @Getter Material material;

    private @Getter String name;
    private @Getter List<String> lore;


    public ItemBuilder(@NotNull Material material) {
        this(material, "", 1, null);
    }


    public ItemBuilder(@NotNull Material material, int amount) {
        this(material, "", amount, null);
    }


    public ItemBuilder(@NotNull Material material, @NotNull String name) {
        this(material, name, 1, null);
    }


    public ItemBuilder(@NotNull Material material, @NotNull String name, int amount) {
        this(material, name, amount, null);
    }


    public ItemBuilder(@NotNull Material material, @NotNull String name, int amount, List<String> lore) {
        this.lore = lore == null ? Collections.emptyList() : lore;
        this.material = material;
        this.amount = amount;
        this.name = name;
    }


    public ItemBuilder(@NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            this.meta = stack.getItemMeta();
        }
        this.amount = stack.getAmount();
        this.material = stack.getType();
        this.lore = meta != null && meta.hasLore()
                    ? meta.getLore()
                    : Collections.emptyList();
    }


    public ItemBuilder(@NotNull ItemStack stack, int amount) {
        this(stack);
        this.amount = amount;
    }


    public ItemBuilder(@NotNull ItemStack stack, @NotNull String name) {
        this(stack);
        this.name = name;
    }


    public ItemBuilder(@NotNull ItemStack stack, @NotNull String name, int amount) {
        this(stack, amount);
        this.name = name;
    }


    public ItemBuilder(@NotNull ItemStack stack, @NotNull String name, int amount, List<String> lore) {
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
    public ItemBuilder setAmount(int amount) {
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
    public ItemBuilder setMaterial(Material material) {
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
    public ItemBuilder setName(String name) {
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
    public ItemBuilder setName(@NotNull CropClick plugin, @NotNull LanguageAPI.Menu name) {
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
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore == null ? this.lore : lore;
        return this;
    }


    /**
     * This function sets the lore of the item to the given lore, or if the given lore is null, it sets the lore to the
     * current lore.
     *
     * @return The ItemUtil object.
     */
    public ItemBuilder setLore(String... lore) {
        this.lore = lore == null ? this.lore : Arrays.asList(lore);
        return this;
    }


    /**
     * Converts the ItemBuilder to an ItemStack, with a given name, material, amount and lore (if there is one).
     *
     * @return An ItemStack.
     */
    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = this.meta != null ? this.meta : stack.getItemMeta();

        assert meta != null; // Only here for the compiler.

        if (lore != null) {
            meta.setLore(lore);
        }

        if (name != null) {
            meta.setDisplayName(name);
        }

        if (isTool(stack)) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        if (name != null || lore != null) {
            stack.setItemMeta(meta);
        }

        return stack;
    }


    /**
     * Converts the ItemBuilder to a Skull ItemStack, with a given name of a player.
     *
     * @param player The player whose head you want to use.
     *
     * @return A new ItemStack with the given properties.
     */
    public @NotNull ItemStack toSkullStack(@NotNull OfflinePlayer player) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta meta = this.meta != null
                         ? (SkullMeta) this.meta
                         : (SkullMeta) stack.getItemMeta();

        assert meta != null; // Only here for the compiler.

        if (lore != null) {
            meta.setLore(lore);
        }

        if (name != null) {
            meta.setDisplayName(name);
        }

        if (name != null || lore != null) {
            meta.setOwningPlayer(player);
            stack.setItemMeta(meta);
        }

        return stack;
    }


    /**
     * Returns true if the given item is a tool.
     *
     * @param item The item to check
     *
     * @return A boolean value.
     */
    private boolean isTool(@NotNull ItemStack item) {
        return EnchantmentTarget.TOOL.includes(item);
    }

}