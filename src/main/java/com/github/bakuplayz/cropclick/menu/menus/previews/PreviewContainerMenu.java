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

package com.github.bakuplayz.cropclick.menu.menus.previews;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.PreviewMenu;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Preview Container menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class PreviewContainerMenu extends PreviewMenu {

    /**
     * A variable containing the {@link Container previewed container's} inventory.
     */
    private @Setter(AccessLevel.PRIVATE) Inventory containerInventory;


    public PreviewContainerMenu(@NotNull CropClick plugin,
                                @NotNull Player player,
                                @NotNull String shortFarmerID,
                                @NotNull Inventory inventory) {
        super(plugin, player, LanguageAPI.Menu.CONTAINER_PREVIEW_TITLE, shortFarmerID);
        setInventoryType(inventory instanceof DoubleChestInventory ? null : inventory.getType());
        setContainerInventory(inventory);
    }


    @Override
    public void setMenuItems() {
        inventory.setContents(containerInventory.getContents());
    }

}