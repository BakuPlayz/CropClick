package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.*;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * Represents the Main menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MainMenu extends Menu {

    public MainMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.MAIN_TITLE);
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(21, getCropsItem());
        inventory.setItem(23, getAutofarmsItem());

        inventory.setItem(45, getUpdatesItem());

        inventory.setItem(49, getHelpItem());

        inventory.setItem(44, getAddonsItem());
        inventory.setItem(53, getSettingsItem());
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        if (clicked.equals(getCropsItem())) {
            new CropsMenu(plugin, player, CropMenuState.CROP).open();
        }

        if (clicked.equals(getAutofarmsItem())) {
            new AutofarmsMenu(plugin, player, AutofarmsMenuState.MENU_REDIRECT).open();
        }

        if (clicked.equals(getUpdatesItem())) {
            new UpdatesMenu(plugin, player).open();
        }

        if (clicked.equals(getAddonsItem())) {
            new AddonsMenu(plugin, player).open();
        }

        if (clicked.equals(getHelpItem())) {
            new HelpMenu(plugin, player, true).open();
        }

        if (clicked.equals(getSettingsItem())) {
            new SettingsMenu(plugin, player, true).open();
        }
    }


    /**
     * It creates an item with the material of an anvil, the name of "Updates" and the lore of explaining the update menu.
     *
     * @return An ItemStack
     */
    private @NotNull ItemStack getUpdatesItem() {
        String updateState = plugin.getUpdateManager().getUpdateStateMessage();
        return new ItemBuilder(Material.ANVIL)
                .setName(plugin, LanguageAPI.Menu.MAIN_UPDATES_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_UPDATES_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_UPDATES_ITEM_STATE.get(plugin, updateState)
                )).toItemStack();
    }


    /**
     * It creates an item with the material of an ender chest, the name of "Addons" and the lore of explaining the addons menu.
     *
     * @return An ItemStack
     */
    private @NotNull ItemStack getAddonsItem() {
        return new ItemBuilder(Material.ENDER_CHEST)
                .setName(plugin, LanguageAPI.Menu.MAIN_ADDONS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_ADDONS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_ADDONS_ITEM_STATUS.get(plugin, 6)
                )).toItemStack();
    }


    /**
     * It creates an item with the material of a chest, the name of "Settings" and the lore explaining the settings menu.
     *
     * @return An ItemStack
     */
    private @NotNull ItemStack getSettingsItem() {
        return new ItemBuilder(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.MAIN_SETTINGS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_SETTINGS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_SETTINGS_ITEM_STATUS.get(plugin, 6)
                )).toItemStack();
    }


    /**
     * It creates an item with the material of a wheat, the name of "Crops" and the lore of "Amount of Crops: X".
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getCropsItem() {
        int amountOfCrops = plugin.getCropManager().getAmountOfCrops();
        return new ItemBuilder(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.MAIN_CROPS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_CROPS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_CROPS_ITEM_STATUS.get(plugin, amountOfCrops)
                )).toItemStack();
    }


    /**
     * It creates an item with material of dispenser, the name of "Autofarms" and the lore "Amount of Autofarms: X".
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getAutofarmsItem() {
        int amountOfFarms = plugin.getAutofarmManager().getAmountOfFarms();
        return new ItemBuilder(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.MAIN_AUTOFARMS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_AUTOFARMS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_AUTOFARMS_ITEM_STATUS.get(plugin, amountOfFarms)
                )).toItemStack();
    }


    /**
     * It creates an item with material of book, the name of "Help" and the lore "Amount of Commands: X".
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getHelpItem() {
        int amountOfCommands = plugin.getCommandManager().getAmountOfCommands();
        return new ItemBuilder(Material.BOOK)
                .setName(plugin, LanguageAPI.Menu.MAIN_HELP_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_HELP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_HELP_ITEM_STATUS.get(plugin, amountOfCommands)
                )).toItemStack();
    }

}