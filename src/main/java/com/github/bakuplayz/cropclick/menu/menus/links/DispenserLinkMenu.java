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

package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Dispenser Link menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class DispenserLinkMenu extends LinkMenu {

    /**
     * A variable containing the state or menu to return to when clicking the {@link #getBackItem() back item}.
     */
    private final AutofarmsMenuState menuState;


    public DispenserLinkMenu(@NotNull CropClick plugin,
                             @NotNull Player player,
                             @NotNull Block block,
                             Autofarm autofarm,
                             AutofarmsMenuState state) {
        super(plugin, player, block, autofarm, LanguageAPI.Menu.DISPENSER_LINK_TITLE, Component.DISPENSER);
        this.menuState = state;
    }


    @Override
    public void setMenuItems() {
        super.setMenuItems();

        if (menuState != AutofarmsMenuState.DISPENSER) {
            setBackItem();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        if (menuState != AutofarmsMenuState.DISPENSER) {
            handleBack(clicked, new AutofarmsMenu(plugin, player, menuState));
        }

        if (isUnclaimed) {
            handleUnclaimed(clicked);
            refreshMenu();
            return;
        }

        if (!isUnlinked) {
            handlePreviews(clicked);
            handleToggle(clicked);
            refreshMenu();
        }

        if (!clicked.equals(dispenserItem)) {
            return;
        }

        if (isUnlinked) {
            handleSelect();
            refreshMenu();
            handleLink();
        }
    }

}