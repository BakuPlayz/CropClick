/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropsMenu;
import com.github.bakuplayz.cropclick.menus.crops.CropMenu;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.CROPS_ITEM_DROP_VALUE;

/**
 * A class representing the Crops menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropsMenu extends AbstractCropsMenu {

    public CropsMenu(@NotNull CropClick plugin) {
        super(plugin, (crop) -> getItemLore(plugin, crop));
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull Crop crop) {
        return CROPS_ITEM_DROP_VALUE.getAsList(plugin, crop.getDrop().getAmount());
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Crop crop, int position) {
        return (item, player) -> new CropMenu(plugin, crop).open(player);
    }

}
