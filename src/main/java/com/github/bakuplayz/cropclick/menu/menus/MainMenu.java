/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
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
 * A class representing the Main menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MainMenu extends BaseMenu {

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
            new CropsMenu(plugin, player, CropMenuState.CROP).openMenu();
        }

        if (clicked.equals(getAutofarmsItem())) {
            new AutofarmsMenu(plugin, player, AutofarmsMenuState.MENU).openMenu();
        }

        if (clicked.equals(getUpdatesItem())) {
            new UpdatesMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getAddonsItem())) {
            new AddonsMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getHelpItem())) {
            new HelpMenu(plugin, player, true).openMenu();
        }

        if (clicked.equals(getSettingsItem())) {
            new SettingsMenu(plugin, player, true).openMenu();
        }
    }


    /**
     * Gets the updates {@link ItemStack item}.
     *
     * @return the updates item.
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
     * Gets the addons {@link ItemStack item}.
     *
     * @return the addons item.
     */
    private @NotNull ItemStack getAddonsItem() {
        return new ItemBuilder(Material.ENDER_CHEST)
                .setName(plugin, LanguageAPI.Menu.MAIN_ADDONS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_ADDONS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_ADDONS_ITEM_STATUS.get(plugin, 6)
                )).toItemStack();
    }


    /**
     * Gets the settings {@link ItemStack item}.
     *
     * @return the settings item.
     */
    private @NotNull ItemStack getSettingsItem() {
        return new ItemBuilder(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.MAIN_SETTINGS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MAIN_SETTINGS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MAIN_SETTINGS_ITEM_STATUS.get(plugin, 6)
                )).toItemStack();
    }


    /**
     * Gets the crops {@link ItemStack item}.
     *
     * @return the crops item.
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
     * Gets the autofarms {@link ItemStack item}.
     *
     * @return the autofarms item.
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
     * Gets the help {@link ItemStack item}.
     *
     * @return the help item.
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