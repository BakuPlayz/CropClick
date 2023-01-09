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

package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.Enableable;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the base of an addon menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class AddonMenu extends BaseMenu implements Enableable {

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
     * Gets the toggle {@link ItemStack item}.
     *
     * @return the toggle item.
     */
    protected abstract @NotNull ItemStack getToggleItem();


    /**
     * Gets the worlds {@link ItemStack item}.
     *
     * @return the worlds item.
     */
    protected final @NotNull ItemStack getWorldsItem() {
        Addon addon = addonManager.findByName(addonName);

        return new ItemBuilder(Material.GRASS)
                .setName(plugin, LanguageAPI.Menu.ADDON_WORLDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDON_WORLDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDON_WORLDS_ITEM_STATUS.get(
                                plugin,
                                getAmountOfBanished(addon)
                        )
                )).toItemStack();
    }


    /**
     * Sets the {@link ItemStack toggle item} to its designated places in the {@link #inventory}.
     */
    protected final void setToggleItem() {
        inventory.setItem(22, getToggleItem());
    }


    /**
     * Sets the {@link ItemStack worlds item} to their designated places in the {@link #inventory}.
     */
    protected final void setWorldsItem() {
        inventory.setItem(24, getWorldsItem());
    }


    /**
     * Handles toggling of items inside the menu.
     *
     * @param clicked    the item that was clicked.
     * @param toggleItem the item to toggle.
     */
    protected final void handleToggle(@NotNull ItemStack clicked, @NotNull ItemStack toggleItem) {
        if (!clicked.equals(toggleItem)) {
            return;
        }

        addonManager.toggleAddon(addonName);
    }


    /**
     * Handles navigation to worlds inside the menu.
     *
     * @param clicked   the item that was clicked.
     * @param worldMenu the menu to navigate to.
     */
    protected final void handleWorlds(@NotNull ItemStack clicked, @NotNull BaseMenu worldMenu) {
        if (!clicked.equals(getWorldsItem())) {
            return;
        }

        worldMenu.openMenu();
    }


    /**
     * Gets the amount of {@link FarmWorld worlds} banishing the {@link Addon}.
     *
     * @param addon the addon to check.
     *
     * @return the amount of worlds banishing the addon.
     */
    private int getAmountOfBanished(Addon addon) {
        return addon == null ? 0 : addon.getAmountOfBanished();
    }


    /**
     * Checks whether the addon is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return addonManager.isEnabled(addonName);
    }

}