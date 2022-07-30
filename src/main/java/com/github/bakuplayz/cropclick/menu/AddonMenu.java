package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class AddonMenu extends Menu {

    protected boolean addonEnabled;
    protected final String addonName;

    protected final AddonManager addonManager;


    public AddonMenu(@NotNull CropClick plugin,
                     @NotNull Player player,
                     @NotNull LanguageAPI.Menu menuTitle,
                     @NotNull String addonName) {
        super(plugin, player, menuTitle);
        this.addonManager = plugin.getAddonManager();
        this.addonName = addonName;
    }


    /**
     * This function updates the menu by clearing the inventory and then setting the menu items.
     */
    @Override
    public void updateMenu() {
        inventory.clear();
        setMenuItems();
    }


    /**
     * If the item clicked is the toggle item, toggle the addon.
     *
     * @param clicked    The item that was clicked.
     * @param toggleItem The item that the player clicked on.
     */
    protected final void handleToggle(@NotNull ItemStack clicked, @NotNull ItemStack toggleItem) {
        if (!clicked.equals(toggleItem)) return;

        addonManager.toggle(addonName);
    }


    /**
     * Set the toggle item in the inventory to the correct state, and then
     * finally it updates the addons enabled status.
     */
    protected final void setToggleItem() {
        addonEnabled = addonManager.isEnabled(addonName);
        inventory.setItem(22, getToggleItem());
    }


    /**
     * This function returns the item that will be used to toggle the item's state.
     *
     * @return The item that will be used to toggle the ability.
     */
    @NotNull
    protected abstract ItemStack getToggleItem();


    /**
     * If the clicked item is the world item, open the menu.
     *
     * @param clicked The item that was clicked.
     * @param menu    The menu that the item is in.
     */
    protected final void handleWorlds(@NotNull ItemStack clicked, @NotNull Menu menu) {
        if (!clicked.equals(getWorldsItem())) return;

        menu.open();
    }


    /**
     * Sets the world item in the inventory.
     */
    protected final void setWorldsItem() {
        inventory.setItem(24, getWorldsItem());
    }


    /**
     * Returns an ItemStack that represents the worlds addon.
     *
     * @return An ItemStack.
     */
    @NotNull
    protected ItemStack getWorldsItem() {
        return new ItemUtil(Material.GRASS)
                .setName(plugin, LanguageAPI.Menu.ADDON_WORLDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDON_WORLDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDON_WORLDS_ITEM_STATUS.get(plugin)
                ))
                .toItemStack();
    }

}