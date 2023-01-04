package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the base of a menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class BaseMenu implements Menu, InventoryHolder {

    protected final Player player;
    protected final CropClick plugin;

    protected @Getter Inventory inventory;
    protected @Setter InventoryType inventoryType;

    /**
     * A variable containing the extending menu's title type.
     */
    private final String titleType;

    /**
     * A variable containing the extending {@link LanguageAPI.Menu menu's title}.
     */
    private final LanguageAPI.Menu menuTitle;


    public BaseMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull LanguageAPI.Menu menuTitle) {
        this.menuTitle = menuTitle;
        this.titleType = "";
        this.player = player;
        this.plugin = plugin;
    }


    public BaseMenu(@NotNull CropClick plugin,
                    @NotNull Player player,
                    @NotNull LanguageAPI.Menu menuTitle,
                    @NotNull String titleType) {
        this.titleType = titleType;
        this.menuTitle = menuTitle;
        this.player = player;
        this.plugin = plugin;
    }


    /**
     * Sets all the menu items.
     */
    public abstract void setMenuItems();

    /**
     * Handles all the menu click events.
     *
     * @param event the menu event.
     */
    public abstract void handleMenu(@NotNull InventoryClickEvent event);


    /**
     * Handles navigations back to the previous menu.
     *
     * @param clicked the item that was clicked.
     * @param menu    the menu to navigate back to.
     */
    public void handleBack(@NotNull ItemStack clicked, @NotNull Menu menu) {
        if (!clicked.equals(getBackItem())) {
            return;
        }

        menu.openMenu();
    }


    /**
     * Sets the back {@link ItemStack item} to its designated places in the {@link #inventory}.
     */
    protected void setBackItem() {
        inventory.setItem(49, getBackItem());
    }


    /**
     * Sets and initializes the {@link #inventory}.
     */
    private void setInventory() {
        if (inventoryType != null) {
            this.inventory = Bukkit.createInventory(this, inventoryType, menuTitle.getTitle(plugin, titleType));
            return;
        }

        this.inventory = Bukkit.createInventory(this, 54, menuTitle.getTitle(plugin, titleType));
    }


    /**
     * Opens the extending menu.
     */
    public final void openMenu() {
        setInventory();
        setMenuItems();
        player.openInventory(inventory);
    }


    /**
     * Refreshes the extending menu.
     */
    public void refreshMenu() {
        inventory.clear();
        setMenuItems();
    }


    /**
     * Gets the back {@link ItemStack item}.
     *
     * @return the back item.
     */
    protected final @NotNull ItemStack getBackItem() {
        return new ItemBuilder(Material.BARRIER)
                .setName(plugin, LanguageAPI.Menu.GENERAL_BACK_ITEM_NAME)
                .toItemStack();
    }

}