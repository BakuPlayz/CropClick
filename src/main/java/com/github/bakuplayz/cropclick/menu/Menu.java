package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class Menu implements InventoryHolder {

    protected Player player;
    protected CropClick plugin;

    protected @Getter Inventory inventory;
    protected @Setter InventoryType inventoryType;

    private final String titleType;
    private final LanguageAPI.Menu menuTitle;


    public Menu(@NotNull CropClick plugin, @NotNull Player player, @NotNull LanguageAPI.Menu menuTitle) {
        this.menuTitle = menuTitle;
        this.titleType = "";
        this.player = player;
        this.plugin = plugin;
    }


    public Menu(@NotNull CropClick plugin,
                @NotNull Player player,
                @NotNull LanguageAPI.Menu menuTitle,
                @NotNull String titleType) {
        this.titleType = titleType;
        this.menuTitle = menuTitle;
        this.player = player;
        this.plugin = plugin;
    }


    /**
     * This function is called when the menu is created, and it's where you should add all of your menu items.
     */
    public abstract void setMenuItems();

    /**
     * This function is called when a player clicks on a menu item.
     *
     * @param event The InventoryClickEvent that was fired.
     */
    public abstract void handleMenu(@NotNull InventoryClickEvent event);


    /**
     * If the item clicked is the back item, open the menu.
     *
     * @param clicked The item that was clicked.
     * @param menu    The menu that the item is in.
     */
    protected final void handleBack(@NotNull ItemStack clicked, @NotNull Menu menu) {
        if (!clicked.equals(getBackItem())) {
            return;
        }

        menu.open();
    }


    /**
     * It sets the item in the bottom right corner of the inventory to the item returned by the `getBackItem()` function.
     */
    protected final void setBackItem() {
        inventory.setItem(49, getBackItem());
    }


    /**
     * This function creates an inventory with the title of the menuTitle variable and sets it to the inventory variable.
     */
    private void setInventory() {
        if (inventoryType != null) {
            this.inventory = Bukkit.createInventory(this, inventoryType, menuTitle.getTitle(plugin, titleType));
            return;
        }

        this.inventory = Bukkit.createInventory(this, 54, menuTitle.getTitle(plugin, titleType));
    }


    /**
     * This function opens the inventory for the player.
     */
    public final void open() {
        setInventory();
        setMenuItems();
        player.openInventory(inventory);
    }


    /**
     * It clears the inventory, then sets the menu items.
     */
    public void updateMenu() {
        inventory.clear();
        setMenuItems();
    }


    /**
     * It returns a new ItemStack with the material of a barrier and the name of the back item.
     *
     * @return An ItemStack.
     */
    protected final @NotNull ItemStack getBackItem() {
        return new ItemUtil(Material.BARRIER)
                .setName(plugin, LanguageAPI.Menu.BACK_ITEM_NAME)
                .toItemStack();
    }

}