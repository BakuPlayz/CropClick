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
 * A utility class to manufacture {@link ItemStack items} easier.
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
     * Sets the amount to the provided amount.
     *
     * @param amount the amount to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount <= -1 ? this.amount : amount;
        return this;
    }


    /**
     * Sets the material to the provided material.
     *
     * @param material the material to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setMaterial(Material material) {
        this.material = material == null ? this.material : material;
        return this;
    }


    /**
     * Sets the material to the provided material, if it matches the condition.
     *
     * @param material the material to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setMaterial(boolean condition, Material material) {
        if (condition) {
            setMaterial(material);
        }
        return this;
    }


    /**
     * Sets the name of the item to the provided name.
     *
     * @param name the name to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setName(String name) {
        this.name = name == null ? this.name : name;
        return this;
    }


    /**
     * Sets the name of the item to the provided name.
     *
     * @param plugin the CropClick instance.
     * @param name   the name to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setName(@NotNull CropClick plugin, @NotNull LanguageAPI.Menu name) {
        this.name = name.get(plugin);
        return this;
    }


    /**
     * Sets the lore of the item to the provided lore.
     *
     * @param lore the lore to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore == null ? this.lore : lore;
        return this;
    }


    /**
     * Sets the lore of the item to the provided lore.
     *
     * @param lore the lore to set.
     *
     * @return the {@link ItemBuilder ItemBuilder instance}.
     */
    public ItemBuilder setLore(String... lore) {
        this.lore = lore == null ? this.lore : Arrays.asList(lore);
        return this;
    }


    /**
     * Converts the {@link ItemBuilder item builder} to a {@link ItemStack item}.
     *
     * @return the item.
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

        if (isToolOrWeapon(stack)) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        if (name != null || lore != null) {
            stack.setItemMeta(meta);
        }

        return stack;
    }


    /**
     * Converts the {@link ItemBuilder item builder} to a {@link ItemStack item} of the {@link OfflinePlayer provided player's} head.
     *
     * @param player the player whose head you want to use.
     *
     * @return the head of the provided {@link OfflinePlayer}.
     */
    public @NotNull ItemStack toPlayerHead(@NotNull OfflinePlayer player) {
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
     * Checks whether the passed {@link ItemStack item} is a tool or weapon.
     *
     * @param item the item to check.
     *
     * @return true if is, otherwise false.
     */
    private boolean isToolOrWeapon(@NotNull ItemStack item) {
        return EnchantmentTarget.WEAPON.includes(item) || EnchantmentTarget.TOOL.includes(item);
    }

}